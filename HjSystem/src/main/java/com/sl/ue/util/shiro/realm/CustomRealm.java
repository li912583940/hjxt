package com.sl.ue.util.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.component.SpringTool;

public class CustomRealm extends AuthorizingRealm{

	SysUserService sysUserSQL = (SysUserService) SpringTool.getBean("sysUserSQL");
	
	/* 
	 * 功能：[认证]
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 从 token 中获取用户身份信息
		String username = (String) token.getPrincipal();
		// 从 token 中获取用户密码
		String password =new String((char[]) token.getCredentials());
		
		
		// 通过 username 从数据库中查询
		SysUserVO model = new SysUserVO();
		model.setUserName(username);
		model.setUserPwd(password);
		List<SysUserVO> userList = sysUserSQL.findList(model);
		if(userList.size() == 0){ // 如果查询不到用户信息
			throw new UnknownAccountException(); //如果用户名错误
			//throw new IncorrectCredentialsException(); //如果密码错误
		}
		//SysUser user = userList.get(0);
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
		
		// 将权限信息封闭为AuthorizationInfo
		SimpleAuthorizationInfo simple = new SimpleAuthorizationInfo();
		simple.setRoles(sysUserSQL.findRoles(username));
		simple.setStringPermissions(sysUserSQL.findPermissions(username));
		return simple;
	}

	

}
