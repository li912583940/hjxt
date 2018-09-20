package com.sl.ue.util.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 说明 [web上下文]
 * @作者 LXT @2018年9月3日
 */
public class WebContextUtil {

	
	/**
	 * 说明 [获取 request]
	 * @作者 LXT @2018年9月3日
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}
	
}
