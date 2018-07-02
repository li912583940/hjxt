package com.sl.ue.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sl.ue.service.BaseService;

/**
 * 说明 [持久层 实现类 SQL Server]
 * @author lxt
 */
public abstract class BaseSqlImpl<T> implements BaseService<T>{

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public BaseSqlImpl(){
		Type superClass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superClass;
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	
	/** 实际操作的实体类对象 */
	private Class<T> clazz; 
	
	@Override
	public List<T> baseFindList(T model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> baseFindList(T model, Integer pageSize, Integer pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T baseFindOne(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T baseAdd(T model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T baeEdit(T model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void baeDelete(T model) {
		// TODO Auto-generated method stub
		
	}

}
