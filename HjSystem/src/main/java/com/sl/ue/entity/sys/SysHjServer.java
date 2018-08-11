package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_HJ_SERVER")
public class SysHjServer implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = 295095947898628049L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Server_Name")
	private String serverName;
	@DbField("IP")
	private String ip;
	@DbField("Port")
	private Integer port;
	@DbField("AudioPort")
	private Integer audioPort;
	@DbField("RecUrl")
	private String recUrl;
	@DbField("HjIndexDate")
	private Integer hjIndexDate;
	@DbField("HjIndexCount")
	private Integer hjIndexCount;
	@DbField("ZwIndex")
	private Integer zwIndex;


	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

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

	public Integer getAudioPort() {
		return this.audioPort;
	}

	public void setAudioPort(Integer audioPort) {
		this.audioPort = audioPort;
	}

	public String getRecUrl() {
		return this.recUrl;
	}

	public void setRecUrl(String recUrl) {
		this.recUrl = recUrl;
	}

	public Integer getHjIndexDate() {
		return this.hjIndexDate;
	}

	public void setHjIndexDate(Integer hjIndexDate) {
		this.hjIndexDate = hjIndexDate;
	}

	public Integer getHjIndexCount() {
		return this.hjIndexCount;
	}

	public void setHjIndexCount(Integer hjIndexCount) {
		this.hjIndexCount = hjIndexCount;
	}

	public Integer getZwIndex() {
		return this.zwIndex;
	}

	public void setZwIndex(Integer zwIndex) {
		this.zwIndex = zwIndex;
	}

}