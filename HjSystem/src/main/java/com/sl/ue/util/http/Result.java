package com.sl.ue.util.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * 说明 [数据返回封装结果集]
 * @author lxt
 */
public class Result {
	public final static Integer error_0 = 0; // "正确"
	public final static Integer error_100 = 100; // "系统异常"
	public final static Integer error_101 = 101; // 无效token
	public final static Integer error_102 = 102; // "参数错误"
	public final static Integer error_103 = 103; // 业务错误
	
	final static Map<Integer, String> confMap = new ConcurrentHashMap<>(); // 状态码
	
	static{
		confMap.put(0, "正确");
		confMap.put(100, "系统异常");
		confMap.put(101, "无效token");
		confMap.put(102, "参数错误");
		confMap.put(103, "业务错误");
	}
	JSONObject json = new JSONObject(); //返回数据
	public Result(){
		json.put("errCode", error_0);
		json.put("errMsg", confMap.get(0));
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
	
	public void putPojo(String name, Object value) {
		if(StringUtils.isNotBlank(name)){
			json.put(name, value);
		}else {
			this.putPojo(value);
		}
	}
	
	public void putPojo(Object value) {
		json.put("pojo", value);
	}
	/** 数据结束 */
	
	/** 消息开始 */
	public void error(Integer error_){
		json.put("errCode", error_);
		json.put("errMsg", confMap.get(error_));
	}
	
	public void error(Integer error_, String msg){
		json.put("errCode", error_);
		json.put("errMsg", msg);
	}
	
	public void msg(String msg){
		json.put("errMsg", msg);
		json.put("errCode", error_0);
	}
	
	/** 消息结束  */
	
	public String toResult(){
		String result = JSON.toJSONString(json, SerializerFeature.WriteMapNullValue);
		json.clear();
		json.put("errCode", error_0);
		json.put("errMsg", confMap.get(0));
		return result;
	}
	
	public String toString(){
		return this.toResult();
	}
	
}
