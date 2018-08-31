package com.sl.ue.service.sys.sqlImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysUserService;

@Service("sysUserSQL")
public class SysUserServiceImpl extends BaseSqlImpl<SysUserVO> implements SysUserService{

	@Override
	public Set<String> findRoles(String username) {
		String sql = "select b.role from SYS_USER a, sys_role b, sys_user_role c "+
				"where a.username=? and a.WebID=c.user_id and b.id=c.role_id";
		return new HashSet<String>(jdbcTemplate.queryForList(sql, String.class, username));
	}

	@Override
	public Set<String> findPermissions(String username) {
		// 先查询出 roleid
		String sql_roleId = "select b.role_id AS roleId from SYS_USER a, sys_user_role b "+
				"where a.username=? and a.WebID=b.user_id";
		List<Integer>  roleIdList = jdbcTemplate.queryForList(sql_roleId, Integer.class, username);
		Object[] obj = roleIdList.toArray();
		// 根据roleid 查询权限
		String sql_per = "select from sys_role_permission a, sys_permission b "+
				"where a.permission_id=b.id and a.role_id in (?)";
		List<String> perList = jdbcTemplate.queryForList(sql_per, String.class, obj);
		return new HashSet<String>(perList);
	}

}
