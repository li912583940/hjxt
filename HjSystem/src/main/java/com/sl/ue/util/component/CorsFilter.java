package com.sl.ue.util.component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.sl.ue.util.Constants;

/**
 * 说明 [跨域]
 * @作者 LXT @2018年9月14日
 */
@Component
public class CorsFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		 	HttpServletResponse response = (HttpServletResponse) res;  
	        response.setHeader("Access-Control-Allow-Origin", "*");  
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
	        response.setHeader("Access-Control-Max-Age", String.valueOf(Constants.TOKEN_EXPIRES_HOURS*60*60));  
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");  
	        chain.doFilter(req, res);  
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
