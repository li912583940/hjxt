package com.sl.ue.util.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 说明 [跨域]
 * @作者 LXT @2018年9月26日
 */
@Configuration
public class CorsApiConfigurer implements WebMvcConfigurer {

//	@Override
//	public void addCorsMappings(CorsRegistry registry){
//		System.out.println("自动加载");
//		//处理所有请求
//		registry.addMapping("/**");
//	
//		//处理特定的请求
////		registry.addMapping("/use/**")
////        .allowedOrigins("http://128.0.0.2")
////        .allowedMethods("GET", "POST")
////        .allowCredentials(false).maxAge(3600);
//
//	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }




}
