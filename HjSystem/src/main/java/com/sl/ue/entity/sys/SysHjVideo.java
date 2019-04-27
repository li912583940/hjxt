package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_HJ_VIDEO")
public class SysHjVideo implements java.io.Serializable {

	// Fields

	/** */
	private static final long serialVersionUID = -3523168568384178895L;
	@Id
	@DbField("Server_Name")
	private String serverName;
	@DbField("IP")
	private String ip;
	@DbField("Port")
	private Integer port;
	@DbField("LoginUser")
	private String loginuser;
	@DbField("LoginPwd")
	private String loginpwd;
	@DbField("VideoPath")
	private String videopath;
	@DbField("VideoUrl")
	private String videourl;


	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(String loginuser) {
		this.loginuser = loginuser;
	}

	public String getLoginpwd() {
		return loginpwd;
	}

	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}

	public String getVideopath() {
		return videopath;
	}

	public void setVideopath(String videopath) {
		this.videopath = videopath;
	}

	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

	

}