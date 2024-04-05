package com.zm.cloud.controller;

import com.zm.cloud.resp.ResultData;
import com.zm.cloud.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Resource
    private AccountService accountService;
    //扣减账户余额
    @RequestMapping("/account/decrease")
    ResultData decrease(@RequestParam("userId") Long userId, @RequestParam("money") Long money){
        accountService.decrease(userId,money);
        return ResultData.success("扣减账户余额成功！");
    }
}