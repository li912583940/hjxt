package com.sl.ue.util.component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
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
			HttpServletRequest request = (HttpServletRequest) req;
		 	HttpServletResponse response = (HttpServletResponse) res;  
		 	if(StringUtils.isNotBlank(request.getHeader("Origin"))){
		 		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));  
		 		response.setHeader("Access-Control-Allow-Credentials","true");
		 	}else{
		 		response.setHeader("Access-Control-Allow-Origin", "*");  
		 	}
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
	        response.setHeader("Access-Control-Allow-Headers", "X-Token,x-requested-with,Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setHeader("Pragma", "no-cache");
	        //response.setHeader("Access-Control-Max-Age", String.valueOf(Constants.TOKEN_EXPIRES_HOURS*60*60));  
//	        if (request.getMethod().equals("OPTIONS")) {
//	        	response.setStatus(HttpStatus.SC_OK);
//				// hresp.setContentLength(0);
//	        	response.getWriter().write("OPTIONS returns OK");
//	            return;
//	        }
	        chain.doFilter(req, res);  
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
