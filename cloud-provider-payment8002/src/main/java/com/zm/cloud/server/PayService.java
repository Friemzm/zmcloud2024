package com.zm.cloud.server;

import com.zm.cloud.entities.Pay;

import java.util.List;

public interface PayService {
    //添加
    public int add(Pay pay);
    public int delete(Integer id);
    public int update(Pay pay);
    public Pay getById(Integer id);
    public List<Pay> getAll();

}
