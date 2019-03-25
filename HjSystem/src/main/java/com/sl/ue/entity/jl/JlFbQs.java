package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_FB_QS")
public class JlFbQs implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -245587633565417112L;
	@Id
	@DbField("WebID")
	private Integer webid;
	@DbField("FR_No")
	private String frNo;
	@DbField("QS_SFZ")
	private String qsSfz;
	@DbField("QS_Name")
	private String qsName;
	@DbField("QS_Card")
	private String qsCard;
	@DbField("GX")
	private String gx;
	@DbField("XB")
	private String xb;
	@DbField("DZ")
	private String dz;
	@DbField("TELE")
	private String tele;
	@DbField("SW")
	private Integer sw;
	@DbField("ZP")
	private byte[] zp;
	@DbField("JZ")
	private byte[] jz;
	@DbField("SP_State")
	private Integer spState;
	@DbField("SP_UserNo")
	private String spUserNo;
	@DbField("SP_Time")
	private Date spTime;
	@DbField("SP_Info")
	private String spInfo;
	@DbField("SP_Mon")
	private String spMon;
	@DbField("QS_ZJLB")
	private Integer qsZjlb;



	

	public Integer getWebid() {
		return webid;
	}

	public void setWebid(Integer webid) {
		this.webid = webid;
	}

	public String getFrNo() {
		return this.frNo;
	}

	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}

	public String getQsSfz() {
		return this.qsSfz;
	}

	public void setQsSfz(String qsSfz) {
		this.qsSfz = qsSfz;
	}

	public String getQsName() {
		return this.qsName;
	}

	public void setQsName(String qsName) {
		this.qsName = qsName;
	}

	public String getQsCard() {
		return this.qsCard;
	}

	public void setQsCard(String qsCard) {
		this.qsCard = qsCard;
	}

	public String getGx() {
		return this.gx;
	}

	public void setGx(String gx) {
		this.gx = gx;
	}

	public String getXb() {
		return this.xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getDz() {
		return this.dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getTele() {
		return this.tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public Integer getSw() {
		return this.sw;
	}

	public void setSw(Integer sw) {
		this.sw = sw;
	}

	public byte[] getZp() {
		return this.zp;
	}

	public void setZp(byte[] zp) {
		this.zp = zp;
	}

	public byte[] getJz() {
		return this.jz;
	}

	public void setJz(byte[] jz) {
		this.jz = jz;
	}

	public Integer getSpState() {
		return this.spState;
	}

	public void setSpState(Integer spState) {
		this.spState = spState;
	}

	public String getSpUserNo() {
		return this.spUserNo;
	}

	public void setSpUserNo(String spUserNo) {
		this.spUserNo = spUserNo;
	}

	public Date getSpTime() {
		return this.spTime;
	}

	public void setSpTime(Date spTime) {
		this.spTime = spTime;
	}

	public String getSpInfo() {
		return this.spInfo;
	}

	public void setSpInfo(String spInfo) {
		this.spInfo = spInfo;
	}

	public String getSpMon() {
		return this.spMon;
	}

	public void setSpMon(String spMon) {
		this.spMon = spMon;
	}

	public Integer getQsZjlb() {
		return qsZjlb;
	}

	public void setQsZjlb(Integer qsZjlb) {
		this.qsZjlb = qsZjlb;
	}

}