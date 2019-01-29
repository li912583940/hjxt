package com.sl.ue.service.jl.sqlImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjSpSetVO;
import com.sl.ue.entity.jl.vo.JlHjSpUserVO;
import com.sl.ue.entity.other.vo.DeptVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjSpSetService;
import com.sl.ue.service.jl.JlHjSpUserService;
import com.sl.ue.service.other.DeptService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.http.Result;

@Service("jlHjSpSetSQL")
public class JlHjSpSetServiceImpl extends BaseSqlImpl<JlHjSpSetVO> implements JlHjSpSetService{

	@Autowired
	private JlHjSpUserService jlHjSpUserSQL;
	@Autowired
	private DeptService deptSQL;
	@Autowired
	private SysUserService sysUserSQL;
	
	@Override
	public String findDetails(Integer id) {
		Result result = new Result();
		if(id==null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjSpSetVO jlHjSpSet = this.findOne(id);
		String gxValue = jlHjSpSet.getSpValue();
		if(StringUtils.isNotBlank(gxValue)){
			List<String> gxList = Arrays.asList(gxValue.split(","));
			result.putData("gxList", gxList);
		}
		result.putJson("jlHjSpSet", jlHjSpSet);
		
		List<Integer> deptList1 = new ArrayList<Integer>();
		List<String> userList1 = new ArrayList<String>();
		
		List<Integer> deptList2 = new ArrayList<Integer>();
		List<String> userList2 = new ArrayList<String>();
		
		List<Integer> deptList3 = new ArrayList<Integer>();
		List<String> userList3 = new ArrayList<String>();
		
		JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
		jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
		List<JlHjSpUserVO> jlHjSpUserList = jlHjSpUserSQL.findList(jlHjSpUser);
		for(JlHjSpUserVO t : jlHjSpUserList){
			if(t.getSpLevel()==1){ // 第一级
				if(t.getSpType()==1){ // 部门
					deptList1.add(t.getSpDeptId());
				}else if(t.getSpType()==0){ // 用户
					userList1.add(t.getSpUserNo());
				}
			}else if(t.getSpLevel()==2){
				if(t.getSpType()==1){ // 部门
					deptList2.add(t.getSpDeptId());
				}else if(t.getSpType()==0){ // 用户
					userList2.add(t.getSpUserNo());
				}
			}else if(t.getSpLevel()==3){
				if(t.getSpType()==1){ // 部门
					deptList3.add(t.getSpDeptId());
				}else if(t.getSpType()==0){ // 用户
					userList3.add(t.getSpUserNo());
				}
			}
		}
		result.putData("deptList1", deptList1);
		result.putData("userList1", userList1);
		
		result.putData("deptList2", deptList2);
		result.putData("userList2", userList2);
		
		result.putData("deptList3", deptList3);
		result.putData("userList3", userList3);
		return result.toResult();
	}

	@Override
	public String spConf(Integer id, String spExplain, Integer usable,
			String gxValue,
			String deptValue1, String userValue1, 
			String deptValue2, String userValue2,
			String deptValue3, String userValue3) {
		Result result = new Result();
		JlHjSpSetVO  jlHjSpSet = null;
		if(id==null){
			result.error(Result.error_102);
			return result.toResult();
		}else{
			jlHjSpSet = this.findOne(id);
			JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
			jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
			jlHjSpUserSQL.delete(jlHjSpUser);
		}
		int maxNum=0;
		if(StringUtils.isNotBlank(deptValue1)){ // 一级审批部门
			for(String deptId : deptValue1.split(",")){
				JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
				jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
				jlHjSpUser.setSpDeptId(Integer.parseInt(deptId));
				DeptVO dept = deptSQL.findOne(Integer.parseInt(deptId));
				jlHjSpUser.setSpDeptName(dept.getDeptName());
				jlHjSpUser.setSpLevel(1);
				jlHjSpUser.setSpType(1);
				jlHjSpUserSQL.add(jlHjSpUser);
			}
			maxNum=1;
		}
		if(StringUtils.isNotBlank(userValue1)){ // 一级审批用户
			for(String userNo: userValue1.split(",")){
				JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
				jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
				jlHjSpUser.setSpUserNo(userNo);
				SysUserVO sysUser = new SysUserVO();
				sysUser.setUserNo(userNo);
				List<SysUserVO> userList = sysUserSQL.findList(sysUser);
				if(userList.size()>0){
					jlHjSpUser.setSpUserName(userList.get(0).getUserName());
				}
				jlHjSpUser.setSpLevel(1);
				jlHjSpUser.setSpType(0);
				jlHjSpUserSQL.add(jlHjSpUser);
			}
			maxNum=1;
		}
		if(StringUtils.isNotBlank(deptValue2)){ // 二级审批部门
			for(String deptId : deptValue2.split(",")){
				JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
				jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
				jlHjSpUser.setSpDeptId(Integer.parseInt(deptId));
				DeptVO dept = deptSQL.findOne(Integer.parseInt(deptId));
				jlHjSpUser.setSpDeptName(dept.getDeptName());
				jlHjSpUser.setSpLevel(2);
				jlHjSpUser.setSpType(1);
				jlHjSpUserSQL.add(jlHjSpUser);
			}
			maxNum=2;
		}
		if(StringUtils.isNotBlank(userValue2)){ // 二级审批用户
			for(String userNo: userValue2.split(",")){
				JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
				jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
				jlHjSpUser.setSpUserNo(userNo);
				SysUserVO sysUser = new SysUserVO();
				sysUser.setUserNo(userNo);
				List<SysUserVO> userList = sysUserSQL.findList(sysUser);
				if(userList.size()>0){
					jlHjSpUser.setSpUserName(userList.get(0).getUserName());
				}
				jlHjSpUser.setSpLevel(2);
				jlHjSpUser.setSpType(0);
				jlHjSpUserSQL.add(jlHjSpUser);
			}
			maxNum=2;
		}
		
		if(StringUtils.isNotBlank(deptValue3)){ // 三级审批部门
			for(String deptId : deptValue3.split(",")){
				JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
				jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
				jlHjSpUser.setSpDeptId(Integer.parseInt(deptId));
				DeptVO dept = deptSQL.findOne(Integer.parseInt(deptId));
				jlHjSpUser.setSpDeptName(dept.getDeptName());
				jlHjSpUser.setSpLevel(3);
				jlHjSpUser.setSpType(1);
				jlHjSpUserSQL.add(jlHjSpUser);
			}
			maxNum=3;
		}
		if(StringUtils.isNotBlank(userValue3)){ // 三级审批用户
			for(String userNo: userValue3.split(",")){
				JlHjSpUserVO jlHjSpUser = new JlHjSpUserVO();
				jlHjSpUser.setSpSetNo(jlHjSpSet.getSpNo());
				jlHjSpUser.setSpUserNo(userNo);
				SysUserVO sysUser = new SysUserVO();
				sysUser.setUserNo(userNo);
				List<SysUserVO> userList = sysUserSQL.findList(sysUser);
				if(userList.size()>0){
					jlHjSpUser.setSpUserName(userList.get(0).getUserName());
				}
				jlHjSpUser.setSpLevel(3);
				jlHjSpUser.setSpType(0);
				jlHjSpUserSQL.add(jlHjSpUser);
			}
			maxNum=3;
		}
		jlHjSpSet.setId(id);
		jlHjSpSet.setSpExplain(spExplain);
		jlHjSpSet.setUsable(usable);
		jlHjSpSet.setMaxNum(maxNum);
		jlHjSpSet.setSpValue(gxValue);
		this.edit(jlHjSpSet);
		return result.toResult();
	}

}
