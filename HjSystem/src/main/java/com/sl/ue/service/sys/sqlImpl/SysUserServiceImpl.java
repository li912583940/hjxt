package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.entity.sys.vo.SysRoleJqVO;
import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.entity.sys.vo.SysUserRoleVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysResourceService;
import com.sl.ue.service.sys.SysRoleJqService;
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
	@Autowired
	private SysRoleJqService sysRoleJqSQL;
	
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
		List<String> roles = new ArrayList<String>();  //菜单权限
		
		//存放按钮权限
		JSONObject obj = new JSONObject();
		
		SysUserVO sysUser = TokenUser.getUser();
		String isAdmin = ""; 
		if(sysUser.getIsSuper()==1){
			isAdmin="admin";
		}
		if(StringUtils.isNotBlank(isAdmin)){
			roles.add(isAdmin);
			result.putJson(roles);
			return result.toResult();
		}
		// 查询当前用户是否为管理员权限
		SysUserRoleVO userRole = new SysUserRoleVO();
		userRole.setUserId(sysUser.getWebId());
		List<SysUserRoleVO> userRoleList = sysUserRoleSQL.findList(userRole);
		if(userRoleList.size() == 0){
			result.error(Result.error_103, "当前账号无权限");
			return result.toResult();
		}
		String roleIds = ""; // 角色id  “,”分割
		for(SysUserRoleVO t : userRoleList){
			roleIds += t.getRoleId()+",";
		}
	
		//  非管理员权限，查询当前用户权限所能访问的资源
		SysResourceVO sysResource = new SysResourceVO();
		sysResource.setLeftJoinTable(" left join sys_role_resource b ON a.id=b.resource_id");
		sysResource.setLeftJoinWhere(" AND b.role_id in ("+StringUtil.lastComma(roleIds)+") AND a.useble=1");
		List<SysResourceVO> resourceList = SysResourceSQL.findList(sysResource);
		
		// 需要计算真实的菜单权限，因为
		Map<Integer,SysResourceVO> menuMap = new HashMap<>();
		List<SysResourceVO> buttonList = new ArrayList<>();
		Set<Integer> menuId = new HashSet<Integer>(); //存储按钮所属的菜单
		for(SysResourceVO t : resourceList){
			if("menu".equals(t.getType())){
				roles.add(t.getPathUrl());
				menuMap.put(t.getId(), t);
			}else if("button".equals(t.getType())){
				buttonList.add(t);
				menuId.add(t.getParentId());
			}
		}
		
		for(Integer id: menuId){
			JSONArray ary = new JSONArray();
			for(SysResourceVO t: buttonList){
				if(t.getParentId() == id){
					ary.add(t.getPathUrl());
				}
			}
			obj.put(menuMap.get(id).getPathUrl(), ary);
		}
		result.putJson(roles);
		result.putJson("buttonData", obj);
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
	
	public String getJqs(SysUserVO loginUser){
		if(loginUser.getIsSuper()==1){
			return "admin";
		}
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setUserId(loginUser.getWebId());
		List<SysUserRoleVO> roleList = sysUserRoleSQL.findList(sysUserRole);
		String roles = "";
		for(SysUserRoleVO t : roleList){
			roles+=t.getRoleId()+",";
		}
		if(StringUtils.isBlank(roles)){
			return "";
		}
		SysRoleJqVO roleJq = new SysRoleJqVO();
		roleJq.setLeftJoinWhere(" AND a.role_id in ("+StringUtil.lastComma(roles)+")");
		List<SysRoleJqVO> roleJqList = sysRoleJqSQL.findList(roleJq);
		String jqs = "";
		for(SysRoleJqVO t : roleJqList){
			jqs+="'"+t.getJqId()+"',";
		}
		return StringUtil.lastComma(jqs);
	}
}
