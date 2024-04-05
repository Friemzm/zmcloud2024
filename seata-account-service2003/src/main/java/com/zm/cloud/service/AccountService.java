package com.zm.cloud.service;

import feign.Param;

public interface AccountService {
    //扣减余额
    void  decrease(@Param("userId") Long userId, @Param("money") Long money);
}