package com.zm.cloud.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.zm.cloud.apis.PayFeignApi;
import com.zm.cloud.entities.PayDTO;
import com.zm.cloud.resp.ResultData;
import com.zm.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class OrderController {
   @Resource
   private PayFeignApi payFeignApi;

   @PostMapping("/feign/pay/add")
   public ResultData addPay(@RequestBody PayDTO payDTO){
      System.out.println("1、使用本地addOrder新增订单功能(省略sql操作),2、开启addPay支付微服务远程调用");
      ResultData resultData = payFeignApi.addPay(payDTO);
      return resultData;
   }
   @GetMapping("/feign/pay/get/{id}")
   public ResultData getByID(@PathVariable("id") Integer id){
      System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
      ResultData payById = null;
      try {
         System.out.println("调用开始时间------>"+ DateUtil.now());
         payById = payFeignApi.getPayById(id);
      }catch (Exception e){
         e.printStackTrace();
         System.out.println("调用结束时间------>"+ DateUtil.now());
         ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
      }
      return payById;
   }
   @DeleteMapping("/feign/pay/del/{id}")
   public ResultData delById(@PathVariable("id") Integer id){
      System.out.println("-------支付微服务远程调用，按照id查删除订单支付流水信息");
      return payFeignApi.delById(id);
   }
   @PutMapping("/feign/pay/update")
   public ResultData update(@RequestBody PayDTO payDTO){
      System.out.println("-------支付微服务远程调用，修改订单支付流水信息");
      return payFeignApi.update(payDTO);
   }
   @GetMapping("/feign/pay/getall")
   public ResultData getAll(){
      return payFeignApi.getAll();
   }
   @GetMapping("/feign/pay/getInfo")
   public String mylb(){
      return payFeignApi.myLB();
   }

}
