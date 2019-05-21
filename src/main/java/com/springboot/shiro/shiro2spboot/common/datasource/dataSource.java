package com.springboot.shiro.shiro2spboot.common.datasource;

import java.lang.annotation.*;

/**
 * 多数据源用标识(枚举类)
 * 注解添加在类或方法上面
 * @dataSource(DatabaseType.mysql)
 * @dataSource(DatabaseType.oracle)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface dataSource {
    DatabaseType value() default DatabaseType.mysql;
}
