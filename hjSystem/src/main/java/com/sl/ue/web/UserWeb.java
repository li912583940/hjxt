package com.sl.ue.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.UserService;

/**
 * 说明 [用户]
 * L_晓天  @2018年4月2日
 */
@Controller
@RequestMapping("/user")
public class UserWeb {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/hello")
	public ModelAndView hello(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		return mv;
	}
	
	@RequestMapping("/findList")
	@ResponseBody
	public String findList(){
		List<SysUser> list = userService.baseFindList(new SysUser());
		return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
	}
	
	@RequestMapping("/findOne")
	@ResponseBody
	public String findOne(){
		SysUser user = userService.baseFindOne(1);
		return JSON.toJSONString(user, SerializerFeature.WriteMapNullValue);
	}
}
