package com.sl.ue.util.anno;

public @interface Id {

	/**
	 * 说明 [主键 默认自增]
	 * @author L_晓天    @2018年4月7日
	 */
	boolean increment() default true;
}
