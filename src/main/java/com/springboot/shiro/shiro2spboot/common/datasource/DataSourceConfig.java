package com.springboot.shiro.shiro2spboot.common.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * springboot数据源配置
 * 1）创建Druid数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory
 * 3）配置事务管理器，除非需要使用事务，否则不用配置
 */
@Configuration // 该注解类似于spring配置文件
@MapperScan(basePackages = "com.zkyt.map.dao")
public class DataSourceConfig {

    @Autowired
    private Environment env;


    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */
    @Bean
    public DataSource mysqlDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.druid.mysql.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.druid.mysql.url"));
        props.put("username", env.getProperty("spring.datasource.druid.mysql.username"));
        props.put("password", env.getProperty("spring.datasource.druid.mysql.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource oracleDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.druid.oracle.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.druid.oracle.url"));
        props.put("username", env.getProperty("spring.datasource.druid.oracle.username"));
        props.put("password", env.getProperty("spring.datasource.druid.oracle.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@Autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            @Qualifier("oracleDataSource") DataSource oracleDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.mysql, mysqlDataSource);
        targetDataSources.put(DatabaseType.oracle, oracleDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(mysqlDataSource);// 默认的DataSource设置为oracleDataSource

        return dataSource;
    }

    /**
     * jdbc模板
     *
     * @param mysqlDataSource
     * @param oracleDataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            @Qualifier("oracleDataSource") DataSource oracleDataSource) {
        return new JdbcTemplate(this.dataSource(
                mysqlDataSource,
                oracleDataSource));
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            @Qualifier("oracleDataSource") DataSource oracleDataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
//		fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        fb.setDataSource(this.dataSource(
                mysqlDataSource,
                oracleDataSource));//解决上面配置产生的数据源循环依赖的问题
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));// 指定基包
        //配置mybatis返回Map是值为空时显示key
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        fb.setConfiguration(configuration);
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
//        配置数据库下划线转驼峰命名
        fb.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
