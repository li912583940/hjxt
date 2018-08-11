package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_YJ")
public class JlYj implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = 4868236344859903034L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("YJ_No")
	private String yjNo;
	@DbField("YJ_Name")
	private String yjName;
	@DbField("YJ_Card")
	private String yjCard;
	@DbField("JY")
	private String jy;
	@DbField("JQ")
	private String jq;
	@DbField("TeleUser")
	private String teleUser;
	@DbField("TelePwd")
	private String telePwd;
	@DbField("YJ_Num")
	private String yjNum;
	@DbField("Dept_Name")
	private String deptName;

	

	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getYjNo() {
		return this.yjNo;
	}

	public void setYjNo(String yjNo) {
		this.yjNo = yjNo;
	}

	public String getYjName() {
		return this.yjName;
	}

	public void setYjName(String yjName) {
		this.yjName = yjName;
	}

	public String getYjCard() {
		return this.yjCard;
	}

	public void setYjCard(String yjCard) {
		this.yjCard = yjCard;
	}

	public String getJy() {
		return this.jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public String getJq() {
		return this.jq;
	}

	public void setJq(String jq) {
		this.jq = jq;
	}

	public String getTeleUser() {
		return this.teleUser;
	}

	public void setTeleUser(String teleUser) {
		this.teleUser = teleUser;
	}

	public String getTelePwd() {
		return this.telePwd;
	}

	public void setTelePwd(String telePwd) {
		this.telePwd = telePwd;
	}

	public String getYjNum() {
		return this.yjNum;
	}

	public void setYjNum(String yjNum) {
		this.yjNum = yjNum;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}