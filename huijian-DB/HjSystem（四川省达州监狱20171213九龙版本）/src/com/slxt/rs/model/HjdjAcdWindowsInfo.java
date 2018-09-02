package com.slxt.rs.model;

import java.sql.Timestamp;

/**
 * HjdjAcdWindowsInfo entity. @author MyEclipse Persistence Tools
 */

public class HjdjAcdWindowsInfo implements java.io.Serializable {

	// Fields

	private Integer acdindex;
	private String acdname;
	private String acdip;
	private Integer acdsetNo;
	private Timestamp acdsetTime;
	private String serverName;

	// Constructors

	/** default constructor */
	public HjdjAcdWindowsInfo() {
	}

	/** minimal constructor */
	public HjdjAcdWindowsInfo(String acdname, String acdip, Integer acdsetNo,
			String serverName) {
		this.acdname = acdname;
		this.acdip = acdip;
		this.acdsetNo = acdsetNo;
		this.serverName = serverName;
	}

	/** full constructor */
	public HjdjAcdWindowsInfo(String acdname, String acdip, Integer acdsetNo,
			Timestamp acdsetTime, String serverName) {
		this.acdname = acdname;
		this.acdip = acdip;
		this.acdsetNo = acdsetNo;
		this.acdsetTime = acdsetTime;
		this.serverName = serverName;
	}

	// Property accessors

	public Integer getAcdindex() {
		return this.acdindex;
	}

	public void setAcdindex(Integer acdindex) {
		this.acdindex = acdindex;
	}

	public String getAcdname() {
		return this.acdname;
	}

	public void setAcdname(String acdname) {
		this.acdname = acdname;
	}

	public String getAcdip() {
		return this.acdip;
	}

	public void setAcdip(String acdip) {
		this.acdip = acdip;
	}

	public Integer getAcdsetNo() {
		return this.acdsetNo;
	}

	public void setAcdsetNo(Integer acdsetNo) {
		this.acdsetNo = acdsetNo;
	}

	public Timestamp getAcdsetTime() {
		return this.acdsetTime;
	}

	public void setAcdsetTime(Timestamp acdsetTime) {
		this.acdsetTime = acdsetTime;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}