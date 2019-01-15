package com.sl.ue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sl.ue.util.DateUtil;

public class Test {

	public static void main(String[] args){
		List<Integer> disNo = new ArrayList<Integer>();
		disNo.add(3);
		disNo.add(5);
		disNo.add(7);
		disNo.add(88);
		System.out.println(disNo.contains(9));
	}
}
