package com.sl.ue.util.component;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.sl.ue.util.Constants;
import com.sl.ue.util.anno.IgnoreSecurity;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.WebContextUtil;
import com.sl.ue.util.http.token.TokenManager;

/**
 * 说明 [安全效验, 所有需要安全效验的方法都被切面执行]
 * @作者 LXT @2018年9月4日
 */
@Component
@Aspect
public class SecurityAspect extends Result{

	private TokenManager tokenManager = new TokenManager();
	
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object execute(ProceedingJoinPoint pjp) throws Throwable {
		// 从切点上获取目标方法
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		// 若目标方法忽略了安全性检查,则直接调用目标方法
		if (method.isAnnotationPresent(IgnoreSecurity.class)) {
			return pjp.proceed();
		}
		// 从 request header 中获取当前 token
		String token = WebContextUtil.getRequest().getHeader(
				Constants.TOKEN_NAME);
		// 检查 token 有效性
		if (!tokenManager.checkToken(token)) {
			error(error_101);
			return this.toResult();
		}
		tokenManager.refDate(token); // 刷新token
		// 调用目标方法
		return pjp.proceed();
	}
}
