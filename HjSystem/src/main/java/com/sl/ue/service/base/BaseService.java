package com.sl.ue.service.base;

import java.util.List;
import java.util.Map;



/**
 * 说明 [基本数据层封装]
 * @author lxt  @2018年4月3日
 */
public interface BaseService<T>{

	public List<T> findList(T model);
	
	/**
	 * 说明 [分页查询,不带记录总数(count) 升序]
	 * @作者 LXT @2018年9月24日
	 */
	public List<T> findList(T model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [分页查询,不带记录总数(count) 自定义排序 :DESC :ASC]
	 * @作者 LXT @2018年9月24日
	 */
	public List<T> findList(T model, Integer pageSize, Integer pageNum, String sort);
	
	/**
	 * 说明 [分页查询,带记录总数(count) 降序]
	 * @作者 LXT @2018年9月24日
	 */
	public Map<String, Object> findPojo(T model);
	
	/**
	 * 说明 [分页查询,带记录总数(count) 降序]
	 * @作者 LXT @2018年9月24日
	 */
	public Map<String, Object> findPojo(T model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [分页查询,带记录总数(count) 自定义排序  :DESC :ASC]
	 * @作者 LXT @2018年9月24日
	 */
	public Map<String, Object> findPojo(T model, Integer pageSize, Integer pageNum, String sort);
	
	/**
	 * 说明 [查询记录数]
	 * @作者 LXT @2018年9月24日
	 */
	public Integer count(T model);
	
	/**
	 * 说明 [查询单条记录]
	 * key： 接受主键
	 * @author lxt
	 */
	public T findOne(Object key);
	
	public T add(T model);
	
	/**
	 * 说明 [where后面 只能是主键]
	 * L_晓天  @2018年10月12日
	 */
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
