package com.sl.ue.util.http.token;

import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.util.http.WebContextUtil;

/**
 * 说明 [http会话, 管理token与user]
 * L_晓天  @2018年9月20日
 */
public class TokenSession {

	public void setTokenUser(String token, SysUserVO user){
		WebContextUtil.getRequest().getSession().setAttribute(token, user);
	}
	
	public SysUserVO getUser(String token){
		return (SysUserVO) WebContextUtil.getRequest().getSession().getAttribute(token);
	}
}
