package com.zm.cloud.controller;

import com.zm.cloud.entities.PayDTO;
import com.zm.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {
    //先写死，支付模块的地址
    //private static final String PAY_MAN_SERVER = "http://localhost:8001";
    private static final String PAY_MAN_SERVER = "http://cloud-payment-service";
    @Autowired
    private RestTemplate restTemplate;
    //添加订单
    //一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求
    //我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者
    //参数可以不添加@RequestBody
    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){

        return restTemplate.postForObject(PAY_MAN_SERVER + "/pay/add",payDTO,ResultData.class);
    }
    //通过id查询
    @GetMapping("/consumer/pay/gat/{id}")
    public ResultData getOrderById(@PathVariable("id") Integer id){
        System.out.println("-----通过id查询----");
        return restTemplate.getForObject(PAY_MAN_SERVER + "/pay/get/"+id,ResultData.class,id);
    }
    //修改
    @GetMapping("/consumer/pay/update")
    public ResultData updateOrder(PayDTO payDTO){
        return restTemplate.postForObject(PAY_MAN_SERVER + "/pay/update",payDTO,ResultData.class);
    }
    //删除
    @GetMapping("/consumer/pay/del/{id}")
    public ResultData delById(@PathVariable("id") Integer id){
        return restTemplate.postForObject(PAY_MAN_SERVER + "/pay/del/"+id,id,ResultData.class);
    }
    //查询所有
    @GetMapping("/consumer/pay/getall")
    public ResultData getAll(){
        System.out.println("----80查所有----");
        return restTemplate.getForObject(PAY_MAN_SERVER + "/pay/getall",ResultData.class);
    }

    //负载均衡测试
    @GetMapping("/consumer/pay/getInfo")
    public String getInfo(){
        return restTemplate.getForObject(PAY_MAN_SERVER + "/pay/getInfo",String.class);
    }
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery(){
        //获取注册中心的服务列表
        List<String> services = discoveryClient.getServices();
        System.out.println("获取到的服务有："+"\n");
        for (String service : services) {
            System.out.println(service);
        }
        System.out.println("----------------------------");
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return instances.get(0).getServiceId()+"--port:"+instances.get(0).getPort();
    }



}
