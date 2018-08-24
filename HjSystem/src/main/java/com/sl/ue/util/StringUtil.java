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
	

	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
