package com.zjm.servicebase.exceptionhandler;

import com.zjm.commonutils.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常
@Data
@AllArgsConstructor  //生成有参数构造方法
@NoArgsConstructor   //生成无参数构造
public class MSException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息

    public MSException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MSException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "MSException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}