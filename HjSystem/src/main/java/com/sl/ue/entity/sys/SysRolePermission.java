package com.sl.ue.entity.sys;

import java.io.Serializable;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [系统角色与权限关联表]
 * @author lxt
 */
@Table("sys_role_permission")
public class SysRolePermission implements Serializable{

	/** */
	private static final long serialVersionUID = 1L;

	@Id
	@DbField("role_id")
	private Integer roleId;
	
	@Id
	@DbField("permission_id")
	private Integer permissionId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	
	
}
