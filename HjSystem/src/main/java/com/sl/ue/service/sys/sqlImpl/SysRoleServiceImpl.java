package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.entity.sys.vo.SysRoleJqVO;
import com.sl.ue.entity.sys.vo.SysRoleResourceVO;
import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.entity.sys.vo.SysUserRoleVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysResourceService;
import com.sl.ue.service.sys.SysRoleJqService;
import com.sl.ue.service.sys.SysRoleResourceService;
import com.sl.ue.service.sys.SysRoleService;
import com.sl.ue.service.sys.SysUserRoleService;
import com.sl.ue.util.http.Result;

@Service("sysRoleSQL")
public class SysRoleServiceImpl extends BaseSqlImpl<SysRoleVO> implements SysRoleService{

	@Autowired
	private JlJqService jlJqSQL;
	@Autowired
	private SysRoleResourceService sysRoleResourceSQL;
	@Autowired
	private SysRoleJqService sysRoleJqSQL;
	@Autowired
	private SysResourceService sysResourceSQL;
	@Autowired
	private SysUserRoleService sysUserRoleSQL;
	
	@Override
	public Map<String, Object> findPojoJoin(SysRoleVO model, Integer pageSize, Integer pageNum){
		StringBuffer field = new StringBuffer(); // sql关联字段
		field.append(",b.User_Name AS createUserName");
		
		StringBuffer table = new StringBuffer(); // sql关联表
		table.append(" left join SYS_USER b ON a.create_user_id=b.WebID");
		
		StringBuffer Where = new StringBuffer(); // sql条件
    	if(StringUtils.isNotBlank(model.getName())){
    		String str = model.getName();
    		Where.append(" AND a.name LIKE '%"+str+"%' ");
    		model.setName(null);
    	}
    	model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
    	model.setLeftJoinWhere(Where.toString());
    	Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
    	return map;
	}
	
	@Override
	public String getMenuTree() {
		Result result = new Result();
		JSONArray jArray = getMenuTreeBySQL(null);
		result.putJson(jArray);
		return result.toResult();
	}

	private JSONArray getMenuTreeBySQL(Integer resourceId){
		String where ="";
		if(resourceId != null){
			where = "AND parent_id="+resourceId;
		}else{
			where = "AND parent_id is NULL";
		}
		String sql = "SELECT * FROM sys_resource where 1=1 AND useble=1 "+where+" ORDER BY sort ASC";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			JSONArray ary = new JSONArray();
			for(Map<String, Object> map : list){
				JSONObject obj = new JSONObject();
				Integer id = (Integer) map.get("id");
				String name = (String) map.get("name");
				JSONArray children = getMenuTreeBySQL(id);
				if(children != null){
					obj.put("children", children);
				}
				obj.put("id", id);
				obj.put("label", name);
				ary.add(obj);
			}
			return ary;
		}
		
		return null;
	} 
	private JSONArray getMenuTreeByMethod(List<SysResourceVO> list){
		JSONArray jArray1 = new JSONArray();//第一级
		JSONObject jObject1 = new JSONObject();
		jObject1.put("id", -1);
		jObject1.put("label", "全选");
		
		JSONArray jArray2 = new JSONArray(); //第二级
		for(SysResourceVO t : list){
			if(t.getParentId() != null && t.getParentId() == 0){
				JSONObject jObject2 = new JSONObject();
				jObject2.put("id", t.getId());
				jObject2.put("label", t.getName());
				jArray2.add(jObject2);
				
				JSONArray jArray3 = new JSONArray(); //第三级
				for(SysResourceVO children : list){
					if(t.getId() == children.getParentId()){
						JSONObject jObject3 = new JSONObject();
						jObject3.put("id", children.getId());
						jObject3.put("label", children.getName());
						jArray3.add(jObject3);
					}
				}
				
				if(jArray3.size()>0){ //如果有子集 就添加到jObject2中
					jObject2.put("children", jArray3);
				}
			}
		}
		jObject1.put("children", jArray2);
		jArray1.add(jObject1);
		return jArray1;
	}
	
	@Override
	public String getJqTree() {
		Result result = new Result();
		
		JlJqVO jlJq = new JlJqVO();
		List<JlJqVO> list = jlJqSQL.findList(jlJq);
		JSONArray jArray1 = new JSONArray();
		JSONObject jObject1 = new JSONObject();
		jObject1.put("id", -2);
		jObject1.put("label", "全选");
		
		JSONArray jArray2 = new JSONArray();
		for(JlJqVO t : list){
			JSONObject jObject2 = new JSONObject();
			jObject2.put("id", t.getJqNo());
			jObject2.put("label", t.getJqName());
			jArray2.add(jObject2);
		}
		jObject1.put("children", jArray2);
		jArray1.add(jObject1);
		result.putJson(jArray1);
		return result.toResult();
	}

	@Override
	public String getCheckedMenu(Integer roleId) {
		Result result = new Result();
		if(roleId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
//		SysRoleResourceVO sysRoleResource = new SysRoleResourceVO();
//		sysRoleResource.setRoleId(roleId);
//		sysRoleResource.setType("menu");
//		List<SysRoleResourceVO> list = sysRoleResourceSQL.findList(sysRoleResource);
//		List<Integer> reList = new ArrayList<Integer>();
//		for(SysRoleResourceVO t : list){
//			reList.add(t.getResourceId());
//		}
		String[] s = {};
		SysRoleVO sysRole = this.findOne(roleId);
		if(StringUtils.isNotBlank(sysRole.getResources())){
			s = sysRole.getResources().split(",");
		}
		result.putJson(s);
		return result.toResult();
	}

	@Override
	public String getCheckedJq(Integer roleId) {
		Result result = new Result();
		if(roleId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		SysRoleJqVO sysRoleJq = new SysRoleJqVO();
		sysRoleJq.setRoleId(roleId);
		List<SysRoleJqVO> list = sysRoleJqSQL.findList(sysRoleJq);
		
		List<String> reList = new ArrayList<String>();
		for(SysRoleJqVO t : list){
			reList.add(t.getJqId());
		}
		result.putJson(reList);
		return result.toResult();
	}

	@Override
	public String addRoleMenu(Integer roleId, String menus) {
		Result result = new Result();
		if(roleId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		int authorityResource = 0; // 角色是否已设置菜单权限
		// 先删
		SysRoleResourceVO sysRoleResource = new SysRoleResourceVO();
		sysRoleResource.setRoleId(roleId);
		sysRoleResourceSQL.delete(sysRoleResource);
		
		// 再添加
		if(StringUtils.isNotBlank(menus)){
			List<String> oldMenuList = Arrays.asList(menus.split(","));
			List<String> menuList = new ArrayList(oldMenuList);
			SysResourceVO sysResource = new SysResourceVO();
			sysResource.setLeftJoinWhere(" AND a.id in ("+menus+")");
			List<SysResourceVO> sysResourceList = sysResourceSQL.findList(sysResource);
			//先将现成的数据插入
			for(SysResourceVO t : sysResourceList){
				SysRoleResourceVO sysRR = new SysRoleResourceVO();
				sysRR.setRoleId(roleId);
				sysRR.setResourceId(t.getId());
				sysRR.setType(t.getType());
				sysRoleResourceSQL.add(sysRR);
			}
			// 再执行递归插入，这样避免插入重复数据时报错
			for(SysResourceVO t : sysResourceList){
				handleCheckedMenuId(roleId, t, menuList);
			}
			authorityResource=1;
		}
		
		//更新反选的资源
		SysRoleVO sysRole = this.findOne(roleId);
		String resources = StringUtils.isNotBlank(menus)?menus:"";
		sysRole.setResources(resources);
		sysRole.setAuthorityResource(authorityResource);
		this.edit(sysRole);
		
		return result.toResult();
	}

	/**
	 * 说明 [因为前端element树形控件只传了选中的id，而对于上一级的id当成未选中状态（非全选状态下）并没有传递。所以需要计算出上一级的id并插入到数据库中]
	 * L_晓天  @2018年11月4日
	 */
	private void handleCheckedMenuId(Integer roleId, SysResourceVO sysResource,List<String> menuList){
		if(sysResource.getParentId()!=null){
			if(!menuList.contains(sysResource.getParentId()+"")){
				SysResourceVO oldSysResource = sysResourceSQL.findOne(sysResource.getParentId());
				SysRoleResourceVO sysRR = new SysRoleResourceVO();
				sysRR.setRoleId(roleId);
				sysRR.setResourceId(oldSysResource.getId());
				sysRR.setType(oldSysResource.getType());
				sysRoleResourceSQL.add(sysRR);
				menuList.add(oldSysResource.getId()+"");
				handleCheckedMenuId(roleId, oldSysResource, menuList);
			}
		}
	}
	@Override
	public String addRoleJq(Integer roleId, String jqs) {
		Result result = new Result();
		if(roleId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		int authorityJq = 0; //角色是否已设置监区权限
		// 先删
		SysRoleJqVO sysRoleJq = new SysRoleJqVO();
		sysRoleJq.setRoleId(roleId);
		sysRoleJqSQL.delete(sysRoleJq);
		
		// 再添加
		if(StringUtils.isNotBlank(jqs)){
			for(String i : jqs.split(",")){
				if("-2".equals(i)){
					continue;
				}
				SysRoleJqVO t = new SysRoleJqVO();
				t.setRoleId(roleId);
				t.setJqId(i);
				sysRoleJqSQL.add(t);
			}
			authorityJq=1;
		}
		
		SysRoleVO sysRole = this.findOne(roleId);
		sysRole.setAuthorityJq(authorityJq);
		this.edit(sysRole);
		
		return result.toResult();
	}

	@Override
	public String getCheckedUser(Integer roleId) {
		Result result = new Result();
		if(roleId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setRoleId(roleId);
		List<SysUserRoleVO> list = sysUserRoleSQL.findList(sysUserRole);
		List<Integer> reList = new ArrayList<Integer>();
		for(SysUserRoleVO t : list){
			reList.add(t.getUserId());
		}
		result.putJson(reList);
		return result.toResult();
	}

	@Override
	public String addRoleUser(Integer roleId, String users) {
		Result result = new Result();
		if(roleId == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		// 先删
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setRoleId(roleId);
		sysUserRoleSQL.delete(sysUserRole);
		
		//再添加
		if(StringUtils.isNotBlank(users)){
			for(String i : users.split(",")){
				SysUserRoleVO t = new SysUserRoleVO();
				t.setRoleId(roleId);
				t.setUserId(Integer.parseInt(i));
				sysUserRoleSQL.add(t);
			}
		}
		return result.toResult();
	}
}
