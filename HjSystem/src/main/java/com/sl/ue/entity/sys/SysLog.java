package com.sl.ue.entity.sys;

import java.io.Serializable;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_LOG")
public class SysLog implements Serializable{

	/** */
	private static final long serialVersionUID = 5199550690194069364L;
	@Id
	@DbField("LOG_ID")
	private Long logId;
	@DbField("TYPE")
	private String type;
	@DbField("MODEL")
	private String model;
	@DbField("OP")
	private String op;
	@DbField("INFO")
	private String info;
	@DbField("USER_NO")
	private String userNo;
	@DbField("USER_NAME")
	private String userName;
	@DbField("USER_IP")
	private String userIp;
	@DbField("LOG_TIME")
	private String logTime;
	
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	
	
}
