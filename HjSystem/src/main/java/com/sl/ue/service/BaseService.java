package com.sl.ue.service;

import java.util.List;

import com.sl.ue.util.Page;


/**
 * 说明 [基本数据层封装]
 * @author lxt  @2018年4月3日
 */
public interface BaseService<T>{

	public List<T> baseFindList(T model);
	
	public List<T> baseFindList(T model, Integer pageNum, Integer pageSize);
	
	/**
	 * 说明 [查询单条记录]
	 * key： 接受主键
	 * @author lxt
	 */
	public T baseFindOne(Object key);
	
	public T baseAdd(T model);
	
	public T baseEdit(T model);
	
	/**
	 * 说明 [删除记录]
	 * key： 只接受主键
	 * @author lxt
	 */
	public void baseDeleteKey(Object key);
	
	/**
	 * 说明 [删除记录]
	 * model： 条件之类
	 * @author lxt
	 */
	public void baseDelete(T model);
}
