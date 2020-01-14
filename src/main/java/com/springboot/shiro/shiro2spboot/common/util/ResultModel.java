package com.springboot.shiro.shiro2spboot.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.common.util
 * @className ResultBean
 * @description 通用的数据返回，及数据获取异常处理，优化返回逻辑
 * @date 2020/1/14 8:41
 */
@Getter
@Setter
@ToString
@Slf4j
public class ResultModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //    执行成功结果代码
    public static final int SUCCESS_CODE = 1001;
    //    执行失败结果代码
    public static final int FAILED_CODE = 1002;
    //    成功返回信息
    public static final String SUCCESS_MSG = "操作成功";
    //    失败返回信息
    public static final String FAILED_MSG = "操作失败，程序出错";
    //    返回数据
    private T data;
    //    结果信息
    private String msg = SUCCESS_MSG;
    //    结果代码
    private int code = SUCCESS_CODE;
    //    成功标识
    private boolean flag = true;

    public ResultModel() {
        super();
    }

    public ResultModel(T data) {
        super();
        this.data = data;
    }

    public ResultModel(Throwable e) {
        super();
        this.flag = false;
        this.code = FAILED_CODE;
        this.msg = FAILED_MSG;
//        输出错误信息到日志
        log.error(e.toString());
    }
}
