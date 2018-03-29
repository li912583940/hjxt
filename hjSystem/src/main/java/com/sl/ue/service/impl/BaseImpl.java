package com.sl.ue.service.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSONObject;
import com.sl.ue.service.BaseService;
import com.sl.ue.util.HumpCrossUnderline;
import com.sl.ue.util.Table;

public class BaseImpl<T> implements BaseService<T>{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<T> findList(T model) {
		Table table = model.getClass().getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			Field[] fields = model.getClass().getDeclaredFields();
			for(Field field : fields){
				HumpCrossUnderline.humpToUnderline(field.getName());
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	public T findOne(Object... key) {
		// TODO Auto-generated method stub
		return null;
	}

	public T add(T model) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public T edit(T model) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(T model) {
		// TODO Auto-generated method stub
		
	}

}
