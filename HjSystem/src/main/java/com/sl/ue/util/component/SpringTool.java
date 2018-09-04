package com.sl.ue.util.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 说明 [spring工具类，直接通过此类可以获取到spring管理的bean]
 * @author lxt
 */
@Component
public class SpringTool implements ApplicationContextAware{

	private static ApplicationContext context = null;
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		if(context == null){
			context = arg0;
			System.out.println("ApplicationContext配置成功 "+context);
		}
	}

	public static ApplicationContext getApplicationContext(){
		return context;
	}
	
	public static Object getBean(String name){
		return context.getBean(name);
	}
}
