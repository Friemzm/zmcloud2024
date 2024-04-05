package com.zm.cloud.mapper;

import com.zm.cloud.entities.Account;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AccountMapper extends Mapper<Account> {
    //扣减账户余额
    void  decrease(@Param("userId") Long userId, @Param("money") Long money);
}