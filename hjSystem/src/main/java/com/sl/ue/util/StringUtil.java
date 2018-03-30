package com.sl.ue.util;

public class StringUtil {

	
	/**
	 * 说明: [去掉结尾为“,” ]
	 * @author LXT @2018年3月30日
	 * @param str
	 * @return
	 */
	public static String lastComma(String str){
		if(str.endsWith(",")){
			return str.substring(0, str.length()-1);
		}
		return str;
	}
	

}
