package com.springboot.shiro.shiro2spboot.common.aop;

import com.springboot.shiro.shiro2spboot.common.annotation.LogAnno;
import com.springboot.shiro.shiro2spboot.common.util.HttpContextUtil;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.entity.UserLog;
import com.springboot.shiro.shiro2spboot.service.UserLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * AOP实现日志
 *
 * @author Hongyan Wang
 */
@Order(3)
@Component
@Aspect
public class LogAopAspect {

    @Autowired
    private UserLogService userLogService;

    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.springboot.shiro.shiro2spboot.common.annotation.LogAnno)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        // 获取操作描述的属性值
        String operateType = logAnno.operateType();
        // 创建一个日志对象(准备记录日志)
        UserLog log = new UserLog();
        log.setActType(operateType);// 操作说明

        try {
            // 设置操作人，从session中获取
            HttpSession session = HttpContextUtil.getRequest().getSession();
            User curUser = (User) session.getAttribute("curUser");
            if (curUser != null )
            log.setUsername(curUser.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String ip = HttpContextUtil.getIpAddress();
        log.setIpAddr(ip);
        // 让代理方法执行
        Object result = pjp.proceed();
        userLogService.insertSelective(log);// 添加日志记录
        return result;
    }

}