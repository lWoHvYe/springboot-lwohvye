package com.lwohvye.springboot.dubboconsumer.controller;

import com.lwohvye.springboot.dubboconsumer.common.util.ResultModel;
import com.lwohvye.springboot.dubbointerface.common.util.PageUtil;
import com.lwohvye.springboot.dubbointerface.entity.Role;
import com.lwohvye.springboot.dubbointerface.service.SysRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference(version = "${lwohvye.service.version}")
    private SysRoleService sysRoleService;


    /**
     * @return com.springboot.shiro.shiro2spboot.common.util.ResultModel
     * @description 获取角色列表
     * @params [role, pageUtil]
     * @author Hongyan Wang
     * @date 2019/9/23 17:05
     */
    @RequestMapping(value = "/findRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("role:view")
    public ResultModel<PageUtil<Role>> findByRole(String roleName, PageUtil<Role> pageUtil) {
//        JSONObject jsonObject = new JSONObject();
        return new ResultModel<>(sysRoleService.findRole(roleName, pageUtil));
//        jsonObject.put("result", "success");
//        jsonObject.put("list", pageUtil);
//        return jsonObject.toJSONString();
//        return pageUtil;
    }

    /**
     * @return com.springboot.shiro.shiro2spboot.common.util.ResultModel<java.lang.Integer>
     * @description 添加或修改角色
     * @params [role, permissionId]
     * @author Hongyan Wang
     * @date 2020/1/14 10:46
     */
    @ApiOperation(value = "添加或修改角色", notes = "根据角色是否存在进行添加或修改")
    @ApiImplicitParam(name = "permissionId", value = "权限id，多个id使用逗号分隔", dataType = "String")
    @RequestMapping(value = "/saveRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("role:add")
    public ResultModel<Integer> saveRole(@Valid Role role, String permissionId) {
//        JSONObject jsonObject = new JSONObject();
//        sysRoleService.saveRole(role, permissionId);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysRoleService.saveRole(role, permissionId));
    }


    /**
     * @return com.springboot.shiro.shiro2spboot.common.util.ResultModel<java.lang.Integer>
     * @description 删除角色
     * @params [role]
     * @author Hongyan Wang
     * @date 2020/1/14 10:45
     */
    @RequestMapping(value = "/deleteRole", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("role:del")
    public ResultModel<Integer> deleteRole(Role role) {
//        JSONObject jsonObject = new JSONObject();
//        sysRoleService.deleteRole(role);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysRoleService.deleteRole(role));
    }


}
