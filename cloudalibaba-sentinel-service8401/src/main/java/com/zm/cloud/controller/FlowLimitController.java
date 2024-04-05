package com.zm.cloud.controller;

import com.zm.cloud.service.FlowLimitService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {
    @GetMapping("/test1")
    public String test1(){
        return "--------test1----------";
    }
    @GetMapping("/test2")
    public String test2(){
        return "--------test2----------";
    }

    //链路测试  C和D两个请求都访问flowLimitService.common()方法，阈值到达后对C限流，对D不管
    @Resource
    private FlowLimitService flowLimitService;
    @GetMapping("/testC")
    public String testC()
    {
        flowLimitService.common();
        return "------testC";
    }
    @GetMapping("/testD")
    public String testD()
    {
        flowLimitService.common();
        return "------testD";
    }
    @GetMapping("/testE")
    public String testE(){
        System.out.println(System.currentTimeMillis()+"-------->testE，排队等待");
        return "TestE";
    }
    //新增熔断规则-慢调用比例
    @GetMapping("/testF")
    public String testF(){
        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("----测试:新增熔断规则-慢调用比例 ");
        return "--------testF新增熔断规则-慢调用比例-------";
    }
    //新增熔断规则-异常数比例
    @GetMapping("/testG")
    public String testG(){
        System.out.println("----测试:新增熔断规则-异常数比例 ");
        int age =1/0;
        return "-------testG,新增熔断规则-异常数比例---------";
    }
    //新增熔断规则-异常数
    @GetMapping("/testH")
    public String testH(){
        System.out.println("----测试:新增熔断规则-异常数 ");
        int age =1/0;
        return "-------testG,新增熔断规则-异常数---------";
    }

}
