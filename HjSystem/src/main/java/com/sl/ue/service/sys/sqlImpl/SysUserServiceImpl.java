package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysResourceService;
import com.sl.ue.service.sys.SysRoleService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@Service("sysUserSQL")
public class SysUserServiceImpl extends BaseSqlImpl<SysUserVO> implements SysUserService{

	@Autowired
	private SysRoleService SysRoleSQL;
	@Autowired
	private SysResourceService SysResourceSQL;
	
	@Override
	public List<SysRoleVO> findRoles(String username) {
		String sql = "select b.* from SYS_USER a, sys_role b, sys_user_role c "+
				"where a.username=? AND a.WebID=c.user_id AND b.id=c.role_id AND b.useble=1";
		return jdbcTemplate.queryForList(sql, SysRoleVO.class, username);
	}

	@Override
	public List<SysResourceVO> findResource(String username) {
		// 先查询出 roleid
		String sql_roleId = "select b.role_id AS roleId from SYS_USER a, sys_user_role b "+
				"where a.username=? and a.WebID=b.user_id";
		List<Integer>  roleIdList = jdbcTemplate.queryForList(sql_roleId, Integer.class, username);
		Object[] obj = roleIdList.toArray();
		// 根据roleid 查询权限
		String sql_per = "select b.* from sys_role_resource a, sys_resource b "+
				"where a.resource_id=b.id AND a.role_id in (?) AND b.useble=1";
		return jdbcTemplate.queryForList(sql_per, SysResourceVO.class, obj);
	}

	@Override
	public String getRoles(String token) {
		Result result = new Result();
		List<String> roles = new ArrayList<String>();
		
		SysUserVO sysUser = TokenUser.getUser();
		
		// 查询当前用户是否为管理员权限
		SysRoleVO sysRole = new SysRoleVO();
		sysRole.setLeftJoinTable(" left join sys_user_role b ON a.id=b.role_id");
		sysRole.setLeftJoinWhere(" AND b.user_id="+sysUser.getWebId());
		List<SysRoleVO>  roleList = SysRoleSQL.findList(sysRole);
		if(roleList.size() == 0){
			result.error(Result.error_103, "当前账号无权限");
			return result.toResult();
		}
		String isAdmin = ""; 
		String roleIds = ""; // 角色id  “,”分割
		for(SysRoleVO t : roleList){
			if(t.getIsAdmin() == -1 || t.getIsAdmin()==1 ){ //-1：超级管理员， 1：管理员
				isAdmin="admin";
				break;
			}
			roleIds += t.getId()+",";
		}
		if(StringUtils.isNotBlank(isAdmin)){
			roles.add(isAdmin);
			result.putJson(roles);
			return result.toResult();
		}
		//  非管理员权限，查询当前用户权限所能访问的资源
		SysResourceVO sysResource = new SysResourceVO();
		sysResource.setLeftJoinTable(" left join sys_role_resource b ON a.id=b.resource_id");
		sysResource.setLeftJoinWhere(" AND b.role_id in ("+StringUtil.lastComma(roleIds)+") AND a.useble=1");
		List<SysResourceVO> resourceList = SysResourceSQL.findList(sysResource);
		
		for(SysResourceVO t : resourceList){
			roles.add(t.getPathUrl());
		}
		
		result.putJson(roles);
		return result.toResult();
		
	}

}
