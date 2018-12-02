package com.sl.ue.service.sys;


import java.util.Map;

import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.BaseService;

public interface SysUserService extends BaseService<SysUserVO>{

	public Map<String, Object> findPojoJoin(SysUserVO model, Integer pageSize, Integer pageNum);
	/**
	 * 说明 [查询用户角色]
	 * L_晓天  @2018年9月20日
	 */
	public String getRoles(String token);
	
	/**
	 * 说明 [获取当前用户的角色列表]
	 * L_晓天  @2018年11月1日
	 */
	public String getCheckedRole(Integer userId);
	
	/**
	 * 说明 [为当前用户添加角色]
	 * L_晓天  @2018年11月1日
	 */
	public String addUserRole(Integer userId, String roles);
	
	/**
	 * 说明 [获取用户的监区权限]
	 * L_晓天  @2018年12月1日
	 */
	public String getJqs(SysUserVO loginUser);
}