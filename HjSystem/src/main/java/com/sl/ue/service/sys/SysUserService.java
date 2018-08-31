package com.sl.ue.service.sys;

import java.util.Set;

import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.BaseService;

public interface SysUserService extends BaseService<SysUserVO>{

	/**
	 * 说明 [根据用户名查找其角色]
	 * @author lxt
	 */
	public Set<String> findRoles(String username);
	
	/**
	 * 说明 [根据用户名查找其权限]
	 * @author lxt
	 */
	public Set<String> findPermissions(String username);
}