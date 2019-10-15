package com.springboot.shiro.shiro2spboot.config;

import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaEmptyException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaErrorException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaToken;
import com.springboot.shiro.shiro2spboot.common.util.VerifyCodeUtils;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

//实现AuthorizingRealm接口用户认证
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

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
            var username = (String) authenticationToken.getPrincipal();
//            根据用户名获取用户信息
            var user = userService.findByUsername(username);
            if (user != null) {
                log.info("认证 ：Shiro认证成功");
//                验证authenticationToken和authenticationInfo信息
                return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
            }
        }
        return null;
    }
}
