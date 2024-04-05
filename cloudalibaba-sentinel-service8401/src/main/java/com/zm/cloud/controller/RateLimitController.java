package com.zm.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {
    @GetMapping("/rateLimit/byUrl")
    public String byUrl()
    {
        return "按rest地址限流测试OK";
    }
    //自定义限流提示
    @GetMapping("/rateLimit/byResource")
    @SentinelResource(value = "byResourceSentinelResource",blockHandler = "diyBlockHandler")
    public String byResource(){
        return "这里是自定义限流测试，此时是正常访问页面没有问题的！";
    }
    public String diyBlockHandler(BlockException exception){
        return "你太快了大哥，慢一点，服务不可用@SentinelResource被触发了~~~~";
    }
    @GetMapping("/rateLimit/doAction/{p1}")
    @SentinelResource(value = "doActionSentinelResource",blockHandler = "doActionBlockHandler"
    ,fallback = "doActionFallback")
    public String doAction(@PathVariable("p1") Integer p1){
        if (p1 == 0) {
            throw new RuntimeException("p1等于零直接异常" );
        }
        return "------doAction正常访问-----";
    }
    //doActionBlockHandler自定义的限流提醒
   public String doActionBlockHandler(@PathVariable("p1") Integer p1,BlockException e){
       return "sentinel配置自定义被限流了";
   }
   //出现异常自定义服务降级
   public String doActionFallback(@PathVariable("p1") Integer p1,Throwable throwable){
        return "程序逻辑出现异常，这里是自定义的doActionFallback"+throwable.getMessage();
   }
   //热点规则
   @GetMapping("/testHos    tKey")
   @SentinelResource(value = "testHostKey",blockHandler = "hostKeyHandler")
    public String testHostKey(@RequestParam(value = "p1",required = false) String p1,
                              @RequestParam(value = "p2",required = false) String p2){
        return "------testHostKey正常访问-------"+"p1:"+p1+"p2:"+p2;
   }
   public String hostKeyHandler(String p1,String p2,BlockException exception){
        return "------hostKeyHandler限流提醒---------";
   }





}
