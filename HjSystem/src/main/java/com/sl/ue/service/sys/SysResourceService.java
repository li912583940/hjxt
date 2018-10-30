package com.sl.ue.service.sys;

import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.service.base.BaseService;

public interface SysResourceService extends BaseService<SysResourceVO>{

	
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
}