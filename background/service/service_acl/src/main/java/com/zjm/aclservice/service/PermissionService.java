package com.zjm.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjm.aclservice.entity.Permission;

import java.util.List;

/**
 * 权限 服务类
 * @author ZengJinming
 * @since 2020-04-16
 */
public interface PermissionService extends IService<Permission> {

    //获取全部菜单
    List<Permission> queryAllMenu();

    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);

    //给角色分配权限
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    //递归删除菜单
    void removeChildById(String id);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

    //获取全部菜单
    List<Permission> queryAllMenuMS();

    //递归删除菜单
    void removeChildByIdMS(String id);

    //给角色分配权限
    void saveRolePermissionRealtionShipMS(String roleId, String[] permissionId);
}
