package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_MON")
public class JlHjMon implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = 5756057605314195817L;
	@Id
	@DbField("WebID")
	private Long webId;
	@DbField("Call_ID")
	private String callId;
	@DbField("User_No")
	private String userNo;
	@DbField("User_Name")
	private String userName;
	@DbField("Write_Txt")
	private String writeTxt;
	@DbField("Write_Txt_Lx")
	private String writeTxtLx;
	@DbField("Create_Time")
	private Date createTime;

	
	public Long getWebId() {
		return this.webId;
	}

	public void setWebId(Long webId) {
		this.webId = webId;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getWriteTxt() {
		return this.writeTxt;
	}

	public void setWriteTxt(String writeTxt) {
		this.writeTxt = writeTxt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWriteTxtLx() {
		return writeTxtLx;
	}

	public void setWriteTxtLx(String writeTxtLx) {
		this.writeTxtLx = writeTxtLx;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}