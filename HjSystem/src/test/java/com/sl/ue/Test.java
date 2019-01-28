package com.sl.ue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sl.ue.util.DateUtil;

public class Test {

	public static void main(String[] args){
	    String a ="abcd";
		System.out.println(a.substring(0,3));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1991);
		c.set(Calendar.MONTH, 2-1);
		int one = c.getMinimum(Calendar.DATE);
		System.out.println(one);
		int last = c.getActualMaximum(Calendar.DATE);
		System.out.println(last);
		//c.get(Calendar.YEAR);
		String s = "2019-01-05";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		System.out.println(c.get(Calendar.YEAR));
		System.out.println(c.get(Calendar.MONTH)+1);
		System.out.println(c.get(Calendar.WEEK_OF_MONTH));
	}
}
