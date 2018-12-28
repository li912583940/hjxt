package com.sl.ue.service.base;

public interface BaseLogService<T> {

	/**
	 * 说明 [模块的操作记录]
	 * @param clazz
	 * @param model
	 * @param state  add:新增， edit：修改: delete：删除
	 * L_晓天  @2018年12月28日
	 */
	public void execute(String tableName, T model, String state);
	
	public void executeDel(String tableName, Object key);
}
