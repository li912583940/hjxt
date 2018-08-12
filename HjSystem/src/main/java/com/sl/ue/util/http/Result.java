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
public class Result {
	public static String error_100 = "正确";
	public static String error_101 = "系统异常";
	public static String error_102 = "参数错误";
	
	
	/** 状态  succes or error */
	private String state = "succes";
	
	/** 状态码 */
	private Integer code = 1;
	
	/** 消息提示 */
	private String msg = "正确";
	
	/** 数据 */
	private Object data;

	public Result(){
		map.put("state", this.state);
		map.put("code", this.code);
		map.put("msg", this.msg);
		map.put("code", this.code);
	}
	Map<String, Object> map = new HashMap<String, Object>();
	
	public void putJson(String name, Object value){
		if(StringUtils.isNotBlank(name)){
			map.put(name, value);
		}else{
			this.putJson(value);
		}
	}
	
	public void putJson(Object value){
		map.put("data", value);
	}
	
	public void putData(Object value){
		map.put("list", value);
	}
	
	public void error(String code){
		map.put("code", code);
	}
	
	public void msg(String msg){
		map.put("msg", msg);
		map.put("code", this.code);
	}
	
	public void succes(){
		map.put("msg", this.msg);
		map.put("code", this.code);
	}
	public String toString(){
		return JSON.toJSONString(this.map, SerializerFeature.WriteMapNullValue);
	}
	
	
}
