package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sl.ue.entity.hj.HjdjAcdInfo;
import com.sl.ue.service.hj.HjdjAcdInfoService;

@Controller
@RequestMapping("/hjdjAcdInfo")
public class HjdjAcdInfoWeb {

	@Autowired
	private HjdjAcdInfoService hjdjAcdInfoSQL;
	
	@RequestMapping("/findList")
	@ResponseBody
	public String findList(){
		HjdjAcdInfo model = new HjdjAcdInfo();
		List<HjdjAcdInfo> list = hjdjAcdInfoSQL.baseFindList(model);
		return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
	}
}
