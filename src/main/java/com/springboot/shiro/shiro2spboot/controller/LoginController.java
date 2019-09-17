package com.springboot.shiro.shiro2spboot.controller;

import com.google.code.kaptcha.Constants;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaEmptyException;
import com.springboot.shiro.shiro2spboot.common.shiro.CaptchaErrorException;
import com.springboot.shiro.shiro2spboot.common.util.DateTimeUtil;
import com.springboot.shiro.shiro2spboot.common.util.VerifyCodeUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {


    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) {
        String exception = (String) request.getAttribute("shiroLoginFailure");

        String msg = "";
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

        }
        map.put("msg", msg);
        return "/login";
    }

    /**
     * 另一种获取登陆验证码的方式
     *
     * @param request
     */
    @RequestMapping(value = "/verify")
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

    @RequestMapping("/403")
    public String unauthorizedRole() {
        return "/403";
    }

    @RequestMapping("/jsonTestPage")
    public String jsonTestPage() {
        return "/jsonTest";
    }
}
