package com.zm.cloud.service;

public interface StorageService {
    //扣库存
    void decrease(Long productId, Integer count);
}