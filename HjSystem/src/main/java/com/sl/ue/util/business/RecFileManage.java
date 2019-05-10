package com.sl.ue.util.business;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 说明 [存储需要删除的会见记录临时压缩包]
 * L_晓天  @2019年5月9日
 */
public class RecFileManage {

	/** 将会见记录临时压缩包路径与压缩包存储时间毫秒数存储到JVM内存(ConcurrentHashMap)中  */      
	private static Map<String, Long> recFileMap = new ConcurrentHashMap<String, Long>();
	
	public void putRecFile(String filePath, Long date){
		recFileMap.put(filePath, date);
	}
	
	public Long getRecFile(String filePath){
		return recFileMap.get(filePath);
	}
	
	public Map<String, Long> getAll(){
		return recFileMap;
	}
	
	public void deleteRecFile(String filePath){
		recFileMap.remove(filePath);
	}
	
	public void remove(){
		recFileMap.clear();
	}
}
