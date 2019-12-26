package com.springboot.shiro.shiro2spboot.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLog implements Serializable {
    private Integer id;
//  操作用户名
    private String username;
//  操作类型
    private String actType;
//  操作时间
    private Date actTime;
//  操作参数
    private String actParams;
//  操作结果
    private String actResult;
//  客户机ip地址
    private String ipAddr;

    private static final long serialVersionUID = 1L;
}