package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.entity.UserLog;
import com.springboot.shiro.shiro2spboot.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Hongyan Wang
 * @packageName
 * @className UserLogController
 * @description
 * @date 2019/12/18 16:42
 */
@Controller
@RequestMapping("/userLog")
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    /**
    * @description 获取日志列表
    * @params [username, searchTime, pages, limits]
    * @return java.lang.String
    * @author Hongyan Wang
    * @date 2019/12/18 16:54
    */
    @RequestMapping("/list")
    @ResponseBody
    public String list(String username, String searchTime, int pages, int limits) {
        JSONObject json = new JSONObject();

        String startDate = null;
        String endDate = null;
        if (!StringUtils.isEmpty(searchTime)) {
            String[] searchTimes = searchTime.split(" - ");
            startDate = searchTimes[0];
            endDate = searchTimes[1];
        }

        List<UserLog> list = userLogService.list(username,startDate,endDate,pages,limits);
        int count = userLogService.selectCount(username,startDate,endDate);

        json.put("count", count);
        json.put("curPage", pages);
        json.put("totalPage", getPageCount(count, limits));
        json.put("flag", true);
        json.put("list", list);
        return json.toJSONString();
    }

    private int getPageCount(int recordCount, int pageSize) {
        int size = recordCount / pageSize;// 总条数/每页显示的条数=总页数
        int mod = recordCount % pageSize;// 最后一页的条数
        if (mod != 0)
            size++;
        return recordCount == 0 ? 1 : size;
    }

}
