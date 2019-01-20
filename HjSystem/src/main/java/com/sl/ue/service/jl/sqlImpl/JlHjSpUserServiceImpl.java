package com.sl.ue.service.jl.sqlImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjSpUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjSpUserService;
import com.sl.ue.util.http.Result;

@Service("jlHjSpUserSQL")
public class JlHjSpUserServiceImpl extends BaseSqlImpl<JlHjSpUserVO> implements JlHjSpUserService{

	@Override
	public String findSpeedSpUser(String spSetNo, Integer speedProgress) {
		Result result =new Result();
		// 部门
		JlHjSpUserVO dept= new JlHjSpUserVO();
		dept.setSpSetNo(spSetNo);
		dept.setSpLevel(speedProgress);
		dept.setSpType(1);
		List<JlHjSpUserVO> deptList = this.findList(dept);
		result.putJson("deptList", deptList);
		
		// 用户
		JlHjSpUserVO user = new JlHjSpUserVO();
		user.setSpSetNo(spSetNo);
		user.setSpLevel(speedProgress);
		user.setSpType(0);
		List<JlHjSpUserVO> userList = this.findList(user);
		result.putJson("userList", userList);
		return result.toResult();
	}

}
