package com.lwohvye.springboot.dubboconsumer.controller;

import com.github.pagehelper.PageInfo;
import com.lwohvye.springboot.dubboconsumer.common.util.ResultModel;
import com.lwohvye.springboot.dubbointerface.entity.UserLog;
import com.lwohvye.springboot.dubbointerface.service.UserLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongyan Wang
 * @packageName
 * @className UserLogController
 * @description
 * @date 2019/12/18 16:42
 */
@RestController
@RequestMapping("/userLog")
public class UserLogController {

    @Reference(version = "${lwohvye.service.version}")
    private UserLogService userLogService;

    /**
     * @return com.springboot.shiro.shiro2spboot.common.util.ResultModel
     * @description 获取日志列表
     * @params [username, searchTime, pages, limits]
     * @author Hongyan Wang
     * @date 2019/12/18 16:54
     */
    @ApiOperation(value = "获取日志列表", notes = "获取日志列表，包含根据用户名及操作时间分页查询")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
//    @ResponseBody
    public ResultModel<PageInfo<UserLog>> list(String username, String searchTime, int page, int pageSize) {
//        JSONObject json = new JSONObject();
//
        String startDate = null;
        String endDate = null;
        if (!StringUtils.isEmpty(searchTime)) {
            String[] searchTimes = searchTime.split(" - ");
            startDate = searchTimes[0];
            endDate = searchTimes[1];
        }
//
//        PageInfo<UserLog> pageInfo = userLogService.list(username, startDate, endDate, page, pageSize);
//
//        json.put("flag", true);
//        json.put("result", pageInfo);
//        return json.toJSONString();
        return new ResultModel<>(userLogService.list(username, startDate, endDate, page, pageSize));
    }

}
