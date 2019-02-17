package com.sl.ue.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 说明 [日期工具类]
 * @作者 LXT @2018年9月4日
 */
public class DateUtil {

	/**
	 * 说明 [返回当前日期的字符串类型:默认格式  yyyy-MM-dd HH:mm:ss]
	 * @return
	 * L_晓天  @2018年12月19日
	 */
	public static String getDefaultNow(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * 说明 [返回当前日期的字符串类型:格式  yyyy-MM-dd HH:mm:ss]
	 * @return
	 * L_晓天  @2018年12月19日
	 */
	public static String getDefaultNow(String format){
		if(StringUtils.isBlank(format)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(new Date());
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(new Date());
		}
	}
	
	/**
	 * 说明 [返回日期的字符串类型:默认格式  yyyy-MM-dd HH:mm:ss]
	 * @作者 LXT @2018年9月4日
	 */
	public static String getDefault(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 说明 [返回日期的字符串类型:指定格式]
	 * @作者 LXT @2018年9月4日
	 */
	public static String getFormat(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 说明 [返回日期:指定日期的字符串类型]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date getDate(String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(dateFormat);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	/**
	 * 说明 [日期算法:天--参数为负数时为减.]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date addDay(Date date, int day){
		return add(date, day, 0, 0, 0);
	}
	/**
	 * 说明 [日期算法:小时--参数为负数时为减.]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date addHours(Date date, int hours){
		return add(date, 0, hours, 0, 0);
	}
	
	/**
	 * 说明 [日期算法:分钟--参数为负数时为减.]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date addMin(Date date, int min){
		return add(date, 0, 0, min, 0);
	}
	
	/**
	 * 说明 [日期算法:秒--参数为负数时为减.]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date addSec(Date date, int sec){
		return add(date, 0, 0, 0, sec);
	}
	/**
	 * 说明 [日期新增, 参数为负数时为减.]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date add(Date date, int day, int hours, int min, int sec){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		cal.add(Calendar.MINUTE, min);
		cal.add(Calendar.SECOND, sec);
		return cal.getTime();
	}
	
	/**
	 * 说明 [日期新增:月份]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date addMonth(Date date, int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * 说明 [日期新增:年份]
	 * @作者 LXT @2018年9月4日
	 */
	public static Date addYear(Date date, int year){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}
}
