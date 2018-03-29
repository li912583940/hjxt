package com.sl.ue.service;

import java.util.List;

/**
 * 说明 [持久层基础实现]
 * 
 * @author L_晓天    @2018年3月27日
 */
public interface BaseService<T> {

	public List<T> findList(T model);
	
	public T findOne(Object... key);
	
	public T add(T model);
	
	public T edit(T model);
	
	public void delete(T model);
}
