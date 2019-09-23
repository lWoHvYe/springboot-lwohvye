package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
     * @Description: 获取用户列表
     * @Param: [username, pageUtil]
     * @return: java.lang.String
     * @Author: Hongyan Wang
     * @Date: 2019/9/23 13:26
     */
    @RequestMapping("/findUser")
    @RequiresPermissions("user:view")
    @ResponseBody
    public String findByUsername(String username, PageUtil<User> pageUtil) {
        JSONObject jsonObject = new JSONObject();
//        查询列表
        userService.findUser(username, pageUtil);
        jsonObject.put("result", "success");
        jsonObject.put("list", pageUtil);
        return jsonObject.toJSONString();
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
     * @Description:
     * @Param: [user, roleId]
     * @return: java.lang.String
     * @Author: Hongyan Wang
     * @Date: 2019/9/23 13:27
     */
    @RequestMapping("/saveUser")
    @RequiresPermissions("user:add")
    @ResponseBody
    public String saveUser(User user, String roleId) {
        JSONObject jsonObject = new JSONObject();
        userService.saveUser(user, roleId);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }

    /**
     * 用户删除;
     *
     * @return
     */
    @RequestMapping("/delUser")
    @RequiresPermissions("user:del")//权限管理;
    public String delUser() {
        return "deleteUser";
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/deleteUser")
    @RequiresPermissions("user:del")
    @ResponseBody
    public String deleteUser(User user) {
        JSONObject jsonObject = new JSONObject();
        userService.deleteUser(user);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/test")
    @RequiresPermissions("user:*")
    public String test() {
        System.out.println("test");
        return "jsonTest";
    }

}
