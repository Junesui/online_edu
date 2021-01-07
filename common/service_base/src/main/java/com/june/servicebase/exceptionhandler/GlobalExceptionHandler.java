package com.june.servicebase.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.june.commonutils.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理
 * 
 * @author z
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// 全局异常处理
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public R error(Exception e) {
		log.error(e.getMessage(), e);
		return R.error().message("全局异常处理方法");
	}

	// 自定义异常处理
	@ExceptionHandler(CustomizeException.class)
	@ResponseBody
	public R error(CustomizeException e) {
		log.error(e.getMsg(), e);
		e.printStackTrace();
		return R.error().code(e.getCode()).message(e.getMsg());
	}

}
