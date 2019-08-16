package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 获取角色列表
     *
     * @param response
     * @param role
     * @param curPage
     * @param pageSize
     * @param order
     * @return
     */
    @RequestMapping("/findRole")
    @RequiresPermissions("role:view")
    @ResponseBody
    public String findByRole(HttpServletResponse response, String role, String curPage, String pageSize, String order) {
        JSONObject jsonObject = new JSONObject();
//        设置分页默认值
        int page = 0;
        int size = 10;
        if (!StringUtils.isEmpty(curPage))
            page = Integer.parseInt(curPage);
        if (!StringUtils.isEmpty(pageSize))
            size = Integer.parseInt(pageSize);
        if (StringUtils.isEmpty(order))
//            默认排序字段，根据数据结构修改
            order = "id";
//        创建sort
        Sort sort = new Sort(Sort.Direction.DESC, order);
//        创建pageable
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Role> list = roleService.findRole(role, pageable);
        jsonObject.put("result", "success");
        jsonObject.put("list", list);
        return jsonObject.toJSONString();
    }



    /**
     * 添加或修改角色
     * @param response
     * @param role
     * @param permissionId 权限id，多个id使用逗号分开
     * @return
     */
    @RequestMapping("/saveRole")
    @RequiresPermissions("role:add")
    @ResponseBody
    public String saveRole(HttpServletResponse response, Role role, String permissionId) {
        JSONObject jsonObject = new JSONObject();
        roleService.saveRole(role,permissionId);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


    /**
     * 删除角色
     * @param response
     * @param role
     * @return
     */
    @RequestMapping("/deleteRole")
    @RequiresPermissions("role:del")
    @ResponseBody
    public String deleteRole(HttpServletResponse response, Role role) {
        JSONObject jsonObject = new JSONObject();
        roleService.deleteRole(role);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }


}
