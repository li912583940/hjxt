package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [会见审批]
 * L_晓天  @2018年10月8日
 */
@Table("JL_HJ_SP")
public class JlHjSp implements java.io.Serializable{

	/**  */
	private static final long serialVersionUID = 4078793631471616459L;

	@Id
	@DbField("SPID")
	private Long spId;
	
	@DbField("HJID")
	private Long hjId;
	
	@DbField("SP_Reason")
	private String spReason;
	
	@DbField("SP_JB")
	private Integer spJb;
	
	@DbField("SP_Result")
	private Integer spResult;
	
	@DbField("SP_Remark")
	private String spRemark;
	
	@DbField("SP_Tj_Time")
	private Date spTjTime;
	
	@DbField("SP_Tj_User")
	private String spTjUser;
	
	@DbField("SP_Tj_UserName")
	private String spTjUserName;
	
	@DbField("SP_State")
	private Integer spState;
	
	@DbField("SP_Group_No")
	private String spGroupNo;
	
	@DbField("SP_User")
	private String spUser;
	
	@DbField("SP_UserName")
	private String spUserName;

	public Long getSpId() {
		return spId;
	}

	public void setSpId(Long spId) {
		this.spId = spId;
	}

	public Long getHjId() {
		return hjId;
	}

	public void setHjId(Long hjId) {
		this.hjId = hjId;
	}

	public String getSpReason() {
		return spReason;
	}

	public void setSpReason(String spReason) {
		this.spReason = spReason;
	}

	public Integer getSpJb() {
		return spJb;
	}

	public void setSpJb(Integer spJb) {
		this.spJb = spJb;
	}

	public Integer getSpResult() {
		return spResult;
	}

	public void setSpResult(Integer spResult) {
		this.spResult = spResult;
	}

	public String getSpRemark() {
		return spRemark;
	}

	public void setSpRemark(String spRemark) {
		this.spRemark = spRemark;
	}

	public Date getSpTjTime() {
		return spTjTime;
	}

	public void setSpTjTime(Date spTjTime) {
		this.spTjTime = spTjTime;
	}

	public String getSpTjUser() {
		return spTjUser;
	}

	public void setSpTjUser(String spTjUser) {
		this.spTjUser = spTjUser;
	}

	public String getSpTjUserName() {
		return spTjUserName;
	}

	public void setSpTjUserName(String spTjUserName) {
		this.spTjUserName = spTjUserName;
	}

	public Integer getSpState() {
		return spState;
	}

	public void setSpState(Integer spState) {
		this.spState = spState;
	}

	public String getSpGroupNo() {
		return spGroupNo;
	}

	public void setSpGroupNo(String spGroupNo) {
		this.spGroupNo = spGroupNo;
	}

	public String getSpUser() {
		return spUser;
	}

	public void setSpUser(String spUser) {
		this.spUser = spUser;
	}

	public String getSpUserName() {
		return spUserName;
	}

	public void setSpUserName(String spUserName) {
		this.spUserName = spUserName;
	}
	
	
}
