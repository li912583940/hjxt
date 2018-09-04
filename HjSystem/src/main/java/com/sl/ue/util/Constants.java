package com.sl.ue.util;

import com.sl.ue.entity.sys.vo.SysUserVO;

/**
 * 说明 [常量类]
 * @作者 LXT @2018年9月3日
 */
public class Constants {

	/**
	 * 存储当前登录用户id
	 */
	public static SysUserVO sysUser= null;
	
	/** token有效期(小时) */
	public static final int TOKEN_EXPIRES_HOURS = 2;

	/**  存放Token的header字段 */      
	public static final String TOKEN_NAME = "X-Token";
}
