package com.zm.cloud.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zm.cloud.entities.Pay;
import com.zm.cloud.entities.PayDTO;
import com.zm.cloud.resp.ResultData;
import com.zm.cloud.server.imp.PayServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Tag(name = "支付订单模块",description = "支付的CRUD")
public class PayController {
    @Resource
    private PayServiceImp payServiceImp;
    //增加数据@RequestBody包含请求体
    //summary大概说明，description具体描述
    @PostMapping(value = "/pay/add")
    @Operation(summary = "新增",description = "新增支付流水方法，json串做参数")
    public ResultData<String> addPay(@RequestBody Pay pay){
        System.out.println(pay.toString());
        int i = payServiceImp.add(pay);
        return ResultData.success("新增一个记录返回值："+i);
    }
    //删除
    @DeleteMapping(value = "/pay/del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData<String> delByID(@PathVariable("id") Integer id){
        int i = payServiceImp.delete(id);
        return ResultData.success("成功删除："+i);
    }
    //修改，但不能修改全部
    @PutMapping(value = "/pay/update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData<String> update(@RequestBody PayDTO payDTO){
        //创建真实体类对象pay，然后把payDTO的json串复制给它
        Pay pay = new Pay();
        BeanUtil.copyProperties(payDTO,pay);
        return ResultData.success("修改成功返回值："+payServiceImp.update(pay));
    }
    //通过id查询
    @GetMapping("/pay/get/{id}")
    @Operation(summary = "查询",description = "通过ID查询")
    public ResultData<Pay> payById(@PathVariable("id") Integer id){
        System.out.println("---------正在查询--------");
        //服务提供方业务处理，为了测试feign的超时时间控制
        try {
            TimeUnit.SECONDS.sleep(62);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        return ResultData.success(payServiceImp.getById(id));
    }
    //查询所有消息
    @GetMapping("/pay/getall")
    @Operation(summary = "查询所有",description = "查到所有简要信息")
    public ResultData<List<PayDTO>> getAll(){
        List<PayDTO> list = new ArrayList<>();
        for (Pay pay : payServiceImp.getAll()) {
            PayDTO payDTO = new PayDTO();
            BeanUtil.copyProperties(pay,payDTO);
            list.add(payDTO);
            System.out.println("查询到的消息："+ pay.toString());
        }
        return ResultData.success(list);
    }
    //测试从consul中获取配置信息
    @Value("${server.port}")
    private String port;

    @GetMapping("/pay/getInfo")
    public String getInfoByConsul(@Value("${zm.info}") String info){
        return "info：" + info +"port：" + port;
    }




}
