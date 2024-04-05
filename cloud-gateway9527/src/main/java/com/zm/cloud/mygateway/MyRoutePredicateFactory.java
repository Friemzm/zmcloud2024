package com.zm.cloud.mygateway;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.AfterRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
//业务需求说明：自定义配置会员等级userType，按照钻石/黄金/青铜的等级，yml配置会员等级
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
                if (userType == null) {
                    return false;
                }
                //这个config是从配置文件中拿到的如果和传来的相匹配就通过
                if (userType.equals(config.getUserType())){
                    return true;
                }
                return false;
            }
        };
    }
    @Validated 
    public static class Config{
        @Setter@Getter@NotNull
        private String userType;  //会员类型
    }
    //空参构造函数
    public MyRoutePredicateFactory() {
        super(Config.class);
    }
    //实现短配置方式
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }

}
