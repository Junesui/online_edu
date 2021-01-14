package com.zjm.eduorder.service;

import com.zjm.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 支付日志表 服务类
 * @author ZengJinming
 * @since 2020-04-12
 */
public interface PayLogService extends IService<PayLog> {

    //生成微信支付二维码接口
    Map createQrCode(String orderNo);

    //添加记录到支付表,更新订单表订单状态
    void updateOrdersStatus(Map<String, String> map);

    //根据订单号查询 支付状态
    Map<String, String> queryPayStatus(String orderNo);
}
