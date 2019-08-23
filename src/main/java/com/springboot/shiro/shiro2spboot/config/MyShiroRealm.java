package com.springboot.shiro.shiro2spboot.config;

import com.google.code.kaptcha.Constants;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaEmptyException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaErrorException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaToken;
import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

//实现AuthorizingRealm接口用户认证
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 角色权限和对应权限添加
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        获取用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();
//        添加角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
//            添加角色
            authorizationInfo.addRole(role.getRole());
            for (Permission permission : role.getPermissions()) {
//                添加权限
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        Post请求先进行认证，再到请求
        if (authenticationToken.getPrincipal() != null) {
//            转为CaptchaToken
            CaptchaToken captchaToken = (CaptchaToken) authenticationToken;
//            获取页面输入的验证码
            String captchaCode = captchaToken.getCaptchaCode();
//            验证码为空，抛出相应异常
            if (StringUtils.isEmpty(captchaCode))
                throw new CaptchaEmptyException();

            // 从session获取正确的验证码
            Session session = SecurityUtils.getSubject().getSession();
            String sessionCaptchaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

//            验证码错误，抛出相应异常
            if (!captchaCode.equalsIgnoreCase(sessionCaptchaCode))
                throw new CaptchaErrorException();

//          获取用户名
            String username = (String) authenticationToken.getPrincipal();
//            根据用户名获取用户信息
            User user = userService.findByUsername(username);
            if (user != null) {
//                验证authenticationToken和authenticationInfo信息
                return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
            }

        }
        return null;
    }
}
