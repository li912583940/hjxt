package com.sl.ue.util.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.hj.HjdjAcdInfoService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.SpringTool;

public class CustomRealm extends AuthorizingRealm{

	@Autowired
	private SysUserService SysUserSQL;
	/* 
	 * 功能：[认证]
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 从 token 中获取用户身份信息
		String username = (String) token.getPrincipal();
		System.out.println("-- "+username);
		// 从 token 中获取用户密码
		
		char[] ca = (char[]) token.getCredentials();
		String password =new String(ca);
		System.out.println("-- "+password);
		
		// 通过 username 从数据库中查询
		HjdjAcdInfoService HjdjAcdInfoSQL = (HjdjAcdInfoService) SpringTool.getBean("hjdjAcdInfoSQL");
		HjdjAcdInfoSQL.baseFindOne(0);
		SysUser model = new SysUser();
		model.setUserName(username);
		List<SysUser> userList = SysUserSQL.baseFindList(model);
		if(userList.size() == 0){ // 如果查询不到用户信息
			return null;
		}
		//返回认证信息由父类 AuthenticatingRealm 进行认证
		SimpleAuthenticationInfo simple = new SimpleAuthenticationInfo(username, password, getName());
		return simple;
	}
	
	/* 
	 * 功能：[授权]
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取身份信息
		String username = (String) principals.getPrimaryPrincipal();
		// 根据身份信息从数据库中查询权限数据
        // 这里使用静态数据模拟
		List<String> permissions = new ArrayList<String>();
		permissions.add("user:*");
		permissions.add("department:*");
		
		// 将权限信息封闭为AuthorizationInfo
		SimpleAuthorizationInfo simple = new SimpleAuthorizationInfo();
		// 模拟数据，添加 manager 角色
		simple.addRole("manager");
		
		for(String s : permissions){
			simple.addStringPermission(s);
		}
		
		return simple;
	}

	

}
