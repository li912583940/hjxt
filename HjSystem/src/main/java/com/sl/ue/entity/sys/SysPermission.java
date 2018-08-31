package com.sl.ue.entity.sys;

import java.io.Serializable;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [系统权限表]
 * @author lxt
 */
@Table("sys_permission")
public class SysPermission implements Serializable{

	/** */
	private static final long serialVersionUID = 1L;

	@Id
	@DbField("id")
	private Integer id;
	
	/** 权限标识符，用于在程序中进行隐式权限判断的 */
	@DbField("permission")
	private String permission;
	
	/** 描述 */
	@DbField("description")
	private String description;
	
	/** 是否可用，1：可用，0：不可用 */
	@DbField("useble")
	private Integer useble;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUseble() {
		return useble;
	}

	public void setUseble(Integer useble) {
		this.useble = useble;
	}
	
	
}
