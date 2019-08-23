package com.springboot.shiro.shiro2spboot.config;

import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaFormAuthenticationFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * shiro过滤配置
 */
@Configuration
public class ShiroConfig {
    //    Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterMap = new LinkedHashMap<>();
//        配置不会被拦截的链接 顺序判断
        filterMap.put("/static/**", "anon");
//        配置获取验证码不拦截
        filterMap.put("/verify", "anon");
//        配置登出 具体登出已有shiro内部完成
        filterMap.put("/logout", "logout");
//        过滤器链，从上到下顺序执行，所以需要把/**放在最下面
//        authc:所有url都必须认证通过才可访问,anon所有url都可以匿名访问
        filterMap.put("/**", "authc");
//        登陆页
        shiroFilterFactoryBean.setLoginUrl("/login");
//        首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
//        错误页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        set filter role
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new CaptchaFormAuthenticationFilter());

        return shiroFilterFactoryBean;

    }

    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");//散列算法，这里使用md5算法
        credentialsMatcher.setHashIterations(2);//散列次数
        return credentialsMatcher;
    }

    //    加入自定义Realm
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm shiroRealm = new MyShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    //    配置Realm管理认证,返回类型建议设置为SessionsSecurityManager，需注意
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager);
        return attributeSourceAdvisor;
    }

    /**
     * 异常处理
     *
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver mappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
        mappings.setProperty("UnauthorizedException", "403");
        mappingExceptionResolver.setExceptionMappings(mappings);
        mappingExceptionResolver.setDefaultErrorView("error");
        mappingExceptionResolver.setExceptionAttribute("ex");
        return mappingExceptionResolver;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(true)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
