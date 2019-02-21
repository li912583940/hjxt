package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_DJ")
public class JlHjDj implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = 4904000068528047567L;
	// Fields
	@Id(inc=true)
	@DbField("HJID")
	private Long hjid;
	@DbField("JY")
	private String jy;
	@DbField("JQ_No")
	private String jqNo;
	@DbField("JQ_Name")
	private String jqName;
	@DbField("FR_No")
	private String frNo;
	@DbField("FR_Name")
	private String frName;
	@DbField("QS_Info1")
	private String qsInfo1;
	@DbField("QS_Info2")
	private String qsInfo2;
	@DbField("QS_Info3")
	private String qsInfo3;
	@DbField("QS_Info4")
	private String qsInfo4;
	@DbField("QS_Info5")
	private String qsInfo5;
	@DbField("QS_Info6")
	private String qsInfo6;
	@DbField("QS_Info7")
	private String qsInfo7;
	@DbField("QS_Info8")
	private String qsInfo8;
	@DbField("QS_Info9")
	private String qsInfo9;
	@DbField("HJ_Time")
	private Integer hjTime;
	@DbField("HJ_Info")
	private String hjInfo;
	@DbField("HJ_Type")
	private Integer hjType;
	@DbField("Monitor_Flag")
	private String monitorFlag;
	@DbField("DJ_Type")
	private Integer djType;
	@DbField("DJ_User")
	private String djUser;
	@DbField("DJ_Time")
	private Date djTime;
	@DbField("YJ_No")
	private String yjNo;
	@DbField("YJ_Name")
	private String yjName;
	@DbField("FR_In_User")
	private String frInUser;
	@DbField("FR_In_Time")
	private Date frInTime;
	@DbField("FR_Out_User")
	private String frOutUser;
	@DbField("FR_Out_Time")
	private Date frOutTime;
	@DbField("QS_In_User")
	private String qsInUser;
	@DbField("QS_In_Time")
	private Date qsInTime;
	@DbField("QS_Out_User")
	private String qsOutUser;
	@DbField("QS_Out_Time")
	private Date qsOutTime;
	@DbField("HJ_Order")
	private Integer hjOrder;
	@DbField("FP_Flag")
	private Integer fpFlag;
	@DbField("FP_Line_No")
	private Integer fpLineNo;
	@DbField("FP_Time")
	private Date fpTime;
	@DbField("FP_TZFR_Flag")
	private Integer fpTzfrFlag;
	@DbField("FP_TZQS_Flag")
	private Integer fpTzqsFlag;
	@DbField("HJ_Index")
	private Long hjIndex;
	@DbField("TP_Info")
	private String tpInfo;
	@DbField("SP_Info")
	private String spInfo;
	@DbField("SP_Time")
	private Date spTime;
	@DbField("SP_User")
	private String spUser;
	@DbField("state")
	private Integer state; // 登记状态，0 未完成会见，1已完成会见，2 已取消会见，3 待审批  默认值：0
	@DbField("Cancel_Info")
	private String cancelInfo;
	@DbField("Call_ID")
	private String callId;
	@DbField("Import_Flag")
	private Integer importFlag;
	@DbField("Import_Time")
	private Date importTime;
	@DbField("Info_Wp")
	private Integer infoWp;
	@DbField("QS_Card1")
	private String qsCard1;
	@DbField("QS_Card2")
	private String qsCard2;
	@DbField("QS_Card3")
	private String qsCard3;
	@DbField("QS_Card4")
	private String qsCard4;
	@DbField("QS_Card5")
	private String qsCard5;
	@DbField("QS_Card6")
	private String qsCard6;
	@DbField("QS_Card7")
	private String qsCard7;
	@DbField("QS_Card8")
	private String qsCard8;
	@DbField("QS_Card9")
	private String qsCard9;
	@DbField("WP_DjTime")
	private Date wpDjTime;
	@DbField("WP_DJR")
	private String wpDjr;
	@DbField("Qs_Last_Time")
	private Date qsLastTime;
	@DbField("Qs_Last_Count")
	private Integer qsLastCount;
	@DbField("page_tz_state")
	private Integer pageTzState;
	/* 会见通知方式。 0：立即通知，1：延后通知   */
	@DbField("page_tz_mode")
	private Integer pageTzMode;
	@DbField("Fr_Last_Count")
	private Integer frLastCount;
	@DbField("Fr_Last_Time")
	private Date frLastTime;
	@DbField("TP_QS_Num")
	private Integer tpQsNum;
	@DbField("QZ_SP")
	private Integer qzSp;
	@DbField("FR_DAH")
	private String frDah;
	@DbField("page_tz_User_no")
	private String pageTzUserNo;
	@DbField("page_tz_User_name")
	private String pageTzUserName;
	@DbField("page_tz_Time")
	private Date pageTzTime;
	@DbField("is_over_time")
	private Integer isOverTime; //是否超时，1：已超时，0：未超时
	@DbField("FP_Time_FR")
	private Date fpTimeFr;
	@DbField("FP_Time_QS")
	private Date fpTimeQs;
	@DbField("SH_State")
	private Integer shState; //授权状态 
	@DbField("hj_mode")
	private Integer hjMode; //会见方式。1：隔离会见，2：非隔离会见，3：远程视频会见，9：其他方式. 默认1
	// Constructors

	

	/** default constructor */
	public String getQsCard1() {
		return qsCard1;
	}

	public void setQsCard1(String qsCard1) {
		this.qsCard1 = qsCard1;
	}

	public String getQsCard2() {
		return qsCard2;
	}

	public void setQsCard2(String qsCard2) {
		this.qsCard2 = qsCard2;
	}

	public String getQsCard3() {
		return qsCard3;
	}

	public void setQsCard3(String qsCard3) {
		this.qsCard3 = qsCard3;
	}

	public String getQsCard4() {
		return qsCard4;
	}

	public void setQsCard4(String qsCard4) {
		this.qsCard4 = qsCard4;
	}

	public String getQsCard5() {
		return qsCard5;
	}

	public void setQsCard5(String qsCard5) {
		this.qsCard5 = qsCard5;
	}

	public String getQsCard6() {
		return qsCard6;
	}

	public void setQsCard6(String qsCard6) {
		this.qsCard6 = qsCard6;
	}

	public String getQsCard7() {
		return qsCard7;
	}

	public void setQsCard7(String qsCard7) {
		this.qsCard7 = qsCard7;
	}

	public String getQsCard8() {
		return qsCard8;
	}

	public void setQsCard8(String qsCard8) {
		this.qsCard8 = qsCard8;
	}

	public String getQsCard9() {
		return qsCard9;
	}

	public void setQsCard9(String qsCard9) {
		this.qsCard9 = qsCard9;
	}

	public Long getHjid() {
		return this.hjid;
	}

	public void setHjid(Long hjid) {
		this.hjid = hjid;
	}

	public String getJy() {
		return this.jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public String getJqNo() {
		return this.jqNo;
	}

	public void setJqNo(String jqNo) {
		this.jqNo = jqNo;
	}

	public String getJqName() {
		return this.jqName;
	}

	public void setJqName(String jqName) {
		this.jqName = jqName;
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

	public String getQsInfo1() {
		return this.qsInfo1;
	}

	public void setQsInfo1(String qsInfo1) {
		this.qsInfo1 = qsInfo1;
	}

	public String getQsInfo2() {
		return this.qsInfo2;
	}

	public void setQsInfo2(String qsInfo2) {
		this.qsInfo2 = qsInfo2;
	}

	public String getQsInfo3() {
		return this.qsInfo3;
	}

	public void setQsInfo3(String qsInfo3) {
		this.qsInfo3 = qsInfo3;
	}

	public String getQsInfo4() {
		return this.qsInfo4;
	}

	public void setQsInfo4(String qsInfo4) {
		this.qsInfo4 = qsInfo4;
	}

	public String getQsInfo5() {
		return this.qsInfo5;
	}

	public void setQsInfo5(String qsInfo5) {
		this.qsInfo5 = qsInfo5;
	}

	public String getQsInfo6() {
		return this.qsInfo6;
	}

	public void setQsInfo6(String qsInfo6) {
		this.qsInfo6 = qsInfo6;
	}

	public String getQsInfo7() {
		return this.qsInfo7;
	}

	public void setQsInfo7(String qsInfo7) {
		this.qsInfo7 = qsInfo7;
	}

	public String getQsInfo8() {
		return this.qsInfo8;
	}

	public void setQsInfo8(String qsInfo8) {
		this.qsInfo8 = qsInfo8;
	}

	public String getQsInfo9() {
		return this.qsInfo9;
	}

	public void setQsInfo9(String qsInfo9) {
		this.qsInfo9 = qsInfo9;
	}

	public Integer getHjTime() {
		return this.hjTime;
	}

	public void setHjTime(Integer hjTime) {
		this.hjTime = hjTime;
	}

	public String getHjInfo() {
		return this.hjInfo;
	}

	public void setHjInfo(String hjInfo) {
		this.hjInfo = hjInfo;
	}

	public Integer getHjType() {
		return this.hjType;
	}

	public void setHjType(Integer hjType) {
		this.hjType = hjType;
	}

	public String getMonitorFlag() {
		return this.monitorFlag;
	}

	public void setMonitorFlag(String monitorFlag) {
		this.monitorFlag = monitorFlag;
	}

	public Integer getDjType() {
		return this.djType;
	}

	public void setDjType(Integer djType) {
		this.djType = djType;
	}

	public String getDjUser() {
		return this.djUser;
	}

	public void setDjUser(String djUser) {
		this.djUser = djUser;
	}

	public Date getDjTime() {
		return this.djTime;
	}

	public void setDjTime(Date djTime) {
		this.djTime = djTime;
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

	public String getFrInUser() {
		return this.frInUser;
	}

	public void setFrInUser(String frInUser) {
		this.frInUser = frInUser;
	}

	public Date getFrInTime() {
		return this.frInTime;
	}

	public void setFrInTime(Date frInTime) {
		this.frInTime = frInTime;
	}

	public String getFrOutUser() {
		return this.frOutUser;
	}

	public void setFrOutUser(String frOutUser) {
		this.frOutUser = frOutUser;
	}

	public Date getFrOutTime() {
		return this.frOutTime;
	}

	public void setFrOutTime(Date frOutTime) {
		this.frOutTime = frOutTime;
	}

	public String getQsInUser() {
		return this.qsInUser;
	}

	public void setQsInUser(String qsInUser) {
		this.qsInUser = qsInUser;
	}

	public Date getQsInTime() {
		return this.qsInTime;
	}

	public void setQsInTime(Date qsInTime) {
		this.qsInTime = qsInTime;
	}

	public String getQsOutUser() {
		return this.qsOutUser;
	}

	public void setQsOutUser(String qsOutUser) {
		this.qsOutUser = qsOutUser;
	}

	public Date getQsOutTime() {
		return this.qsOutTime;
	}

	public void setQsOutTime(Date qsOutTime) {
		this.qsOutTime = qsOutTime;
	}

	public Integer getHjOrder() {
		return this.hjOrder;
	}

	public void setHjOrder(Integer hjOrder) {
		this.hjOrder = hjOrder;
	}

	public Integer getFpFlag() {
		return this.fpFlag;
	}

	public void setFpFlag(Integer fpFlag) {
		this.fpFlag = fpFlag;
	}

	public Integer getFpLineNo() {
		return this.fpLineNo;
	}

	public void setFpLineNo(Integer fpLineNo) {
		this.fpLineNo = fpLineNo;
	}

	public Date getFpTime() {
		return this.fpTime;
	}

	public void setFpTime(Date fpTime) {
		this.fpTime = fpTime;
	}

	public Integer getFpTzfrFlag() {
		return this.fpTzfrFlag;
	}

	public void setFpTzfrFlag(Integer fpTzfrFlag) {
		this.fpTzfrFlag = fpTzfrFlag;
	}

	public Integer getFpTzqsFlag() {
		return this.fpTzqsFlag;
	}

	public void setFpTzqsFlag(Integer fpTzqsFlag) {
		this.fpTzqsFlag = fpTzqsFlag;
	}

	public Long getHjIndex() {
		return this.hjIndex;
	}

	public void setHjIndex(Long hjIndex) {
		this.hjIndex = hjIndex;
	}

	public String getTpInfo() {
		return this.tpInfo;
	}

	public void setTpInfo(String tpInfo) {
		this.tpInfo = tpInfo;
	}

	public String getSpInfo() {
		return this.spInfo;
	}

	public void setSpInfo(String spInfo) {
		this.spInfo = spInfo;
	}

	public Date getSpTime() {
		return this.spTime;
	}

	public void setSpTime(Date spTime) {
		this.spTime = spTime;
	}

	public String getSpUser() {
		return this.spUser;
	}

	public void setSpUser(String spUser) {
		this.spUser = spUser;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCancelInfo() {
		return this.cancelInfo;
	}

	public void setCancelInfo(String cancelInfo) {
		this.cancelInfo = cancelInfo;
	}

	public String getCallId() {
		return this.callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Integer getImportFlag() {
		return this.importFlag;
	}

	public void setImportFlag(Integer importFlag) {
		this.importFlag = importFlag;
	}

	public Date getImportTime() {
		return this.importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public Integer getInfoWp() {
		return infoWp;
	}

	public void setInfoWp(Integer infoWp) {
		this.infoWp = infoWp;
	}

	public Date getWpDjTime() {
		return wpDjTime;
	}

	public void setWpDjTime(Date wpDjTime) {
		this.wpDjTime = wpDjTime;
	}

	public String getWpDjr() {
		return wpDjr;
	}

	public void setWpDjr(String wpDjr) {
		this.wpDjr = wpDjr;
	}

	public Integer getPageTzState() {
		return pageTzState;
	}

	public void setPageTzState(Integer pageTzState) {
		this.pageTzState = pageTzState;
	}

	public Integer getPageTzMode() {
		return pageTzMode;
	}

	public void setPageTzMode(Integer pageTzMode) {
		this.pageTzMode = pageTzMode;
	}

	public Integer getTpQsNum() {
		return tpQsNum;
	}

	public void setTpQsNum(Integer tpQsNum) {
		this.tpQsNum = tpQsNum;
	}

	public Integer getQzSp() {
		return qzSp;
	}

	public void setQzSp(Integer qzSp) {
		this.qzSp = qzSp;
	}
	public String getFrDah() {
		return frDah;
	}

	public void setFrDah(String frDah) {
		this.frDah = frDah;
	}

	public String getPageTzUserNo() {
		return pageTzUserNo;
	}

	public void setPageTzUserNo(String pageTzUserNo) {
		this.pageTzUserNo = pageTzUserNo;
	}

	public String getPageTzUserName() {
		return pageTzUserName;
	}

	public void setPageTzUserName(String pageTzUserName) {
		this.pageTzUserName = pageTzUserName;
	}

	public Date getPageTzTime() {
		return pageTzTime;
	}

	public void setPageTzTime(Date pageTzTime) {
		this.pageTzTime = pageTzTime;
	}

	public Integer getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(Integer isOverTime) {
		this.isOverTime = isOverTime;
	}

	public Date getFpTimeFr() {
		return fpTimeFr;
	}

	public void setFpTimeFr(Date fpTimeFr) {
		this.fpTimeFr = fpTimeFr;
	}

	public Date getFpTimeQs() {
		return fpTimeQs;
	}

	public void setFpTimeQs(Date fpTimeQs) {
		this.fpTimeQs = fpTimeQs;
	}

	public Date getQsLastTime() {
		return qsLastTime;
	}

	public void setQsLastTime(Date qsLastTime) {
		this.qsLastTime = qsLastTime;
	}

	public Integer getQsLastCount() {
		return qsLastCount;
	}

	public void setQsLastCount(Integer qsLastCount) {
		this.qsLastCount = qsLastCount;
	}

	public Integer getFrLastCount() {
		return frLastCount;
	}

	public void setFrLastCount(Integer frLastCount) {
		this.frLastCount = frLastCount;
	}

	public Date getFrLastTime() {
		return frLastTime;
	}

	public void setFrLastTime(Date frLastTime) {
		this.frLastTime = frLastTime;
	}

	public Integer getShState() {
		return shState;
	}

	public void setShState(Integer shState) {
		this.shState = shState;
	}

	public Integer getHjMode() {
		return hjMode;
	}

	public void setHjMode(Integer hjMode) {
		this.hjMode = hjMode;
	}

}