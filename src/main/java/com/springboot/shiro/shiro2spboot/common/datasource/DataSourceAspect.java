package com.springboot.shiro.shiro2spboot.common.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切换
 * 根据类或方法上的相关注解,切换数据源
 * @author Administrator
 *
 */
@Aspect
@Component
public class DataSourceAspect {

	/**
	 * 使用空方法定义切点表达式
	 * 切点在service层，所以持久层可以是jpa或者mybatis
	 */
	@Pointcut("execution(* com.springboot.shiro.shiro2spboot.service.*.*(..))")
	public void declareJointPointExpression() {
	}
	
	/**
	 * 使用定义切点表达式的方法进行切点表达式的引入
	 */
	@Before("declareJointPointExpression()")
	public void setDataSourceKey(JoinPoint point) {
//		springboot 2.x版本默认aop代理方式为cglib代理，故可以该方式获取接口实现类上的注解，这点与spring不同
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
//		获取注解
		dataSource annotation = method.getAnnotation(dataSource.class);
//		方法上无注解时去类上获取
		if (annotation==null){
			annotation=point.getTarget().getClass().getAnnotation(dataSource.class);
			if (annotation==null)
				return;
		}
		DatabaseType dataSourceName = annotation.value();
			DynamicDataSource.setDatabaseType(dataSourceName);

	}
	/**
	 * 方法执行结束后将数据源切换为默认
	 */
	@AfterReturning("declareJointPointExpression()")
	public void addAfterReturningLogger(JoinPoint point){
		DynamicDataSource.toDefault();
	}
	
}
