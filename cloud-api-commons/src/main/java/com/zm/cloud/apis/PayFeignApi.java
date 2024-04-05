package com.zm.cloud.apis;

import com.zm.cloud.entities.PayDTO;
import com.zm.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(value = "cloud-payment-service")
@FeignClient(value = "cloud-gateway")
public interface PayFeignApi {
    /**
     * 新增一条支付相关流水记录
     * @param payDTO
     * @return
     */
    @PostMapping("/pay/add")
    public ResultData addPay(@RequestBody PayDTO payDTO);
    //通过id查询
    @GetMapping(value = "/pay/get/{id}")
    public ResultData getPayById(@PathVariable("id") Integer id);
    //删除
    @DeleteMapping("/pay/del/{id}")
    public ResultData delById(@PathVariable("id") Integer integer);
    //修改
    @PutMapping("/pay/update")
    public ResultData update(@RequestBody PayDTO payDTO);
    //查全部
    @GetMapping("/pay/getall")
    public ResultData getAll();

    //openfeign天然支持负载均衡演示
    @GetMapping("/pay/getInfo")
    public String myLB();

    //=========Resilience4j CircuitBreaker 的例子
    @GetMapping("/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id);

    //=========Resilience4j bulkhead 的例子
    @GetMapping(value = "/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id);
    //=========Resilience4j ratelimit 的例子
    @GetMapping(value = "/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id);

    //网关测试
    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id);
    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo();


}
