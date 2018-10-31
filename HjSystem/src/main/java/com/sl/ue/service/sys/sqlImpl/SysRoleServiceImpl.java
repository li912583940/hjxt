package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.List;

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
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysResourceService;
import com.sl.ue.service.sys.SysRoleJqService;
import com.sl.ue.service.sys.SysRoleResourceService;
import com.sl.ue.service.sys.SysRoleService;
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
	
	@Override
	public String getMenuTree() {
		Result result = new Result();
		
		SysResourceVO sysResource = new SysResourceVO();
		sysResource.setType("menu");
		sysResource.setUseble(1);
		List<SysResourceVO> list = sysResourceSQL.findList(sysResource);
		JSONArray jArray1 = new JSONArray();
		JSONObject jObject1 = new JSONObject();
		jObject1.put("id", -1);
		jObject1.put("label", "全选");
		
		JSONArray jArray2 = new JSONArray();
		for(SysResourceVO t : list){
			JSONObject jObject2 = new JSONObject();
			jObject2.put("id", t.getId());
			jObject2.put("label", t.getName());
			jArray2.add(jObject2);
		}
		jObject1.put("children", jArray2);
		jArray1.add(jObject1);
		result.putJson(jArray1);
		return result.toResult();
	}

	@Override
	public String getJqTree() {
		Result result = new Result();
		
		JlJqVO jlJq = new JlJqVO();
		List<JlJqVO> list = jlJqSQL.findList(jlJq);
		JSONArray jArray1 = new JSONArray();
		JSONObject jObject1 = new JSONObject();
		jObject1.put("id", -1);
		jObject1.put("label", "全选");
		
		JSONArray jArray2 = new JSONArray();
		for(JlJqVO t : list){
			JSONObject jObject2 = new JSONObject();
			jObject2.put("id", t.getWebId());
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
		
		SysRoleResourceVO sysRoleResource = new SysRoleResourceVO();
		sysRoleResource.setRoleId(roleId);
		sysRoleResource.setType("menu");
		List<SysRoleResourceVO> list = sysRoleResourceSQL.findList(sysRoleResource);
		List<Integer> reList = new ArrayList<Integer>();
		for(SysRoleResourceVO t : list){
			reList.add(t.getResourceId());
		}
		result.putJson(reList);
		return result.toResult();
	}

	@Override
	public String getCheckedJq(Integer roleId) {
		Result result = new Result();
		
		SysRoleJqVO sysRoleJq = new SysRoleJqVO();
		sysRoleJq.setRoleId(roleId);
		List<SysRoleJqVO> list = sysRoleJqSQL.findList(sysRoleJq);
		
		List<Integer> reList = new ArrayList<Integer>();
		for(SysRoleJqVO t : list){
			reList.add(t.getJqId());
		}
		result.putJson(reList);
		return result.toResult();
	}

	@Override
	public String addRoleMenu(Integer roleId, String menus) {
		Result result = new Result();
		
		int authorityResource = 0; // 角色是否已设置菜单权限
		// 先删
		SysRoleResourceVO sysRoleResource = new SysRoleResourceVO();
		sysRoleResource.setRoleId(roleId);
		sysRoleResourceSQL.delete(sysRoleResource);
		
		// 再添加
		if(StringUtils.isNotBlank(menus)){
			for(String i : menus.split(",")){
				if("-1".equals(i)){
					continue;
				}
				SysRoleResourceVO t = new SysRoleResourceVO();
				t.setRoleId(roleId);
				t.setResourceId(Integer.parseInt(i));
				t.setType("menu");
				sysRoleResourceSQL.add(t);
			}
			authorityResource=1;
		}
		
		SysRoleVO sysRole = this.findOne(roleId);
		sysRole.setAuthorityResource(authorityResource);
		this.edit(sysRole);
		
		return result.toResult();
	}

	@Override
	public String addRoleJq(Integer roleId, String jqs) {
		Result result = new Result();
		
		int authorityJq = 0; //角色是否已设置监区权限
		// 先删
		SysRoleJqVO sysRoleJq = new SysRoleJqVO();
		sysRoleJq.setRoleId(roleId);
		sysRoleJqSQL.delete(sysRoleJq);
		
		// 再添加
		if(StringUtils.isNotBlank(jqs)){
			for(String i : jqs.split(",")){
				if("-1".equals(i)){
					continue;
				}
				SysRoleJqVO t = new SysRoleJqVO();
				t.setRoleId(roleId);
				t.setJqId(Integer.parseInt(i));
				sysRoleJqSQL.add(t);
			}
			authorityJq=1;
		}
		
		SysRoleVO sysRole = this.findOne(roleId);
		sysRole.setAuthorityJq(authorityJq);
		this.edit(sysRole);
		
		return result.toResult();
	}
}
