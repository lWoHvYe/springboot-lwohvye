package com.lwohvye.springboot.dubboconsumer.controller;

import com.lwohvye.springboot.dubboconsumer.common.util.ResultModel;
import com.lwohvye.springboot.dubbointerface.common.util.PageUtil;
import com.lwohvye.springboot.dubbointerface.entity.Permission;
import com.lwohvye.springboot.dubbointerface.service.SysPermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference(version = "${lwohvye.service.version}")
    private SysPermissionService sysPermissionService;


    /**
     * @return com.lwohvye.springboot.dubboconsumer.common.util.ResultModel
     * @description 获取权限列表
     * @params [name, pageUtil]
     * @author Hongyan Wang
     * @date 2019/9/23 17:28
     */
    @RequestMapping(value = "/findPermission", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("permission:view")
    public ResultModel<PageUtil<Permission>> findByPermission(String name, PageUtil<Permission> pageUtil) {
//        JSONObject jsonObject = new JSONObject();
        return new ResultModel<>(sysPermissionService.findPermission(name, pageUtil));
//        jsonObject.put("result", "success");
//        jsonObject.put("list", pageUtil);
//        return jsonObject.toJSONString();
//        return pageUtil;
    }


    /**
     * 添加或修改权限
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "/savePermission", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("permission:add")
    public ResultModel<Integer> savePermission(@Valid Permission permission) {
//        JSONObject jsonObject = new JSONObject();
//        sysPermissionService.savePermission(permission);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysPermissionService.savePermission(permission));
    }


    /**
     * 删除权限
     *
     * @param permission
     * @return
     */
    @RequestMapping(value = "/deletePermission", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("permission:del")
    public ResultModel<Integer> deleteRole(Permission permission) {
//        JSONObject jsonObject = new JSONObject();
//        sysPermissionService.deletePermission(permission);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysPermissionService.deletePermission(permission));
    }


}
