package com.sl.ue.util.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Id {

	/**
	 * 说明 [主键 默认自增]
	 * @author L_晓天    @2018年4月7日
	 */
	boolean inc() default false;
}
