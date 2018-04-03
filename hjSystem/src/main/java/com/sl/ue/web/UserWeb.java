package com.sl.ue.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.sl.ue.entity.User;
import com.sl.ue.service.UserService;

/**
 * 说明 [用户]
 * L_晓天  @2018年4月2日
 */
@Controller
public class UserWeb {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/hello")
	public ModelAndView hello(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		return mv;
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public String get(){
		List<User> list = userService.baseFindList(new User());
		JSONArray jsonAr = (JSONArray) JSONArray.toJSON(list);
		return jsonAr.toJSONString();
	}
}
