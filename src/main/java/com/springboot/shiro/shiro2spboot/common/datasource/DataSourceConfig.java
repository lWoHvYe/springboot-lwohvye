//package com.springboot.shiro.shiro2spboot.common.datasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import com.github.pagehelper.PageInterceptor;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Objects;
//import java.util.Properties;
//
//
///**
// * springboot数据源配置
// * 1）创建Druid数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
// * 2）创建SqlSessionFactory
// * 3）配置事务管理器，除非需要使用事务，否则不用配置
// */
//@Configuration // 该注解类似于spring配置文件
//@MapperScan(basePackages = "com.springboot.shiro.shiro2spboot.dao")
//public class DataSourceConfig {
//
//    @Autowired
//    private Environment env;
//
//
//    /**
//     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
//     *
//     * @Primary 注解用于标识默认使用的 DataSource Bean，因为有三个 DataSource Bean，该注解可用于 master(主数据源)
//     * 或 slave(副数据源) DataSource Bean, 但不能用于 dynamicDataSource Bean, 否则会产生循环调用
//     * 使用@ConfigurationProperties的prefix参数设置要匹配的属性的前缀
//     */
//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
//    public DataSource masterDataSource() {
//        return new DruidDataSource();
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
//    public DataSource slaveDataSource() {
//        return new DruidDataSource();
//    }
//
//    /**
//     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@Autowire注解报错
//     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
//     */
//    @Bean
//    public DynamicDataSource dataSource(
//            @Qualifier("masterDataSource") DataSource masterDataSource,
//            @Qualifier("slaveDataSource") DataSource slaveDataSource) {
//        var targetDataSources = new HashMap<>();
//        targetDataSources.put(DatabaseType.MASTER, masterDataSource);
//        targetDataSources.put(DatabaseType.SLAVE, slaveDataSource);
//
//        var dataSource = new DynamicDataSource();
//        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
////        TODO 主从库配置，项目拆分成读和写两个项目，读项目默认数据源为从库
//        dataSource.setDefaultTargetDataSource(masterDataSource);// 默认的DataSource设置为masterDataSource
//
//        return dataSource;
//    }
//
//    /**
//     * jdbc模板
//     *
//     * @param dataSource
//     * @return
//     */
//    @Bean
//    public JdbcTemplate jdbcTemplate(DynamicDataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    /**
//     * 根据数据源创建SqlSessionFactory
//     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dataSource) throws Exception {
//        var factoryBean = new SqlSessionFactoryBean();
////		fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
//        factoryBean.setDataSource(dataSource);//解决上面配置产生的数据源循环依赖的问题
//        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        factoryBean.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));// 指定基包
//        //配置mybatis返回Map是值为空时显示key
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setCallSettersOnNulls(true);
//        factoryBean.setConfiguration(configuration);
//        factoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources(Objects.requireNonNull(env.getProperty("mybatis.mapper-locations"))));
////        配置数据库下划线转驼峰命名
//        Objects.requireNonNull(factoryBean.getObject()).getConfiguration().setMapUnderscoreToCamelCase(true);
//        return factoryBean.getObject();
//    }
//
//    /**
//     * @description 线程安全
//     * @params [sqlSessionFactory]
//     * @return org.mybatis.spring.SqlSessionTemplate
//     * @author Hongyan Wang
//     * @date 2020/1/3 13:23
//     */
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    /**
//     * 配置事务管理器
//     */
//    @Bean
//    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * @return
//     * @description 配置一个管理后台的Servlet
//     * @params
//     * @author Hongyan Wang
//     * @date 2020/1/3 10:09
//     */
//    @Bean
//    public ServletRegistrationBean<StatViewServlet> druidServlet() {
//        var servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
//        servletRegistrationBean.setServlet(new StatViewServlet());
//        servletRegistrationBean.addUrlMappings("/druid/*");
//        var initParameters = new HashMap<String, String>();
//        initParameters.put("loginUsername", "admin");// 用户名
//        initParameters.put("loginPassword", "admin");// 密码
//        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
//        initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
//        initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
//        servletRegistrationBean.setInitParameters(initParameters);
//        return servletRegistrationBean;
//    }
//
//    /**
//     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
//     * @description 配置一个Web监控的filter
//     * @params []
//     * @author Hongyan Wang
//     * @date 2020/1/3 10:10
//     */
//    @Bean
//    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
//        var filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        filterRegistrationBean.addInitParameter("profileEnable", "true");
//        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
//        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
//        filterRegistrationBean.addInitParameter("DruidWebStatFilter", "/*");
//        return filterRegistrationBean;
//    }
//
//
//}
