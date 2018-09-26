package com.sl.ue.util.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 说明 [请求拦截]
 * @作者 LXT @2018年9月26日
 */
@Component
public class LogInterceptor implements HandlerInterceptor{

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		System.out.println("请求路径： "+request.getRequestURI());
        return true;
    }

}
