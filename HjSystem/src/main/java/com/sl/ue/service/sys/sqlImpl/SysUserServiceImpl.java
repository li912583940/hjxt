package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.entity.sys.vo.SysUserRoleVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysResourceService;
import com.sl.ue.service.sys.SysRoleService;
import com.sl.ue.service.sys.SysUserRoleService;
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
	@Autowired
	private SysUserRoleService sysUserRoleSQL;
	
	@Override
	public Map<String, Object> findPojoJoin(SysUserVO model, Integer pageSize, Integer pageNum){
		StringBuffer Where = new StringBuffer(); // sql条件
    	if(StringUtils.isNotBlank(model.getUserName())){
    		String str = model.getUserName();
    		Where.append(" AND a.User_Name LIKE '%"+str+"%' ");
    		model.setUserName(null);
    	}
    	model.setLeftJoinWhere(Where.toString());
    	Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
    	return map;
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

	@Override
	public String getCheckedRole(Integer userId){
		Result result = new Result();
		if(userId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setUserId(userId);
		List<SysUserRoleVO> list = sysUserRoleSQL.findList(sysUserRole);
		List<Integer> reList = new ArrayList<Integer>();
		for(SysUserRoleVO t : list){
			reList.add(t.getRoleId());
		}
		result.putJson(reList);
		return result.toResult();
	}
	
	@Override
	public String addUserRole(Integer userId, String roles){
		Result result = new Result();
		if(userId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		// 先删
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setUserId(userId);
		sysUserRoleSQL.delete(sysUserRole);
		
		//再添加
		if(StringUtils.isNotBlank(roles)){
			for(String i : roles.split(",")){
				SysUserRoleVO t = new SysUserRoleVO();
				t.setUserId(userId);
				t.setRoleId(Integer.parseInt(i));
				sysUserRoleSQL.add(t);
			}
		}
		return result.toResult();
	}
}
