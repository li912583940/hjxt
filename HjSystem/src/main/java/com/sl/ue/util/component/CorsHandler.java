package com.sl.ue.util.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

//@Configuration
public class CorsHandler implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");  
		  
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");  
  
		response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");  
  
		response.setHeader("X-Powered-By","Jetty");  
  
  
        String method= request.getMethod();  
  
        if (method.equals("OPTIONS")){  
        	response.setStatus(200);  
            return false;  
        }  
  
        System.out.println(method);  
  
        return true; 
	}


}
