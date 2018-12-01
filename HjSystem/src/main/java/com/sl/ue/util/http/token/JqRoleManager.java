package com.sl.ue.util.http.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 说明 [登录用户的监区权限]
 * L_晓天  @2018年12月1日
 */
public class JqRoleManager {
	/** 将token与监区权限存储到JVM内存(ConcurrentHashMap)中  */      
	private static Map<String, String> jqMap = new ConcurrentHashMap<String, String>();
	
	public void putJqRole(String token, String jqs){
		jqMap.put(token, jqs);
	}
	
	public String getJqs(String token){
		return jqMap.get(token);
	}
	
	public void deleteJq(String token){
		jqMap.remove(token);
	}
	
	public void remove(){
		jqMap.clear();
	}
}
