package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("JL_YJ_ABDOOR")
public class JlYjABDoor implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -2259746368057226843L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("YJ_No")
	private String yjNo;
	@DbField("YJ_Name")
	private String yjName;


	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getYjNo() {
		return yjNo;
	}

	public void setYjNo(String yjNo) {
		this.yjNo = yjNo;
	}

	public String getYjName() {
		return yjName;
	}

	public void setYjName(String yjName) {
		this.yjName = yjName;
	}

	
}