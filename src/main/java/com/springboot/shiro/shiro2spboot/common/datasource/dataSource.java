package com.springboot.shiro.shiro2spboot.common.datasource;

import java.lang.annotation.*;

/**
 * 多数据源用标识(枚举类)
 * 注解添加在类或方法上面
 * @dataSource(DatabaseType.master) 主库，只写不读
 * @dataSource(DatabaseType.slave) 从库，只读不写，可多从库
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface dataSource {
//    TODO 主从库配置，项目拆分成读和写两个项目，读项目默认数据源为从库，此处配置的未指定时，使用该注解，会使用的数据源，切换项目时可不改
    DatabaseType value() default DatabaseType.SLAVE;
}
