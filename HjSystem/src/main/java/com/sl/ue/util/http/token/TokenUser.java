package com.sl.ue.util.http.token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sl.ue.entity.sys.vo.SysAccessTokenVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.sys.SysAccessTokenService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.component.SpringTool;
import com.sl.ue.util.http.WebContextUtil;

/**
 * 说明 [登陆用户]
 * L_晓天  @2018年10月10日
 */
public class TokenUser {
	
	/**
	 * 说明 [根据登录者token获取用户信息]
	 * L_晓天  @2018年10月10日
	 */
	public static SysUserVO getUser(){
		SysUserService sysUserSQL = (SysUserService) SpringTool.getBean("sysUserSQL");
		SysAccessTokenService sysAccessTokenSQL = (SysAccessTokenService) SpringTool.getBean("sysAccessTokenSQL");
		// 从 request header 中获取当前 token
		String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
		SysAccessTokenVO sysAccessToken = new SysAccessTokenVO();
		sysAccessToken.setAccessToken(token);
		List<SysAccessTokenVO> sysAccesstokenList = sysAccessTokenSQL.findList(sysAccessToken);
		if(sysAccesstokenList.size()>0){
			SysUserVO sysUser = sysUserSQL.findOne(sysAccesstokenList.get(0).getUserId());
			return sysUser;
		}
		return new SysUserVO();
	}
}
