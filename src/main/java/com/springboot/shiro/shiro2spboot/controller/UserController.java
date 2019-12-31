package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.common.annotation.LogAnno;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "用户相关操作API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户查询.
     *
     * @return
     */
    @ApiIgnore
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
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表，可以通过用户名模糊查询，包含PageUtil分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String"),
            @ApiImplicitParam(name = "pageUtil", value = "分页相关实体类", dataType = "PageUtil")
    })
    @RequestMapping(value = "/findUser", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("user:view")
//    @ResponseBody
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
    @ApiIgnore
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
    @LogAnno(operateType = "添加用户")
    @ApiOperation(value = "添加新用户", notes = "添加新用户，包含用户的授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户实体类", required = true, dataType = "User"),
            @ApiImplicitParam(name = "roleId", value = "用户授权角色id", dataType = "String")
    })
    @RequestMapping(value = "/saveUser", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("user:add")
//    @ResponseBody
    public String saveUser(@Valid User user, String roleId) {
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
    @ApiIgnore
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
    @LogAnno(operateType = "删除用户")
    @ApiOperation(value = "删除指定用户", notes = "根据用户的id删除对应的用户与权限")
    @ApiImplicitParam(name = "uid", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("user:del")
//    @ResponseBody
    public String deleteUser(User user) {
        JSONObject jsonObject = new JSONObject();
        userService.deleteUser(user);
        jsonObject.put("result", "success");
        return jsonObject.toJSONString();
    }

    @ApiIgnore
    @RequestMapping("/testRole")
//    限定只有admin角色可以访问
    @RequiresRoles("admin")
    public String testRole() {
        return "success";
    }

}
