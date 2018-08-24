package com.sl.ue.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.service.sys.SysUserService;

@RestController
public class Login {

	@Autowired
    private SysUserService sysUserSQL;
	
	@RequestMapping("/login")
	public String login(String username, String password){
		
		
		return null;
	}
}
