package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private SysRoleService sysRoleService;


    /**
     * @Description: 获取角色列表
     * @Param: [role, pageUtil]
     * @return: java.lang.String
     * @Author: Hongyan Wang
     * @Date: 2019/9/23 17:05
     */
    @RequestMapping(value = "/findRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("role:view")
//    @ResponseBody
    public String findByRole(String roleName, PageUtil<Role> pageUtil) {
        JSONObject jsonObject = new JSONObject();
        sysRoleService.findRole(roleName, pageUtil);
        jsonObject.put("result", "success");
        jsonObject.put("list", pageUtil);
        return jsonObject.toJSONString();
    }


    /**
     * 添加或修改角色
     *
     * @param role
     * @param permissionId 权限id，多个id使用逗号分开
     * @return
     */
    @RequestMapping(value = "/saveRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("role:add")
//    @ResponseBody
    public String saveRole(@Valid Role role, String permissionId) {
        JSONObject jsonObject = new JSONObject();
        sysRoleService.saveRole(role, permissionId);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


    /**
     * 删除角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/deleteRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("role:del")
//    @ResponseBody
    public String deleteRole(Role role) {
        JSONObject jsonObject = new JSONObject();
        sysRoleService.deleteRole(role);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


}
