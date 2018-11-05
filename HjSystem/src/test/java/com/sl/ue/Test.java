package com.sl.ue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sl.ue.util.http.Result;

public class Test {

	public static void main(String[] args){
		String[] s = {"1","20","30"};
		List<String> list = new ArrayList(Arrays.asList(s));
//		list.add(1);
//		list.add(20);
//		list.add(31);
		System.out.println(list.contains("25"));
	}
}
