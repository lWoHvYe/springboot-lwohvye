package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
    * @Description: 获取权限列表
    * @Param: [name, pageUtil]
    * @return: java.lang.String
    * @Author: Hongyan Wang
    * @Date: 2019/9/23 17:28
    */
    @RequestMapping("/findPermission")
    @RequiresPermissions("permission:view")
    @ResponseBody
    public String findByPermission(String name, PageUtil<Permission> pageUtil) {
        JSONObject jsonObject = new JSONObject();
        permissionService.findPermission(name, pageUtil);
        jsonObject.put("result", "success");
        jsonObject.put("list", pageUtil);
        return jsonObject.toJSONString();
    }



    /**
     * 添加或修改权限
     * @param permission
     * @return
     */
    @RequestMapping("/savePermission")
    @RequiresPermissions("permission:add")
    @ResponseBody
    public String savePermission(Permission permission) {
        JSONObject jsonObject = new JSONObject();
        permissionService.savePermission(permission);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


    /**
     * 删除权限
     * @param permission
     * @return
     */
    @RequestMapping("/deletePermission")
    @RequiresPermissions("permission:del")
    @ResponseBody
    public String deleteRole(Permission permission) {
        JSONObject jsonObject = new JSONObject();
        permissionService.deletePermission(permission);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


}
