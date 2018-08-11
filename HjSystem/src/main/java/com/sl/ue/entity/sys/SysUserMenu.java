package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("SYS_USER_MENU")
public class SysUserMenu implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = 6960358963682787876L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Group_No")
	private String groupNo;
	@DbField("Menu_No")
	private String menuNo;


	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

}