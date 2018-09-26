package com.sl.ue.util.http.token;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.sl.ue.util.Constants;
import com.sl.ue.util.http.WebContextUtil;


public class TokenManager {

	/** 将token存储到JVM内存(ConcurrentHashMap)中  */      
	private static Map<String, Date> tokenMap = new ConcurrentHashMap<String, Date>();

	/**
	 * 说明 [UUID创建Token(用户登录时，创建Token) ]
	 * @作者 LXT @2018年9月3日
	 */
	public String createToken(String username) {
		String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		Date overdue = DateUtils.addHours(new Date(), Constants.TOKEN_EXPIRES_HOURS); // token 到期时间
		tokenMap.put(token, overdue);
		return token;
	}


	
	/**
	 * 说明 [检查token是否有效]
	 * @作者 LXT @2018年9月3日
	 */
	public boolean checkToken(String token) {
		if(StringUtils.isNotBlank(token) && tokenMap.containsKey(token)){
			Date overdue = tokenMap.get(token);
			if(new Date().before(overdue)){ // token没有过期
				return true;
			}
		}
		return false;
	}

	/**
	 * 说明 [刷新token时间]
	 * @作者 LXT @2018年9月4日
	 */
	public void refDate(String token){
		if(StringUtils.isNotBlank(token) && tokenMap.containsKey(token)){
			Date overdue = DateUtils.addHours(new Date(), Constants.TOKEN_EXPIRES_HOURS); // token 到期时间
			tokenMap.put(token, overdue);
		}
		
	}
	
	public void deleteToken(String token) {
		tokenMap.remove(token);
	}
	
	public void clearToken() {
		tokenMap.clear();
	}
}
