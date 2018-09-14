package com.sl.ue.entity.sys.vo;

import com.sl.ue.entity.sys.SysUser;

public class SysUserVO extends SysUser{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** token */
    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
    
}
