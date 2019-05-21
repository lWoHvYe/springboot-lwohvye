package com.springboot.shiro.shiro2spboot.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（需要继承AbstractRoutingDataSource）
 * @author Administrator
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 保存一个线程安全的DatabaseType容器
	 */
	private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();
	
	static void setDatabaseType(DatabaseType type){
		contextHolder.set(type);
	}
//	设置默认数据源
	static void toDefault(){
		contextHolder.remove();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return contextHolder.get();
	}

}
