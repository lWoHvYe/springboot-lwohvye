package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.UserService;
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
     * 获取用户列表
     *
     * @param response
     * @param username
     * @param curPage
     * @param pageSize
     * @param order
     * @return
     */
    @RequestMapping("/findUser")
    @RequiresPermissions("user:view")
    @ResponseBody
    public String findByUsername(HttpServletResponse response, String username, String curPage, String pageSize, String order) {
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
            order = "uid";
//        创建sort
        Sort sort = new Sort(Sort.Direction.DESC, order);
//        创建pageable
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> list = userService.findUser(username, pageable);
        jsonObject.put("result", "success");
        jsonObject.put("list", list);
//        解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
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
     * 添加或修改用户
     * @param response
     * @param user
     * @return
     */
    @RequestMapping("/saveUser")
    @RequiresPermissions("user:add")
    @ResponseBody
    public String saveUser(HttpServletResponse response, User user) {
        JSONObject jsonObject = new JSONObject();
        userService.save(user);
        jsonObject.put("result", "success");
//        解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
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
     * @param response
     * @param user
     * @return
     */
    @RequestMapping("/deleteUser")
    @RequiresPermissions("user:del")
    @ResponseBody
    public String deleteUser(HttpServletResponse response, User user) {
        JSONObject jsonObject = new JSONObject();
        userService.deleteUser(user);
        jsonObject.put("result", "success");
//        解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/test")
    @RequiresPermissions("user:*")
    public String test() {
        System.out.println("test");
        return "test";
    }

}
