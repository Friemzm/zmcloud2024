package com.zm.cloud.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.zm.cloud.entities.Pay;
import com.zm.cloud.resp.ResultData;
import com.zm.cloud.server.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@RestController
public class GateWayController {
    @Resource
    PayService payService;

    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData<Pay> getById(@PathVariable("id") Integer id)
    {
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo()
    {
        return ResultData.success("gateway info test："+ IdUtil.simpleUUID());
    }

    //测试The AddRequestHeader GatewayFilter Factory
    @GetMapping("/pay/gateway/filter")
    public ResultData<String> getGatewayFilter(HttpServletRequest request){
        String result = "";
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headName = headers.nextElement();
            String headerValue = request.getHeader(headName);
            System.out.println("请求头名："+headName+"\t\t\t"+"请求值："+headerValue);
            if (headName.equalsIgnoreCase("X-Request-zm1") || headName.equalsIgnoreCase("X-Request-zm2")){
                result = result + headName + "\t" + headerValue +" ";
            }
        }
        System.out.println("----------------------------------");
        String customerID = request.getParameter("customerID");
        System.out.println("参数customerID："+customerID);
        System.out.println("----------------------------------");
        String customerName = request.getParameter("customerName");
        System.out.println("参数customerName："+customerName);
        System.out.println("----------------------------------");

        return ResultData.success("getGatewayFilter过滤器test："+result+" \t "+ DateUtil.now());
    }


}
