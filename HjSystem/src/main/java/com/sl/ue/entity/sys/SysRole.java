package com.sl.ue.entity.sys;

import java.io.Serializable;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [系统角色表]
 * @author lxt
 */
@Table("sys_role")
public class SysRole implements Serializable{

	
	/** */
	private static final long serialVersionUID = -2872034496477600447L;

	@Id
	@DbField("id")
	private Integer id;
	
	/** 角色标识符 */
	@DbField("role")
	private String role;
	
	/** 角色描述 */
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
