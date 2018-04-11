package com.sl.ue.entity;

import java.io.Serializable;

import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_log")
public class SysLog implements Serializable{

	/**  */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long logId;
	private String type;
	private String model;
	private String op;
	private String info;
	private String userNo;
	private String userName;
	private String userIp;
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
