package com.zjm.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjm.commonutils.ordervo.CourseWebVoOrder;
import com.zjm.commonutils.ordervo.UcenterMemberOrder;
import com.zjm.eduorder.client.EduClient;
import com.zjm.eduorder.client.UcenterClient;
import com.zjm.eduorder.entity.Order;
import com.zjm.eduorder.mapper.OrderMapper;
import com.zjm.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjm.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单 服务实现类
 * @author ZengJinming
 * @since 2020-04-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //创建订单
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

        //通过远程调用根据课程id获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);

        //更新课程销量
        eduClient.updateBuyCountById(order.getCourseId());

        //返回订单号
        return order.getOrderNo();
    }

    //获取当前用户订单列表
    @Override
    public List<Order> selectByMemberId(String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .orderByDesc("gmt_create")
                .eq("member_id", memberId);
        return baseMapper.selectList(queryWrapper);
    }

    //删除订单
    @Override
    public boolean removeById(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", orderId)
                .eq("member_id", memberId);
        return this.remove(queryWrapper);
    }

    //判断课程是否已经购买
    @Override
    public Boolean isBoughtByCourseId(String courseId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("member_id", memberId)
                .eq("course_id", courseId)
                .eq("status", 1);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count.intValue() > 0;
    }
}
