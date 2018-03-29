package com.sl.ue.service;

import java.util.List;

/**
 * ˵�� [�־ò����ʵ��]
 * 
 * @author L_����    @2018��3��27��
 */
public interface BaseService<T> {

	public List<T> findList(T model);
	
	public T findOne(Object... key);
	
	public T add(T model);
	
	public T edit(T model);
	
	public void delete(T model);
}
