package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_HJ_LINE")
public class SysHjLine implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = 8687800148612877092L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Line_No")
	private Integer lineNo;
	@DbField("JY")
	private String jy;
	@DbField("Line_Type")
	private Integer lineType;
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
	@DbField("BoardMon")
	private String boardMon;
	@DbField("LineMon")
	private Integer lineMon;
	@DbField("DKQ")
	private String dkq;
	@DbField("State")
	private Integer state;
	@DbField("HJState")
	private Integer hjstate;
	@DbField("HJID")
	private Long hjid;
	@DbField("AcdBH")
	private Integer acdBh;
	@DbField("AcdFr")
	private String acdFr;
	@DbField("Monitor_State")
	private String monitorState;
	@DbField("Monitor_JQ")
	private String monitorJq;
	@DbField("Monitor_FR")
	private String monitorFr;
	@DbField("Monitor_QS")
	private String monitorQs;
	@DbField("Monitor_YJ")
	private String monitorYj;
	@DbField("Monitor_Time")
	private String monitorTime;
	@DbField("Monitor_JKBZ")
	private String monitorJkbz;
	@DbField("Monitor_CallID")
	private String monitorCallid;
	@DbField("VideoChan1_Server")
	private String videochan1Server;
	@DbField("VideoChan1_No")
	private Integer videochan1No;
	@DbField("VideoChan2_Server")
	private String videochan2Server;
	@DbField("VideoChan2_No")
	private Integer videochan2No;
	@DbField("model")
	private Integer model; //特殊线路 0：正常，1：特殊，默认0
	
	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public Integer getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public String getJy() {
		return this.jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public Integer getLineType() {
		return this.lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
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

	public String getBoardMon() {
		return this.boardMon;
	}

	public void setBoardMon(String boardMon) {
		this.boardMon = boardMon;
	}

	public Integer getLineMon() {
		return this.lineMon;
	}

	public void setLineMon(Integer lineMon) {
		this.lineMon = lineMon;
	}

	public String getDkq() {
		return this.dkq;
	}

	public void setDkq(String dkq) {
		this.dkq = dkq;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getHjstate() {
		return this.hjstate;
	}

	public void setHjstate(Integer hjstate) {
		this.hjstate = hjstate;
	}

	public Long getHjid() {
		return this.hjid;
	}

	public void setHjid(Long hjid) {
		this.hjid = hjid;
	}

	public Integer getAcdBh() {
		return this.acdBh;
	}

	public void setAcdBh(Integer acdBh) {
		this.acdBh = acdBh;
	}

	public String getAcdFr() {
		return this.acdFr;
	}

	public void setAcdFr(String acdFr) {
		this.acdFr = acdFr;
	}

	public String getMonitorState() {
		return this.monitorState;
	}

	public void setMonitorState(String monitorState) {
		this.monitorState = monitorState;
	}

	public String getMonitorJq() {
		return this.monitorJq;
	}

	public void setMonitorJq(String monitorJq) {
		this.monitorJq = monitorJq;
	}

	public String getMonitorFr() {
		return this.monitorFr;
	}

	public void setMonitorFr(String monitorFr) {
		this.monitorFr = monitorFr;
	}

	public String getMonitorQs() {
		return this.monitorQs;
	}

	public void setMonitorQs(String monitorQs) {
		this.monitorQs = monitorQs;
	}

	public String getMonitorYj() {
		return this.monitorYj;
	}

	public void setMonitorYj(String monitorYj) {
		this.monitorYj = monitorYj;
	}

	public String getMonitorTime() {
		return this.monitorTime;
	}

	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}

	public String getMonitorJkbz() {
		return this.monitorJkbz;
	}

	public void setMonitorJkbz(String monitorJkbz) {
		this.monitorJkbz = monitorJkbz;
	}

	public String getMonitorCallid() {
		return monitorCallid;
	}

	public void setMonitorCallid(String monitorCallid) {
		this.monitorCallid = monitorCallid;
	}

	public String getVideochan1Server() {
		return videochan1Server;
	}

	public void setVideochan1Server(String videochan1Server) {
		this.videochan1Server = videochan1Server;
	}

	public Integer getVideochan1No() {
		return videochan1No;
	}

	public void setVideochan1No(Integer videochan1No) {
		this.videochan1No = videochan1No;
	}

	public String getVideochan2Server() {
		return videochan2Server;
	}

	public void setVideochan2Server(String videochan2Server) {
		this.videochan2Server = videochan2Server;
	}

	public Integer getVideochan2No() {
		return videochan2No;
	}

	public void setVideochan2No(Integer videochan2No) {
		this.videochan2No = videochan2No;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	
}