package com.sl.ue.service.sys.sqlImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.http.token.TokenSession;

@Service("sysUserSQL")
public class SysUserServiceImpl extends BaseSqlImpl<SysUserVO> implements SysUserService{

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
	public List<String> getRoles(String token) {
		TokenSession tokenSession = new TokenSession();
		SysUserVO sysUser = tokenSession.getUser(token);
		
		// 根据用户id 查询出角色id
		// 先查询出 roleid
		String sql_roleId = "select b.role_id AS roleId from SYS_USER a, sys_user_role b "+
				"where a.WebID=? and a.WebID=b.user_id";
		List<Integer>  roleIdList = jdbcTemplate.queryForList(sql_roleId, Integer.class, sysUser.getWebid());
		
		// 根据角色id查询资源
		Object[] obj = roleIdList.toArray();
		String sql_per = "select b.* from sys_role_resource a, sys_resource b "+
				"where a.resource_id=b.id AND a.role_id in (?) AND b.useble=1";
		 jdbcTemplate.queryForList(sql_per, SysResourceVO.class, obj);
		
		return null;
	}

}
