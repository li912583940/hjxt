package com.sl.ue.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * 说明 [数据返回封装结果集]
 * @author lxt
 */
public class Result {

	/** 状态  succes or error */
	private String state = "succes";
	
	/** 状态码 */
	private Integer code = 1;
	
	/** 消息提示 */
	private String message = "正确";
	
	/** 数据 */
	private Object data;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toString(){
		return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
	}
	
	
}
