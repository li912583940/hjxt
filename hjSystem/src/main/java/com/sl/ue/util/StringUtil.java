package com.sl.ue.util;

public class StringUtil {

	
	
	/**
	 * 说明 [截掉最后一个“,”]
	 * @author L_晓天    @2018年4月3日
	 */
	public static String lastComma(String str){
		if(str.endsWith(",")){
			return str.substring(0, str.length()-1);
		}
		return str;
	}
	

}
