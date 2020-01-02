package com.springboot.shiro.shiro2spboot.common.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 数据源切换
 * 根据类或方法上的相关注解,切换数据源
 *
 * @author Administrator
 */
@Aspect
@Component
public class DataSourceAspect {

    /**
     * 使用空方法定义切点表达式
     * 切点在service层，所以持久层可以是jpa或者mybatis
     * 将切点从service层，改为有注解的位置，主要为实现只有切换了别的数据源，才在执行后切回默认，减少切换次数，execution使用了模糊匹配
     */
//    @Pointcut("execution(* com.springboot.shiro.shiro2spboot.service.*.*(..))")
    @Pointcut("@annotation(dataSource) || execution(* com.springboot.shiro.shiro2spboot.service.Sys*.*(..))")
    public void declareJointPointExpression() {
//	    空方法，不进行内容的定义
    }

    /**
     * 使用定义切点表达式的方法进行切点表达式的引入
     */
    @Before("declareJointPointExpression()")
    public void setDataSourceKey(JoinPoint point) {
//		springboot 2.x版本默认aop代理方式为cglib代理，故可以该方式获取接口实现类上的注解，这点与spring不同
        var signature = (MethodSignature) point.getSignature();
        var method = signature.getMethod();
//		获取注解
        var annotation = method.getAnnotation(dataSource.class);
//		方法上无注解时去类上获取
        if (annotation == null) {
            annotation = point.getTarget().getClass().getAnnotation(dataSource.class);
            if (annotation == null)
                return;
        }
        var dataSourceName = annotation.value();
        DynamicDataSource.setDatabaseType(dataSourceName);

    }

    /**
     * 方法执行结束后将数据源切换为默认
     */
    @AfterReturning("declareJointPointExpression()")
    public void addAfterReturningLogger() {
        DynamicDataSource.toDefault();
    }

}
