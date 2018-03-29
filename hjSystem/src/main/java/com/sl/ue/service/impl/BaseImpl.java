package com.sl.ue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sl.ue.service.BaseService;

public class BaseImpl<T> implements BaseService<T>{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<T> findList(T model) {
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
