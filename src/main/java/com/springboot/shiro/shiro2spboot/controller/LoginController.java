package com.springboot.shiro.shiro2spboot.controller;

import com.google.code.kaptcha.Constants;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaEmptyException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaErrorException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaToken;
import com.springboot.shiro.shiro2spboot.common.util.DateTimeUtil;
import com.springboot.shiro.shiro2spboot.common.util.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Api(value = "用户登陆相关API")
@Controller
public class LoginController {


    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "/index";
    }

    @ApiIgnore
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(String username, String password, String captchaCode, Map<String, Object> map) {
        String exception = null;

        try {
            var usernamePasswordToken = new CaptchaToken(username, password, captchaCode, false, "localhost");
            var subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            exception = e.getClass().getName();
        }

        String msg = "登陆成功";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                msg = "UnknownAccountException --> 账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "IncorrectCredentialsException --> 密码不正确";
            } else if (CaptchaEmptyException.class.getName().equals(exception)) {
                msg = "CaptchaEmptyException --> 验证码不可为空";
            } else if (CaptchaErrorException.class.getName().equals(exception)) {
                msg = "CaptchaErrorException --> 验证码错误";
            } else {
                msg = "else --> " + exception;
            }
            map.put("msg", msg);
            return "/login";
        }
        map.put("msg", msg);
        return "/index";
    }

    /**
     * 另一种获取登陆验证码的方式
     *
     * @param request
     */
    @ApiOperation(value = "验证码生成API", notes = "用于生成随机验证码")
    @RequestMapping(value = "/verify", method = {RequestMethod.GET, RequestMethod.POST})
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //当前时间
            String nowTime = DateTimeUtil.getCurFormatTime();
            //存入会话session
            HttpSession session = request.getSession();
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, verifyCode);
            session.setAttribute("verify_date", nowTime);
            //生成图片
            int w = 150;
            int h = 50;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
    public String unauthorizedRole() {
        return "/403";
    }

    @RequestMapping(value = "/kickout", method = {RequestMethod.GET, RequestMethod.POST})
    public String kickout() {
        return "/kickout";
    }


    @RequestMapping(value = "/jsonTestPage", method = {RequestMethod.GET, RequestMethod.POST})
    public String jsonTestPage() {
        return "/jsonTest";
    }
}
