package com.sl.ue.entity.jl;

import java.sql.Timestamp;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * JlFr entity. @author MyEclipse Persistence Tools
 */
@Table("JL_FR")
public class JlFr implements java.io.Serializable {

	// Fields
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
	private Timestamp hjLastTime;
	@DbField("Monitor_Flag")
	private String monitorFlag;
	@DbField("State")
	private Integer state;
	@DbField("OutTime")
	private Timestamp outTime;
	@DbField("SP_State")
	private Integer spState;
	@DbField("SP_UserNo")
	private String spUserNo;
	@DbField("SP_Time")
	private Timestamp spTime;
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
	@DbField("Info_CSRQ")
	private String infoCsrq;
	@DbField("info_home")
	private String infoHome;
	@DbField("ZP")
	private byte[] zp;
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
	@DbField("HJ_STOP_TIME")
	private Timestamp hjStopTime;
	@DbField("HJ_STOP_SM")
	private String hjStopSM;

	// Constructors

	

	/** default constructor */
	public JlFr() {
	}

	/** minimal constructor */
	public JlFr(String frNo, String jy, String jq, String jbNo, Integer qqJb,
			Integer qqUse, Integer qqLeft, Integer hjJb, Integer hjUse,
			Integer hjLeft, String monitorFlag, Integer state, Integer spState,String formerJQName,String frDah) {
		this.frNo = frNo;
		this.jy = jy;
		this.jq = jq;
		this.jbNo = jbNo;
		this.qqJb = qqJb;
		this.qqUse = qqUse;
		this.qqLeft = qqLeft;
		this.hjJb = hjJb;
		this.hjUse = hjUse;
		this.hjLeft = hjLeft;
		this.monitorFlag = monitorFlag;
		this.state = state;
		this.spState = spState;
		this.formerJQName = formerJQName;
		this.frDah=frDah;
	}

	/** full constructor */
	public JlFr(String frNo, String frName, String frCard, String jy,
			String jq, String jbNo, Integer jbSetTime, Integer jbSetType,
			Integer qqJb, Integer qqUse, Integer qqLeft, Integer qqYe,
			String qqZh, String qqMm, Integer hjJb, Integer hjUse,
			Integer hjLeft, Timestamp hjLastTime, String monitorFlag,
			Integer state, Timestamp outTime, Integer spState, String spUserNo,
			Timestamp spTime, String spInfo, String spMon, String infoRjsj,
			String infoJg, String infoZm, String infoXq, String infoDq,
			String infoZdzf, String infoCsrq, String infoHome,byte[] zp,
			String formerJQName,String frDah,Integer stateZdzf,String zdzfType,
			String frGj,Timestamp hjStopTime,String hjStopSM) {
		this.frNo = frNo;
		this.frName = frName;
		this.frCard = frCard;
		this.jy = jy;
		this.jq = jq;
		this.jbNo = jbNo;
		this.jbSetTime = jbSetTime;
		this.jbSetType = jbSetType;
		this.qqJb = qqJb;
		this.qqUse = qqUse;
		this.qqLeft = qqLeft;
		this.qqYe = qqYe;
		this.qqZh = qqZh;
		this.qqMm = qqMm;
		this.hjJb = hjJb;
		this.hjUse = hjUse;
		this.hjLeft = hjLeft;
		this.hjLastTime = hjLastTime;
		this.monitorFlag = monitorFlag;
		this.state = state;
		this.outTime = outTime;
		this.spState = spState;
		this.spUserNo = spUserNo;
		this.spTime = spTime;
		this.spInfo = spInfo;
		this.spMon = spMon;
		this.infoRjsj = infoRjsj;
		this.infoJg = infoJg;
		this.infoZm = infoZm;
		this.infoXq = infoXq;
		this.infoDq = infoDq;
		this.infoZdzf = infoZdzf;
		this.infoCsrq = infoCsrq;
		this.infoHome = infoHome;
		this.zp=zp;
		this.formerJQName=formerJQName;
		this.frDah=frDah;
		this.stateZdzf=stateZdzf;
		this.zdzfType=zdzfType;
		this.frGj=frGj;
		this.hjStopTime=hjStopTime;
		this.hjStopSM=hjStopSM;
	}

	// Property accessors
	
	public Integer getWebId() {
		return this.webId;
	}


	public byte[] getZp() {
		return zp;
	}

	public void setZp(byte[] zp) {
		this.zp = zp;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getFrNo() {
		return this.frNo;
	}

	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}

	public String getFrName() {
		return this.frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getFrCard() {
		return this.frCard;
	}

	public void setFrCard(String frCard) {
		this.frCard = frCard;
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

	public String getJbNo() {
		return this.jbNo;
	}

	public void setJbNo(String jbNo) {
		this.jbNo = jbNo;
	}

	public Integer getJbSetTime() {
		return this.jbSetTime;
	}

	public void setJbSetTime(Integer jbSetTime) {
		this.jbSetTime = jbSetTime;
	}

	public Integer getJbSetType() {
		return this.jbSetType;
	}

	public void setJbSetType(Integer jbSetType) {
		this.jbSetType = jbSetType;
	}

	public Integer getQqJb() {
		return this.qqJb;
	}

	public void setQqJb(Integer qqJb) {
		this.qqJb = qqJb;
	}

	public Integer getQqUse() {
		return this.qqUse;
	}

	public void setQqUse(Integer qqUse) {
		this.qqUse = qqUse;
	}

	public Integer getQqLeft() {
		return this.qqLeft;
	}

	public void setQqLeft(Integer qqLeft) {
		this.qqLeft = qqLeft;
	}

	public Integer getQqYe() {
		return this.qqYe;
	}

	public void setQqYe(Integer qqYe) {
		this.qqYe = qqYe;
	}

	public String getQqZh() {
		return this.qqZh;
	}

	public void setQqZh(String qqZh) {
		this.qqZh = qqZh;
	}

	public String getQqMm() {
		return this.qqMm;
	}

	public void setQqMm(String qqMm) {
		this.qqMm = qqMm;
	}

	public Integer getHjJb() {
		return this.hjJb;
	}

	public void setHjJb(Integer hjJb) {
		this.hjJb = hjJb;
	}

	public Integer getHjUse() {
		return this.hjUse;
	}

	public void setHjUse(Integer hjUse) {
		this.hjUse = hjUse;
	}

	public Integer getHjLeft() {
		return this.hjLeft;
	}

	public void setHjLeft(Integer hjLeft) {
		this.hjLeft = hjLeft;
	}

	public Timestamp getHjLastTime() {
		return this.hjLastTime;
	}

	public void setHjLastTime(Timestamp hjLastTime) {
		this.hjLastTime = hjLastTime;
	}

	public String getMonitorFlag() {
		return this.monitorFlag;
	}

	public void setMonitorFlag(String monitorFlag) {
		this.monitorFlag = monitorFlag;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Timestamp getOutTime() {
		return this.outTime;
	}

	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
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

	public Timestamp getSpTime() {
		return this.spTime;
	}

	public void setSpTime(Timestamp spTime) {
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

	public String getInfoRjsj() {
		return this.infoRjsj;
	}

	public void setInfoRjsj(String infoRjsj) {
		this.infoRjsj = infoRjsj;
	}

	public String getInfoJg() {
		return this.infoJg;
	}

	public void setInfoJg(String infoJg) {
		this.infoJg = infoJg;
	}

	public String getInfoZm() {
		return this.infoZm;
	}

	public void setInfoZm(String infoZm) {
		this.infoZm = infoZm;
	}

	public String getInfoXq() {
		return this.infoXq;
	}

	public void setInfoXq(String infoXq) {
		this.infoXq = infoXq;
	}

	public String getInfoDq() {
		return this.infoDq;
	}

	public void setInfoDq(String infoDq) {
		this.infoDq = infoDq;
	}

	public String getInfoZdzf() {
		return this.infoZdzf;
	}

	public void setInfoZdzf(String infoZdzf) {
		this.infoZdzf = infoZdzf;
	}

	public String getInfoCsrq() {
		return this.infoCsrq;
	}

	public void setInfoCsrq(String infoCsrq) {
		this.infoCsrq = infoCsrq;
	}

	public String getInfoHome() {
		return this.infoHome;
	}

	public void setInfoHome(String infoHome) {
		this.infoHome = infoHome;
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

	public Timestamp getHjStopTime() {
		return hjStopTime;
	}

	public void setHjStopTime(Timestamp hjStopTime) {
		this.hjStopTime = hjStopTime;
	}

	public String getHjStopSM() {
		return hjStopSM;
	}

	public void setHjStopSM(String hjStopSM) {
		this.hjStopSM = hjStopSM;
	}

}