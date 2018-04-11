package com.sl.ue.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sl.ue.entity.SysLog;
import com.sl.ue.service.LogService;

@Controller
@RequestMapping("/log")
public class LogWeb {

	@Autowired
	private LogService logService;
	
	@RequestMapping(value="/findOne")
	@ResponseBody
	public String findOne(){
		SysLog log = logService.baseFindOne(new SysLog(),"1");
		return JSON.toJSONString(log, SerializerFeature.WriteMapNullValue);
	}
}
