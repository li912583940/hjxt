package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_MENU")
public class SysMenu implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -7633469644025170857L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Menu_No")
	private String menuNo;
	@DbField("Menu_Name")
	private String menuName;
	@DbField("Menu_Url")
	private String menuUrl;
	@DbField("Menu_Index")
	private Integer menuIndex;
	@DbField("Menu_Admin")
	private Integer menuAdmin;


	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getMenuIndex() {
		return this.menuIndex;
	}

	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}

	public Integer getMenuAdmin() {
		return this.menuAdmin;
	}

	public void setMenuAdmin(Integer menuAdmin) {
		this.menuAdmin = menuAdmin;
	}

}