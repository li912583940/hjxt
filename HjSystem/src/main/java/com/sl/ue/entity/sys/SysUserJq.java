package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_USER_JQ")
public class SysUserJq implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -3407824329436972903L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Group_No")
	private String groupNo;
	@DbField("JY")
	private String jy;
	@DbField("JQ_No")
	private String jqNo;


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

	public String getJy() {
		return this.jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public String getJqNo() {
		return this.jqNo;
	}

	public void setJqNo(String jqNo) {
		this.jqNo = jqNo;
	}

}