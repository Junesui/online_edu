package com.zjm.servicebase.exceptionhandler;


import com.zjm.commonutils.util.ExceptionUtil;
import com.zjm.commonutils.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("有异常");
    }

    //特定异常   先找特定异常  再找全局异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常
    @ExceptionHandler(MSException.class)
    @ResponseBody //为了返回数据
    public R error(MSException e) {
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
