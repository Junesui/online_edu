package com.zjm.eduucenter.mapper;

import com.zjm.eduucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 会员表 Mapper 接口
 * @author ZengJinming
 * @since 2020-04-09
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //查询某天注册人数
    Integer countRegister(String day);
}
