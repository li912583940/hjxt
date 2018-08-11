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
	private String loginUser;
	@DbField("LoginPwd")
	private String loginPwd;
	@DbField("VideoPath")
	private String videoPath;
	@DbField("VideoUrl")
	private String videoUrl;


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

	public String getLoginUser() {
		return this.loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPwd() {
		return this.loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getVideoPath() {
		return this.videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}