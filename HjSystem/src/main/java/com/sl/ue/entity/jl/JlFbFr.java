package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;


public class JlFbFr implements java.io.Serializable {

	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月13日
	 */
	private static final long serialVersionUID = 2555338144633680196L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("FR_No")
	private String frNo;
	@DbField("FR_Name")
	private String frName;
	@DbField("FR_Card")
	private String frCard;
	@DbField("JY")
	private String jy;
	@DbField("JQ")
	private String jq;
	@DbField("JB_No")
	private String jbNo;
	@DbField("JB_SetTime")
	private Integer jbSetTime;
	@DbField("JB_SetType")
	private Integer jbSetType;
	@DbField("QQ_JB")
	private Integer qqJb;
	@DbField("QQ_Use")
	private Integer qqUse;
	@DbField("QQ_Left")
	private Integer qqLeft;
	@DbField("QQ_YE")
	private Integer qqYe;
	@DbField("QQ_ZH")
	private String qqZh;
	@DbField("QQ_MM")
	private String qqMm;
	@DbField("HJ_JB")
	private Integer hjJb;
	@DbField("HJ_Use")
	private Integer hjUse;
	@DbField("HJ_Left")
	private Integer hjLeft;
	@DbField("HJ_Last_Time")
	private Date hjLastTime;
	@DbField("Monitor_Flag")
	private String monitorFlag;
	@DbField("State")
	private Integer state;
	@DbField("OutTime")
	private Date outTime;
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
	@DbField("Info_RJSJ")
	private String infoRjsj;
	@DbField("Info_JG")
	private String infoJg;
	@DbField("Info_ZM")
	private String infoZm;
	@DbField("Info_XQ")
	private String infoXq;
	@DbField("Info_DQ")
	private String infoDq;
	@DbField("Info_ZDZF")
	private String infoZdzf;
	@DbField("info_home")
	private String infoHome;
	@DbField("ZP")
	private Byte[] zp;
	@DbField("Former_JQ_Name")
	private String formerJQName;
	@DbField("FR_DAH")
	private String frDah;
	@DbField("State_ZDZF")
	private Integer stateZdzf;
	@DbField("ZDZF_Type")
	private String zdzfType;
	@DbField("FR_GJ")
	private String frGj;
	@DbField("Info_CSRQ")
	private String infoCsrq;
	
	
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
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	public String getFrCard() {
		return frCard;
	}
	public void setFrCard(String frCard) {
		this.frCard = frCard;
	}
	public String getJy() {
		return jy;
	}
	public void setJy(String jy) {
		this.jy = jy;
	}
	public String getJq() {
		return jq;
	}
	public void setJq(String jq) {
		this.jq = jq;
	}
	public String getJbNo() {
		return jbNo;
	}
	public void setJbNo(String jbNo) {
		this.jbNo = jbNo;
	}
	public Integer getJbSetTime() {
		return jbSetTime;
	}
	public void setJbSetTime(Integer jbSetTime) {
		this.jbSetTime = jbSetTime;
	}
	public Integer getJbSetType() {
		return jbSetType;
	}
	public void setJbSetType(Integer jbSetType) {
		this.jbSetType = jbSetType;
	}
	public Integer getQqJb() {
		return qqJb;
	}
	public void setQqJb(Integer qqJb) {
		this.qqJb = qqJb;
	}
	public Integer getQqUse() {
		return qqUse;
	}
	public void setQqUse(Integer qqUse) {
		this.qqUse = qqUse;
	}
	public Integer getQqLeft() {
		return qqLeft;
	}
	public void setQqLeft(Integer qqLeft) {
		this.qqLeft = qqLeft;
	}
	public Integer getQqYe() {
		return qqYe;
	}
	public void setQqYe(Integer qqYe) {
		this.qqYe = qqYe;
	}
	public String getQqZh() {
		return qqZh;
	}
	public void setQqZh(String qqZh) {
		this.qqZh = qqZh;
	}
	public String getQqMm() {
		return qqMm;
	}
	public void setQqMm(String qqMm) {
		this.qqMm = qqMm;
	}
	public Integer getHjJb() {
		return hjJb;
	}
	public void setHjJb(Integer hjJb) {
		this.hjJb = hjJb;
	}
	public Integer getHjUse() {
		return hjUse;
	}
	public void setHjUse(Integer hjUse) {
		this.hjUse = hjUse;
	}
	public Integer getHjLeft() {
		return hjLeft;
	}
	public void setHjLeft(Integer hjLeft) {
		this.hjLeft = hjLeft;
	}
	public Date getHjLastTime() {
		return hjLastTime;
	}
	public void setHjLastTime(Date hjLastTime) {
		this.hjLastTime = hjLastTime;
	}
	public String getMonitorFlag() {
		return monitorFlag;
	}
	public void setMonitorFlag(String monitorFlag) {
		this.monitorFlag = monitorFlag;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
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
	public String getInfoRjsj() {
		return infoRjsj;
	}
	public void setInfoRjsj(String infoRjsj) {
		this.infoRjsj = infoRjsj;
	}
	public String getInfoJg() {
		return infoJg;
	}
	public void setInfoJg(String infoJg) {
		this.infoJg = infoJg;
	}
	public String getInfoZm() {
		return infoZm;
	}
	public void setInfoZm(String infoZm) {
		this.infoZm = infoZm;
	}
	public String getInfoXq() {
		return infoXq;
	}
	public void setInfoXq(String infoXq) {
		this.infoXq = infoXq;
	}
	public String getInfoDq() {
		return infoDq;
	}
	public void setInfoDq(String infoDq) {
		this.infoDq = infoDq;
	}
	public String getInfoZdzf() {
		return infoZdzf;
	}
	public void setInfoZdzf(String infoZdzf) {
		this.infoZdzf = infoZdzf;
	}
	public String getInfoHome() {
		return infoHome;
	}
	public void setInfoHome(String infoHome) {
		this.infoHome = infoHome;
	}
	public Byte[] getZp() {
		return zp;
	}
	public void setZp(Byte[] zp) {
		this.zp = zp;
	}
	public String getFormerJQName() {
		return formerJQName;
	}
	public void setFormerJQName(String formerJQName) {
		this.formerJQName = formerJQName;
	}
	public String getFrDah() {
		return frDah;
	}
	public void setFrDah(String frDah) {
		this.frDah = frDah;
	}
	public Integer getStateZdzf() {
		return stateZdzf;
	}
	public void setStateZdzf(Integer stateZdzf) {
		this.stateZdzf = stateZdzf;
	}
	public String getZdzfType() {
		return zdzfType;
	}
	public void setZdzfType(String zdzfType) {
		this.zdzfType = zdzfType;
	}
	public String getFrGj() {
		return frGj;
	}
	public void setFrGj(String frGj) {
		this.frGj = frGj;
	}
	public String getInfoCsrq() {
		return infoCsrq;
	}
	public void setInfoCsrq(String infoCsrq) {
		this.infoCsrq = infoCsrq;
	}

	
}