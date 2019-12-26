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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        // 获取方法名
        String methodName = method.getName();
        // 获取类名
        String targetName = pjp.getTarget().getClass().getName();
        // 获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        // 获取操作描述的属性值
        String operateType = logAnno.operateType();
        // 创建一个日志对象(准备记录日志)
        UserLog log = new UserLog();
        StringBuilder actType = new StringBuilder();
        actType.append("类名 : ").append(targetName).append(" : 方法名 : ").append(methodName);
        if (!StringUtils.isEmpty(operateType))
            actType.append(" : 方法描述 : ").append(operateType);
        log.setActType(actType.toString());// 操作说明
//        设置方法参数
        String param = getStrParams(pjp, methodSignature);
        log.setActParams(param);

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
//        记录执行结果
        log.setActResult((String) result);
        userLogService.insertSelective(log);// 添加日志记录
        return result;
    }

    /**
     * @description 获取方法的参数
     * @params [pjp, methodSignature]
     * @return java.lang.String
     * @author Hongyan Wang
     * @date 2019/12/26 10:19
     */
    @NotNull
    private String getStrParams(ProceedingJoinPoint pjp, MethodSignature methodSignature) {
        String[] paraNames = methodSignature.getParameterNames();
        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        if (paraNames != null && paraNames.length > 0 && args != null && args.length > 0) {
            for (int i = 0; i < paraNames.length; i++) {
                sb.append(paraNames[i]).append(":").append(args[i]).append(",");
            }
        }
        sb.delete(sb.length() - 1, sb.length() + 1);
        return sb.toString();
    }

}