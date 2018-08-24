package com.sl.ue.service.base;

import java.util.List;

import com.sl.ue.util.Page;


/**
 * 说明 [基本数据层封装]
 * @author lxt  @2018年4月3日
 */
public interface BaseService<T>{

	public List<T> findList(T model);
	
	public List<T> findList(T model, Integer pageNum, Integer pageSize);
	
	/**
	 * 说明 [查询单条记录]
	 * key： 接受主键
	 * @author lxt
	 */
	public T findOne(Object key);
	
	public T add(T model);
	
	public T edit(T model);
	
	/**
	 * 说明 [删除记录]
	 * key： 只接受主键
	 * @author lxt
	 */
	public void deleteKey(Object key);
	
	/**
	 * 说明 [删除记录]
	 * model： 条件之类
	 * @author lxt
	 */
	public void delete(T model);
}
