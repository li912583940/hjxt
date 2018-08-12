package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [用户]
 * @author lxt
 */
@RestController
@RequestMapping("/user")
public class SysUserWeb {

	@Autowired
	private SysUserService sysUserSQL;
	
	@RequestMapping("/findOne")
	public String findOne(){
		List<SysUser> list = sysUserSQL.baseFindList(new SysUser());
		return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 说明 [登录]
	 * @author lxt
	 */
	@RequestMapping("/login")
	public String login(String userNo, String userPwd){
		Result result = new Result();
		SysUser model = new SysUser();
		model.setUserNo(userNo);
		model.setUserPwd(userPwd);
		List<SysUser> list = sysUserSQL.baseFindList(model);
		//账号密码正确 允许登录，并返回相应数据
		if(list.size()>0){
			SysUser t = list.get(0);
			SysUser resultUser = new SysUser();
			resultUser.setUserNo(userNo);
			resultUser.setUserName(t.getUserName());
			return result.toString();
		}
		return result.toString();
	
	}
}
