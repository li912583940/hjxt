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
	private Long webid;
	@DbField("Call_ID")
	private String callId;
	@DbField("HJID")
	private Long hjid;
	/**  操作类型。1:监听，2：停止监听，3：切断，4：插话，5注释  */
	@DbField("type")
	private Integer type;
	@DbField("User_No")
	private String userNo;
	@DbField("User_Name")
	private String userName;
	@DbField("Write_Txt")
	private String writeTxt;
	@DbField("Create_Time")
	private Date createTime;

	


	public Long getWebid() {
		return webid;
	}

	public void setWebid(Long webid) {
		this.webid = webid;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Long getHjid() {
		return hjid;
	}

	public void setHjid(Long hjid) {
		this.hjid = hjid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}