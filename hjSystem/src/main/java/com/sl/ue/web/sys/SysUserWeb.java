package com.sl.ue.web.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.service.sys.SysUserService;

@Controller
@RequestMapping("/sysUser")
public class SysUserWeb {

	@Autowired
	private SysUserService sysUserService;
}
