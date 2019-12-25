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

    private String username;

    private String actType;

    private Date actTime;

    private String ipAddr;

    private static final long serialVersionUID = 1L;
}