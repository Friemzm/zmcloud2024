package com.zm.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Retryer myRetryer(){
        return Retryer.NEVER_RETRY; //Feign默认配置是不走重试策略的
        //最大请求次数为（1+2）次，初始时间间隔为100ms，重试最大间隔时间为1秒
        //return new Retryer.Default(100,1,3);
    }
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
