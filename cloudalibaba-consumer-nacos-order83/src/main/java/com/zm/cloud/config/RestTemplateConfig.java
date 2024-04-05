package com.zm.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    //实例化 RestTemplate 实例
    @Bean
    @LoadBalanced  //开负载均衡
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
