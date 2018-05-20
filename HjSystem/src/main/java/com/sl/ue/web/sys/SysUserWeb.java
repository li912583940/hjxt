package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.sys.SysUserService;

@Controller
@RequestMapping("/sysUser")
public class SysUserWeb {

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/findOne")
	@ResponseBody
	public String findOne(){
		List<SysUser> list = sysUserService.baseFindList(new SysUser());
		return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
	}
}
