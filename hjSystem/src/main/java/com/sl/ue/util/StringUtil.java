package com.sl.ue.util;

public class StringUtil {

	
	/**
	 * ˵��: [ȥ����βΪ��,�� ]
	 * @author LXT @2018��3��30��
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
