package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 获取权限列表
     *
     * @param response
     * @param name
     * @param curPage
     * @param pageSize
     * @param order
     * @return
     */
    @RequestMapping("/findPermission")
    @RequiresPermissions("permission:view")
    @ResponseBody
    public String findByPermission(HttpServletResponse response, String name, String curPage, String pageSize, String order) {
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
        List<Permission> list = permissionService.findPermission(name, pageable);
        jsonObject.put("result", "success");
        jsonObject.put("list", list);
//        解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        return jsonObject.toJSONString();
    }



    /**
     * 添加或修改权限
     * @param response
     * @param permission
     * @return
     */
    @RequestMapping("/savePermission")
    @RequiresPermissions("permission:add")
    @ResponseBody
    public String savePermission(HttpServletResponse response, Permission permission) {
        JSONObject jsonObject = new JSONObject();
        permissionService.savePermission(permission);
        jsonObject.put("result", "success");
//        解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        return jsonObject.toJSONString();
    }


    /**
     * 删除权限
     * @param response
     * @param permission
     * @return
     */
    @RequestMapping("/deletePermission")
    @RequiresPermissions("permission:del")
    @ResponseBody
    public String deleteRole(HttpServletResponse response, Permission permission) {
        JSONObject jsonObject = new JSONObject();
        permissionService.deletePermission(permission);
        jsonObject.put("result", "success");
//        解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        return jsonObject.toJSONString();
    }


}
