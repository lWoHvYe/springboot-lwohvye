package com.springboot.shiro.shiro2spboot.common.shiro;

import com.springboot.shiro.shiro2spboot.common.util.HttpContextUtil;
import com.springboot.shiro.shiro2spboot.common.util.VerifyCodeUtils;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.entity.UserLog;
import com.springboot.shiro.shiro2spboot.service.UserLogService;
import com.springboot.shiro.shiro2spboot.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;

//实现AuthorizingRealm接口用户认证
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserLogService userLogService;

    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @description 用户授权
     * @params [principalCollection]
     * @author Hongyan Wang
     * @date 2019/10/12 16:26
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        获取用户信息
        var primaryPrincipal = principalCollection.getPrimaryPrincipal();
//        添加角色和权限
        var authorizationInfo = new SimpleAuthorizationInfo();
        if (primaryPrincipal instanceof User) {
            var user = (User) primaryPrincipal;
            var role = user.getRoles();
//            添加角色
            authorizationInfo.addRole(role.getRoleName());
            for (var permission : role.getPermissions()) {
//                添加权限
                authorizationInfo.addStringPermission(permission.getPermissionStr());
            }
        }
        log.info("授权 ：Shiro认证成功");
        return authorizationInfo;
    }

    /**
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     * @description 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
//        Post请求先进行认证，再到请求
        if (authenticationToken.getPrincipal() != null) {
//            转为CaptchaToken
            var captchaToken = (CaptchaToken) authenticationToken;
//            获取页面输入的验证码
            var captchaCode = captchaToken.getCaptchaCode();
//            验证码为空，抛出相应异常
            if (StringUtils.isEmpty(captchaCode))
                throw new CaptchaEmptyException();

            // 从session获取正确的验证码
            var session = SecurityUtils.getSubject().getSession();
            var sessionCaptchaCode = (String) session.getAttribute(VerifyCodeUtils.VERIFY_CODE_SESSION_KEY);
//            var sessionCaptchaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

//            验证码错误，抛出相应异常
            if (!captchaCode.equalsIgnoreCase(sessionCaptchaCode))
                throw new CaptchaErrorException();

//          获取用户名
            var username = ((CaptchaToken) authenticationToken).getUsername();
            var password = ((CaptchaToken) authenticationToken).getPassword();
//            根据用户名获取用户信息
            var user = sysUserService.findLoginUser(username);
//            用户不存在，抛出相应异常
            if (user == null)
                throw new UnknownAccountException();
            log.info("认证 ：Shiro认证成功");

//            将用户信息放入session中
            session.setAttribute("curUser", user);
//            加入日志中
            UserLog log = new UserLog();
            log.setActType("类名 : com.springboot.shiro.shiro2spboot.controller.LoginController ; 方法名 : login ; 方法描述 : 登陆系统");// 操作说明
            log.setUsername(user.getUsername());
//            获取并设置参数
            String actParams = " 用户名 : " + username + " : 密码(用户名+密码倒叙排列) : " + Arrays.toString(password);
            log.setActParams(actParams);
            String ip = HttpContextUtil.getIpAddress();
            log.setIpAddr(ip);
            userLogService.insertSelective(log);// 添加日志记录

//                验证authenticationToken和authenticationInfo信息
            return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        }
        return null;
    }
}
