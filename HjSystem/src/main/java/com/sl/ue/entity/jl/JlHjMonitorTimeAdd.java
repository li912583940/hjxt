package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_MONITOR_TIME_ADD")
public class JlHjMonitorTimeAdd implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = 2814204021074507066L;
	@Id(inc=true)
	@DbField("WebID")
	private Integer webId;
	@DbField("Call_ID")
	private String callId;
	@DbField("Line_No")
	private Integer lineNo;
	@DbField("JY")
	private String jy;
	@DbField("Add_Time")
	private Integer addTime;
	@DbField("State")
	private Integer state;
	@DbField("Create_Time")
	private Date createTime;
	

	

	public Integer getWebId() {
		return webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public String getJy() {
		return jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

}