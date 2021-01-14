package com.zjm.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjm.commonutils.util.JwtInfo;
import com.zjm.commonutils.util.JwtUtils;
import com.zjm.commonutils.result.R;
import com.zjm.eduorder.entity.Order;
import com.zjm.eduorder.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单 前端控制器
 * @author ZengJinming
 * @since 2020-04-12
 */
@Api(description="订单服务相关")
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //1 生成订单的方法
    @ApiOperation(value = "生成订单")
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrders(courseId, jwtInfo.getId());
        return R.success().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @ApiOperation(value = "根据订单id查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.success().data("item",order);
    }

    //根据课程id'和用户id查询订单表中订单的状态 status
//    @ApiOperation(value = "根据课程id和用户id查询订单表中订单的状态")
//    @GetMapping("isBought/{courseId}/{memberId}")
//    public boolean isBoughtCourse(@PathVariable String courseId,
//                                  @PathVariable String memberId){
//        QueryWrapper<Order> wrapper = new QueryWrapper<>();
//        wrapper.eq("course_id",courseId);
//        wrapper.eq("member_id",memberId);
//        wrapper.eq("status",1);  //支付状态  1已支付
//        int count = orderService.count(wrapper);
//        if (count>0){
//            return true;
//        }else {
//            return false;
//        }
//    }

    //查询订单表中订单的状态 判断课程是否购买
    @ApiOperation( "判断课程是否购买")
    @GetMapping("isBought/{courseId}")
    public R isBuyByCourseId(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBought = orderService.isBoughtByCourseId(courseId, JwtUtils.getMemberIdByJwtToken(request).getId());
        return R.success().data("isBought", isBought);
    }

    //获取当前用户订单列表
    @ApiOperation(value = "获取当前用户订单列表")
    @GetMapping("orderList")
    public R list(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<Order> list = orderService.selectByMemberId(jwtInfo.getId());
        return R.success().data("items", list);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("removeOrder/{orderId}")
    public R remove(@PathVariable String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = orderService.removeById(orderId, jwtInfo.getId());
        if(result){
            return R.success().message("删除订单成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

}

