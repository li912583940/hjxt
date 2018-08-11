package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_JQ_WEEK")
public class JlHjJqWeek implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = -7883676699643925610L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("JQ_No")
	private String jqNo;
	@DbField("JY")
	private String jy;
	@DbField("JQ_Week")
	private Integer jqWeek;
	

	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getJqNo() {
		return this.jqNo;
	}

	public void setJqNo(String jqNo) {
		this.jqNo = jqNo;
	}

	public String getJy() {
		return this.jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public Integer getJqWeek() {
		return this.jqWeek;
	}

	public void setJqWeek(Integer jqWeek) {
		this.jqWeek = jqWeek;
	}

}