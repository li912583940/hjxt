package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sl.ue.entity.jl.JlFr;
import com.sl.ue.service.jl.JlFrService;

@RestController
@RequestMapping("/jlFr")
public class JlFrWeb {

	@Autowired
	private JlFrService jlFrSQL;
	
	
	@RequestMapping("/findList")
	public String findList(Integer a, Integer b){
		List<JlFr> list = jlFrSQL.baseFindList(new JlFr(),a,b);
		return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
	}
	
	@RequestMapping("/findOne")
	public String findOne(Integer webId){
		JlFr jlFr = jlFrSQL.baseFindOne(webId);
		return JSON.toJSONString(jlFr, SerializerFeature.WriteMapNullValue);
	}
	
	@RequestMapping("/add")
	public String add(){
		for(int i =0; i<20; i++){
			JlFr jlFr = new JlFr();
			jlFr.setFrNo(i+"");
			jlFr.setJy("长沙监狱");
			jlFr.setJq("一监区");
			jlFr.setJbNo((i/3+1)+"");
			jlFr.setQqJb(i/4);
			jlFr.setQqUse(1);
			jlFr.setHjJb(i/2+1);
			jlFrSQL.baseAdd(jlFr);
		}
		return "success";
	}
	
	@RequestMapping("/del")
	public String del(){
		JlFr jlFr = new JlFr();
		jlFr.setJbNo("1");
		jlFrSQL.baseDelete(jlFr);
		return "success";
	}
	
	@RequestMapping("/del")
	public String del(Integer webId){
		jlFrSQL.baseDeleteKey(webId);
		return "success";
	}
}
