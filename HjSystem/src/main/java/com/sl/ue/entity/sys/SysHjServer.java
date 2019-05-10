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
	private Integer webid;
	@DbField("Server_Name")
	private String serverName;
	@DbField("IP")
	private String ip;
	@DbField("Port")
	private Integer port;
	@DbField("AudioPort")
	private Integer audioport;
	@DbField("RecUrl")
	private String recurl;
	@DbField("HjIndexDate")
	private Integer hjindexdate;
	@DbField("HjIndexCount")
	private Integer hjindexcount;
	@DbField("ZwIndex")
	private Integer zwindex;




	public Integer getWebid() {
		return webid;
	}

	public void setWebid(Integer webid) {
		this.webid = webid;
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


	public String getRecurl() {
		return this.recurl;
	}

	public void setRecurl(String recurl) {
		this.recurl = recurl;
	}

	public Integer getAudioport() {
		return audioport;
	}

	public void setAudioport(Integer audioport) {
		this.audioport = audioport;
	}

	public Integer getHjindexdate() {
		return hjindexdate;
	}

	public void setHjindexdate(Integer hjindexdate) {
		this.hjindexdate = hjindexdate;
	}

	public Integer getHjindexcount() {
		return hjindexcount;
	}

	public void setHjindexcount(Integer hjindexcount) {
		this.hjindexcount = hjindexcount;
	}

	public Integer getZwindex() {
		return zwindex;
	}

	public void setZwindex(Integer zwindex) {
		this.zwindex = zwindex;
	}

	

}