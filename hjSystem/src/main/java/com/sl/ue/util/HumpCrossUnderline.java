package com.sl.ue.util;

/**
 * 说明 [驼峰-下划线]
 *
 * @author L_晓天 @2018年3月29日
 */
public class HumpCrossUnderline {

	public static final char UNDERLINE = '_';

	
	/**
	 * 说明: [驼峰转下划线]
	 * @param param
	 * @return
	 */
	public static String humpToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}


	/**
	 * 说明: [下划线转驼峰]
	 * @param param
	 * @return
	 */
	public static String underlineToHump(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
