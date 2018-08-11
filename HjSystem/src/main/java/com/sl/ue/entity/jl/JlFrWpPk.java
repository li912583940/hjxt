package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_FR_WP_PK")
public class JlFrWpPk implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = 9148189772160563605L;
	// Fields
	@Id
	@DbField("WebID")
	private Long webId;
	@DbField("hjid")
	private Long hjid;
	@DbField("wpNo")
	private String wpNo;
	@DbField("wpCount")
	private Integer wpCount;


	public Long getWebId() {
		return this.webId;
	}

	public void setWebId(Long webId) {
		this.webId = webId;
	}

	public Long getHjid() {
		return this.hjid;
	}

	public void setHjid(Long hjid) {
		this.hjid = hjid;
	}

	public String getWpNo() {
		return this.wpNo;
	}

	public void setWpNo(String wpNo) {
		this.wpNo = wpNo;
	}

	public Integer getWpCount() {
		return this.wpCount;
	}

	public void setWpCount(Integer wpCount) {
		this.wpCount = wpCount;
	}

}