package com.sl.ue.service.sys;

import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.service.base.BaseService;

public interface SysRoleService extends BaseService<SysRoleVO>{

	/**
	 * 说明 [获取目录的树形结构]
	 * L_晓天  @2018年10月30日
	 */
	public String getMenuTree();
	
	 /**
	 * 说明 [获取当前角色选中的目录 数组格式]
	 * L_晓天  @2018年10月30日
	 */
	public String getCheckedMenu(Integer roleId);
	/**
	 * 说明 [获取监区树形结构]
	 * L_晓天  @2018年10月30日
	 */
	public String getJqTree();
	
	/**
	 * 说明 [获取当前角色选中的监区 数组格式]
	 * L_晓天  @2018年10月30日
	 */
	public String getCheckedJq(Integer roleId);
	
	/**
	 * 说明 [添加角色菜单关联关系]
	 * L_晓天  @2018年10月31日
	 */
	public String addRoleMenu(Integer roleId, String menus);
	
	/**
	 * 说明 [添加角色监区关联关系]
	 * L_晓天  @2018年10月31日
	 */
	public String addRoleJq(Integer roleId, String jqs);
}