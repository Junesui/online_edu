package com.zjm.aclservice.mapper;

import com.zjm.aclservice.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 权限 Mapper 接口
 * @author ZengJinming
 * @since 2020-04-16
 */
public interface PermissionMapper extends BaseMapper<Permission> {


    List<String> selectPermissionValueByUserId(String id);

    List<String> selectAllPermissionValue();

    List<Permission> selectPermissionByUserId(String userId);
}
