package com.sl.ue.util;

/**
 * ˵�� [�շ�-�»���]
 *
 * @author L_���� @2018��3��29��
 */
public class HumpCrossUnderline {

	public static final char UNDERLINE = '_';

	
	/**
	 * ˵��: [�շ�ת�»���]
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
	 * ˵��: [�»���ת�շ�]
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
