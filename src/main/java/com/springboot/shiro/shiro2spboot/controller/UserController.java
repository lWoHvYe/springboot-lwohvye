package com.springboot.shiro.shiro2spboot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 用户查询.
     *
     * @return
     */
    @RequestMapping("/getUser")
    @RequiresPermissions("user:view")//权限管理;
    public String getUser() {
        return "userInfo";
    }

    /**
     * 用户添加;
     *
     * @return
     */
    @RequestMapping("/addUser")
    @RequiresPermissions("user:add")//权限管理;
    public String addUser() {
        return "addUser";
    }

    /**
     * 用户删除;
     *
     * @return
     */
    @RequestMapping("/deleteUser")
    @RequiresPermissions("user:del")//权限管理;
    public String deleteUser() {
        return "deleteUser";
    }

    @RequestMapping("/test")
    @RequiresPermissions("user:*")
    public String test() {
        System.out.println("test");
        return "test";
    }

}
