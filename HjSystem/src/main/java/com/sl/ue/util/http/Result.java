package com.sl.ue.util.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * 说明 [数据返回封装结果集]
 * @author lxt
 */
public abstract class Result {
	public final static Integer error_0 = 0; // "正确"
	public final static Integer error_100 = 100; // "系统异常"
	public final static Integer error_101 = 101; // 
	public final static Integer error_102 = 102; // "参数错误"
	
	final static Map<Integer, String> confMap = new HashMap<>(); // 状态码
	
	static{
		confMap.put(0, "正确");
		confMap.put(100, "系统异常");
		confMap.put(101, "");
		confMap.put(102, "参数错误");
	}
	JSONObject json = new JSONObject(); //返回数据
	public Result(){
		json.put("code", error_0);
		json.put("msg", confMap.get(0));
	}
	
	
	/** 数据开始 */
	public void putJson(String name, Object value){
		if(StringUtils.isNotBlank(name)){
			json.put(name, value);
		}else{
			this.putJson(value);
		}
	}
	
	public void putJson(Object value){
		json.put("data", value);
	}
	
	public void putData(String name, Object value){
		if(StringUtils.isNotBlank(name)){
			json.put(name, value);
		}else{
			this.putData(value);
		}
	}
	
	public void putData(Object value){
		json.put("list", value);
	}
	/** 数据结束 */
	
	/** 消息开始 */
	public void error(Integer error_){
		json.put("code", error_);
		json.put("msg", confMap.get(error_));
	}
	
	public void error(Integer error_, String msg){
		json.put("code", error_);
		json.put("msg", msg);
	}
	
	public void msg(String msg){
		json.put("msg", msg);
		json.put("code", confMap.get(0));
	}
	
	/** 消息结束  */
	
	public String toResult(){
		JSONObject newJson = (JSONObject) this.json.clone();
		json.clear();
		json.put("code", error_0);
		json.put("msg", confMap.get(0));
		return JSON.toJSONString(newJson, SerializerFeature.WriteMapNullValue);
	}
	
	
}
