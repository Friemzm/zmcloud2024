package com.zm.cloud.resp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)//@Accessors(chain = true)推荐使用链式编程
public class ResultData<T> {
    private String code;//结果状态 ,具体状态码参见枚举类ReturnCodeEnum.java
    private String message;
    private T data;
    private long timestamp;
    public ResultData(){
        this.timestamp = System.currentTimeMillis();
    }
    //成功的
    public static <T> ResultData <T> success(T data){
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCodeEnum.RC200.getCode());
        resultData.setMessage(ReturnCodeEnum.RC200.getMessage());
        resultData.setData(data);
        return resultData;
    }
    //失败的
    public static <T> ResultData <T> fail(String code,String message){
        ResultData resultData = new ResultData();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(null);
        return resultData;
    }
}
