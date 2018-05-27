package com.sl.ue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sl.ue.util.http.Result;

public class Test {

	public static void main(String[] args){
		Result rs = new Result();
//		rs.setState(0);
//		rs.setMessage("你好");
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("aa", 111);
		map.put("bb", "你好啊");
		list.add(map);
		rs.setData(list);
		System.out.println(rs.toString());
	}
}
