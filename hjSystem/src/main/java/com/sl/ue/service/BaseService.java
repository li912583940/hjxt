package com.sl.ue.service;

import java.util.List;

import com.sl.ue.util.Page;

/**
 * ˵�� [�־ò��ʵ��]
 * 
 * @author L_����    @2018��3��27��
 */
public interface BaseService<T>{

	public List<T> baseFindList(T model);
	
	public List<T> baseFindList(T model, Integer pageSize, Integer pageNum);
	
	public T baseFindOne(Object... key);
	
	public T baseAdd(T model);
	
	public T baeEdit(T model);
	
	public void baeDelete(T model);
}
