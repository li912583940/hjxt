package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_JB")
public class JlJb implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -7785962855203180609L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("JB_No")
	private String jbNo;
	@DbField("JB_Name")
	private String jbName;
	@DbField("QQ_Count")
	private Integer qqCount;
	@DbField("QQ_Time")
	private Integer qqTime;
	@DbField("HJ_Count")
	private Integer hjCount;
	@DbField("HJ_Time")
	private Integer hjTime;
	@DbField("Auto_Down")
	private Integer autoDown;
	@DbField("Down_Time")
	private Integer downTime;
	@DbField("Down_JB")
	private String downJb;
	@DbField("HJ_QY")
	private Integer hjQy;
	@DbField("record_over_time")
	private Integer recordOverTime;
	
	public Integer getWebId() {
		return this.webId;
	}

	public Integer getHjQy() {
		return hjQy;
	}

	public void setHjQy(Integer hjQy) {
		this.hjQy = hjQy;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getJbNo() {
		return this.jbNo;
	}

	public void setJbNo(String jbNo) {
		this.jbNo = jbNo;
	}

	public String getJbName() {
		return this.jbName;
	}

	public void setJbName(String jbName) {
		this.jbName = jbName;
	}

	public Integer getQqCount() {
		return this.qqCount;
	}

	public void setQqCount(Integer qqCount) {
		this.qqCount = qqCount;
	}

	public Integer getQqTime() {
		return this.qqTime;
	}

	public void setQqTime(Integer qqTime) {
		this.qqTime = qqTime;
	}

	public Integer getHjCount() {
		return this.hjCount;
	}

	public void setHjCount(Integer hjCount) {
		this.hjCount = hjCount;
	}

	public Integer getHjTime() {
		return this.hjTime;
	}

	public void setHjTime(Integer hjTime) {
		this.hjTime = hjTime;
	}

	public Integer getAutoDown() {
		return this.autoDown;
	}

	public void setAutoDown(Integer autoDown) {
		this.autoDown = autoDown;
	}

	public Integer getDownTime() {
		return this.downTime;
	}

	public void setDownTime(Integer downTime) {
		this.downTime = downTime;
	}

	public String getDownJb() {
		return this.downJb;
	}

	public void setDownJb(String downJb) {
		this.downJb = downJb;
	}

	public Integer getRecordOverTime() {
		return recordOverTime;
	}

	public void setRecordOverTime(Integer recordOverTime) {
		this.recordOverTime = recordOverTime;
	}

}