package com.zm.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.zm.cloud.mapper")
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Main2002 {
    public static void main(String[] args) {
        SpringApplication.run(Main2002.class,args);
    }
}