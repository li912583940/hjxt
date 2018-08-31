package com.sl.ue.entity.sys;

import java.io.Serializable;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [系统用户与角色关联表]
 * @author lxt
 */
@Table("sys_user_role")
public class SysUserRole implements Serializable{

	/** */
	private static final long serialVersionUID = 1L;

	@Id
	@DbField("user_id")
	private Integer userId;
	
	@Id
	@DbField("role_id")
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
