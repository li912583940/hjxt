package com.sl.ue.entity.hj;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("HJDJ_ACD_WINDOWS_INFO")
public class HjdjAcdWindowsInfo implements java.io.Serializable {
	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月12日
	 */
	private static final long serialVersionUID = 4798755209631243730L;
	@Id
	@DbField("ACDIndex")
	private Integer acdindex;
	@DbField("ACDName")
	private String acdname;
	@DbField("ACDIP")
	private String acdip;
	@DbField("ACDSetNo")
	private Integer acdsetno;
	@DbField("ACDSetTime")
	private Date acdsettime;
	@DbField("Server_Name")
	private String serverName;
	
	public Integer getAcdindex() {
		return acdindex;
	}
	public void setAcdindex(Integer acdindex) {
		this.acdindex = acdindex;
	}
	public String getAcdname() {
		return acdname;
	}
	public void setAcdname(String acdname) {
		this.acdname = acdname;
	}
	public String getAcdip() {
		return acdip;
	}
	public void setAcdip(String acdip) {
		this.acdip = acdip;
	}

	public Integer getAcdsetno() {
		return acdsetno;
	}
	public void setAcdsetno(Integer acdsetno) {
		this.acdsetno = acdsetno;
	}
	public Date getAcdsettime() {
		return acdsettime;
	}
	public void setAcdsettime(Date acdsettime) {
		this.acdsettime = acdsettime;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	
	
}