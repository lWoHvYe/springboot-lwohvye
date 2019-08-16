package com.springboot.shiro.shiro2spboot.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORSFilter 解决跨域问题
 *
 */
@Component
public class InitCORSFilter extends OncePerRequestFilter {
  private Logger logger = LoggerFactory.getLogger(InitCORSFilter.class);
  public InitCORSFilter() {
    logger.info("==== 初始化系统允许跨域请求 ====");
  }
  /**
   * 解决跨域：Access-Control-Allow-Origin，值为*表示服务器端允许任意Domain访问请求
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
//    if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
      response.addHeader("Access-Control-Allow-Origin", "*");
      response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, sid, mycustom, smuser");
      response.addHeader("Access-Control-Max-Age", "1800");//30 min
//    }
    filterChain.doFilter(request, response);
  }
}