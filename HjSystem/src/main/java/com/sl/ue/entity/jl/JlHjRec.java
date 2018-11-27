package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_REC")
public class JlHjRec implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -5508517096804292358L;
	@Id
	@DbField("WebID")
	private Long webId;
	@DbField("Call_ID")
	private String callId;
	@DbField("Line_No")
	private Integer lineNo;
	@DbField("ZW")
	private String zw;
	@DbField("Board")
	private String board;
	@DbField("Line")
	private Integer line;
	@DbField("BoardJs")
	private String boardJs;
	@DbField("LineJs")
	private Integer lineJs;
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
	@DbField("YJ_No")
	private String yjNo;
	@DbField("YJ_Name")
	private String yjName;
	@DbField("Monitor_Flag")
	private String monitorFlag;
	@DbField("HJ_Info")
	private String hjInfo;
	@DbField("HJ_Type")
	private Integer hjType;
	@DbField("DJ_Type")
	private Integer djType;
	@DbField("Call_Time_Start")
	private String callTimeStart;
	@DbField("Call_Time_End")
	private String callTimeEnd;
	@DbField("Call_Time_Len")
	private Integer callTimeLen;
	@DbField("Call_RecFile")
	private String callRecfile;
	@DbField("Call_VideoFile1")
	private String callVideofile1;
	@DbField("Call_VideoFile2")
	private String callVideofile2;
	@DbField("Down_Video_Count")
	private Integer downVideoCount;
	@DbField("Delete_Flag")
	private Integer deleteFlag;
	@DbField("DJ_User")
	private String djUser;
	@DbField("DJ_Time")
	private Date djTime;
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
	@DbField("TP_Info")
	private String tpInfo;
	@DbField("SP_Info")
	private String spInfo;
	@DbField("SP_Time")
	private Date spTime;
	@DbField("SP_User")
	private String spUser;
	@DbField("HJID")
	private Long hjid;
	@DbField("Import_Flag")
	private Integer importFlag;
	@DbField("Rec_Rating_State")
	private Integer recRatingState;
	@DbField("Rec_Assessment_State")
	private Integer recAssessmentState;
	
	
	public Long getWebId() {
		return this.webId;
	}

	public Integer getImportFlag() {
		return importFlag;
	}

	public void setImportFlag(Integer importFlag) {
		this.importFlag = importFlag;
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

	public Integer getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public String getZw() {
		return this.zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public String getBoard() {
		return this.board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public Integer getLine() {
		return this.line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public String getBoardJs() {
		return this.boardJs;
	}

	public void setBoardJs(String boardJs) {
		this.boardJs = boardJs;
	}

	public Integer getLineJs() {
		return this.lineJs;
	}

	public void setLineJs(Integer lineJs) {
		this.lineJs = lineJs;
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

	public String getMonitorFlag() {
		return this.monitorFlag;
	}

	public void setMonitorFlag(String monitorFlag) {
		this.monitorFlag = monitorFlag;
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

	public Integer getDjType() {
		return this.djType;
	}

	public void setDjType(Integer djType) {
		this.djType = djType;
	}

	public String getCallTimeStart() {
		return this.callTimeStart;
	}

	public void setCallTimeStart(String callTimeStart) {
		this.callTimeStart = callTimeStart;
	}

	public String getCallTimeEnd() {
		return this.callTimeEnd;
	}

	public void setCallTimeEnd(String callTimeEnd) {
		this.callTimeEnd = callTimeEnd;
	}

	public Integer getCallTimeLen() {
		return this.callTimeLen;
	}

	public void setCallTimeLen(Integer callTimeLen) {
		this.callTimeLen = callTimeLen;
	}
	public String getCallRecfile() {
		return callRecfile;
	}

	public void setCallRecfile(String callRecfile) {
		this.callRecfile = callRecfile;
	}

	public String getCallVideofile1() {
		return callVideofile1;
	}

	public void setCallVideofile1(String callVideofile1) {
		this.callVideofile1 = callVideofile1;
	}

	public String getCallVideofile2() {
		return callVideofile2;
	}

	public void setCallVideofile2(String callVideofile2) {
		this.callVideofile2 = callVideofile2;
	}

	public Integer getDownVideoCount() {
		return this.downVideoCount;
	}

	public void setDownVideoCount(Integer downVideoCount) {
		this.downVideoCount = downVideoCount;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Long getHjid() {
		return this.hjid;
	}

	public void setHjid(Long hjid) {
		this.hjid = hjid;
	}

	public Integer getRecRatingState() {
		return recRatingState;
	}

	public void setRecRatingState(Integer recRatingState) {
		this.recRatingState = recRatingState;
	}

	public Integer getRecAssessmentState() {
		return recAssessmentState;
	}

	public void setRecAssessmentState(Integer recAssessmentState) {
		this.recAssessmentState = recAssessmentState;
	}

}