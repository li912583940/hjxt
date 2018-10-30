package com.sl.ue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sl.ue.util.http.Result;

public class Test {

	public static void main(String[] args){
		List<Integer> i = new ArrayList<>();
		i.add(1);
		i.add(4);
		Object[] o = i.toArray();
		String s = o.toString();
		System.out.println(s);
	}
}
