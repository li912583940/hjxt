package com.sl.ue.util.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestShiro {

	public static void main(String[] args) {
		 //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =  
	            new IniSecurityManagerFactory("classpath:shiro-realm.ini");  
	    //2、得到SecurityManager实例 并绑定给SecurityUtils  
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);  
	    //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）  
	    Subject subject = SecurityUtils.getSubject();  
	    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");  
	  
	    try {  
	        //4、登录，即身份验证  
	        subject.login(token);  
	        System.out.println("身份验证成功");
	    } catch (AuthenticationException e) {  
	        //5、身份验证失败  
	    	System.out.println("身份验证失败");
	    }  
	    
	    // 认证成功后
	    if(subject.isAuthenticated()){
	    	System.out.println("用户  "+subject.getPrincipal()+" 登录成功");
	    	//测试角色
	    	System.out.println("是否拥有 manager 角色："+subject.hasRole("manager"));
	    	//测试权限
	    	System.out.println("是否拥有 user:create 权限 "+subject.isPermitted("user:create"));
	    }
	  
	    //6、退出  
	    subject.logout();  

	}

}
