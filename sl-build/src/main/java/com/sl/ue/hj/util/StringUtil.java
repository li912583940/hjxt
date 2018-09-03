package com.sl.ue.hj.util;

public class StringUtil {

	//首字母转小写
    public static String toLower(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpper(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    
    /**
     * 说明 [根据数据库表名得到小写的包名]
     * L_晓天  @2018年9月3日
     */
    public static String getPackage(String s){
    	String[] ss = s.split("_");
    	return ss[0].toLowerCase();
    }
    
    /**
     * 说明 [数据库字段转实体类属性名]
     * L_晓天  @2018年9月3日
     */
    public static String toFieldName(String s){
    	s = s.toLowerCase();
    	String[] ss = s.split("_");
    	String fieldName = ss[0];
    	if(ss.length>1){
    		for(int i=1;i<ss.length;i++){
    			fieldName += toUpper(ss[i]);
    		}
    	}
    	return fieldName;
    }
}
