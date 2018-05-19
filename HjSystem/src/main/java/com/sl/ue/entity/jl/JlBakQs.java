package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_BAK_QS")
public class JlBakQs implements java.io.Serializable {


	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月12日
	 */
	private static final long serialVersionUID = 2207041619579163538L;
	@Id
	@DbField("WebID")
	private Integer webId;
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
	@DbField("CreateTime")
	private Date createTime;
	
	public Integer getWebId() {
		return webId;
	}
	public void setWebId(Integer webId) {
		this.webId = webId;
	}
	public String getFrNo() {
		return frNo;
	}
	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}
	public String getQsSfz() {
		return qsSfz;
	}
	public void setQsSfz(String qsSfz) {
		this.qsSfz = qsSfz;
	}
	public String getQsName() {
		return qsName;
	}
	public void setQsName(String qsName) {
		this.qsName = qsName;
	}
	public String getQsCard() {
		return qsCard;
	}
	public void setQsCard(String qsCard) {
		this.qsCard = qsCard;
	}
	public String getGx() {
		return gx;
	}
	public void setGx(String gx) {
		this.gx = gx;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getDz() {
		return dz;
	}
	public void setDz(String dz) {
		this.dz = dz;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public Integer getSw() {
		return sw;
	}
	public void setSw(Integer sw) {
		this.sw = sw;
	}
	public byte[] getZp() {
		return zp;
	}
	public void setZp(byte[] zp) {
		this.zp = zp;
	}
	public byte[] getJz() {
		return jz;
	}
	public void setJz(byte[] jz) {
		this.jz = jz;
	}
	public Integer getSpState() {
		return spState;
	}
	public void setSpState(Integer spState) {
		this.spState = spState;
	}
	public String getSpUserNo() {
		return spUserNo;
	}
	public void setSpUserNo(String spUserNo) {
		this.spUserNo = spUserNo;
	}
	public Date getSpTime() {
		return spTime;
	}
	public void setSpTime(Date spTime) {
		this.spTime = spTime;
	}
	public String getSpInfo() {
		return spInfo;
	}
	public void setSpInfo(String spInfo) {
		this.spInfo = spInfo;
	}
	public String getSpMon() {
		return spMon;
	}
	public void setSpMon(String spMon) {
		this.spMon = spMon;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
}