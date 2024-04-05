package com.zm.cloud.controller;

import com.zm.cloud.entities.Order;
import com.zm.cloud.resp.ResultData;
import com.zm.cloud.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;
    //创建订单
    @GetMapping("/order/create")
    public ResultData create(Order order){
        orderService.createOrder(order);
        return ResultData.success(order);
    }
}
