package com.zm.cloud.mygateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyGlobal implements GlobalFilter, Ordered {
    public static final String START_TIME = "start_time";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
         //先记录访问接口开始的时间
         exchange.getAttributes().put(START_TIME,System.currentTimeMillis());
         //调用chain.filter(exchange)，将请求传递给下一个过滤器或最终的目标服务。
        //使用then方法确保在请求处理完成后执行一些操作
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                log.info("访问接口的主机：{}",exchange.getRequest().getURI().getHost());
                log.info("访问接口端口：{}",exchange.getRequest().getURI().getPort());
                log.info("访问接口URL：{}",exchange.getRequest().getURI().getPath());
                log.info("访问接口的URL参数：{}",exchange.getRequest().getURI().getRawQuery());
                log.info("访问接口的时长：{}",(System.currentTimeMillis()-startTime)+"毫秒");
                System.out.println("----------------------分割线---------------------------");
            }
        }));
    }
    //返回的值越小优先级别越高
    @Override
    public int getOrder() {
        return -1;
    }
}
