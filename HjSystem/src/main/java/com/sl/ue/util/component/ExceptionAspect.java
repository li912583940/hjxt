package com.sl.ue.util.component;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.ue.util.http.Result;

/**
 * 说明 [切面-处理所有接口异常信息]
 * @author lxt
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAspect extends Result{

	/**
	 * 400 - Bad Request
	 */
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public String handleHttpMessageNotReadableException(
//			HttpMessageNotReadableException e) {
//		this.error(error_100);
//		//e.printStackTrace();
//		return this.toResult();
//	}
	
	/**
	 * 400 - Bad Request
	 */
//	@ExceptionHandler({MethodArgumentNotValidException.class})
//	public String handleValidationException(MethodArgumentNotValidException e) {
//		this.error(error_100);
//		//e.printStackTrace();
//		return this.toResult();
//	}
	
	
	/**
	 * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
//	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//	public String handleHttpRequestMethodNotSupportedException(
//			HttpRequestMethodNotSupportedException e) {
//		this.error(error_100);
//		//e.printStackTrace();
//		return this.toResult();
//	}
	
	/**
	 * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
//	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
//	public String handleHttpMediaTypeNotSupportedException(Exception e) {
//		this.error(error_100);
//		//e.printStackTrace();
//		return this.toResult();
//	}
	
//	@ExceptionHandler(Exception.class)
//	public String handleException(Exception e){
//		this.error(error_100);
//		//e.printStackTrace();
//		return this.toResult();
//	}
}
