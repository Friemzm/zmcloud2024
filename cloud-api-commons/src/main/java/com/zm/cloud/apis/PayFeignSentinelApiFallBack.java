package com.zm.cloud.apis;

import com.zm.cloud.resp.ResultData;
import com.zm.cloud.resp.ReturnCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class PayFeignSentinelApiFallBack implements PayFeignSentinelApi{
    @Override
    public ResultData getPayByOrderNo(String orderNo) {
        return ResultData.fail(ReturnCodeEnum.RC500.getCode(), "服务不可用，fallback服务降级了");
    }
}
