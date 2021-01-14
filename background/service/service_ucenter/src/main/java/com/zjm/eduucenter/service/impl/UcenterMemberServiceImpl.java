package com.zjm.eduucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjm.commonutils.result.ResultCodeEnum;
import com.zjm.commonutils.util.FormUtils;
import com.zjm.commonutils.util.JwtInfo;
import com.zjm.commonutils.util.JwtUtils;
import com.zjm.commonutils.util.MD5;
import com.zjm.eduucenter.entity.UcenterMember;
import com.zjm.eduucenter.entity.vo.ChangeVo;
import com.zjm.eduucenter.entity.vo.LoginVo;
import com.zjm.eduucenter.entity.vo.RegisterVo;
import com.zjm.eduucenter.mapper.UcenterMemberMapper;
import com.zjm.eduucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjm.servicebase.exceptionhandler.MSException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 会员表 服务实现类
 * @author ZengJinming
 * @since 2020-04-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录的方法
    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验：参数是否合法
        if(StringUtils.isEmpty(mobile)
                || !FormUtils.isMobile(mobile)
                || StringUtils.isEmpty(password)){
            throw new MSException(ResultCodeEnum.PARAM_ERROR);
        }

        //校验手机号是否存在
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        if(member == null){
            throw new MSException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }

        //校验密码是否正确
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new MSException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //校验用户是否被禁用
        if(member.getIsDisabled()){
            throw new MSException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //登录：生成token
        JwtInfo info = new JwtInfo();
        info.setId(member.getId());
        info.setNickname(member.getNickname());
        info.setAvatar(member.getAvatar());

        String jwtToken = JwtUtils.getJwtToken(info, 1800);

        return jwtToken;
    }
//    @Override
//    public String login(UcenterMember member) {
//        //获取登录手机号和密码
//        String mobile = member.getMobile();
//        String password = member.getPassword();
//
//        //手机号和密码非空判断
//        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//            throw new MSException(20001,"登录失败");
//        }
//
//        //判断手机号是否正确
//        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
//        wrapper.eq("mobile",mobile);
//        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
//        //判断查询对象是否为空
//        if(mobileMember == null) {//没有这个手机号
//            throw new MSException(20001,"登录失败");
//        }
//
//        //判断密码
//        //因为存储到数据库密码肯定加密的
//        //把输入的密码进行加密，再和数据库密码进行比较
//        //加密方式 MD5
//        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
//            throw new MSException(20001,"登录失败");
//        }
//
//        //判断用户是否禁用
//        if(mobileMember.getIsDisabled()) {
//            throw new MSException(20001,"登录失败");
//        }
//
//        //登录成功
//        //生成token字符串，使用jwt工具类
//        JwtInfo info = new JwtInfo();
//        info.setId(member.getId());
//        info.setNickname(member.getNickname());
//        info.setAvatar(member.getAvatar());
//
//        String jwtToken = JwtUtils.getJwtToken(info, 1800);
//        return jwtToken;
//    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据 校验参数
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new MSException(20001,"注册失败！");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new MSException(20001,"验证🐎有误！注册失败！");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new MSException(20001,"该手机号已注册！注册失败！");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要j进行MD5加密
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://guli-file-191125.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(member);
    }

    //根据openid判断是否有相同微信数据
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询某天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegister(day);
    }

    //更改密码
    @Override
    public void changePasswd(ChangeVo changeVo) {
        //获取注册的数据 校验参数
        String code = changeVo.getCode(); //验证码
        String mobile = changeVo.getMobile(); //手机号
        String password = changeVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)) {
            throw new MSException(20001,"修改密码失败！");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new MSException(20001,"验证🐎有误！修改密码失败！");
        }

        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(changeVo,ucenterMember);
        int update = baseMapper.updateById(ucenterMember);
        if(update == 0) {
            throw new MSException(20001,"修改密码失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));//密码需要进行MD5加密
        baseMapper.updateById(member);


    }
}
