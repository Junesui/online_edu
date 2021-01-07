package com.june.servicebase.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler{

	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("gmtCreate", new Date(), metaObject);
		this.setFieldValByName("gmtModified", new Date(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("gmtModified", new Date(), metaObject);
	}

}
