package com.sl.ue;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.sl.ue.util.DateUtil;

public class Test {

	public static void main(String[] args){
		Date date = new Date();
		System.out.println(date.getTime());
		System.out.println(System.currentTimeMillis());
	}
}
