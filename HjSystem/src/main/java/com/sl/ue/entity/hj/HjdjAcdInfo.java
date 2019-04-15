package com.sl.ue.entity.hj;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("HJDJ_ACD_INFO")
public class HjdjAcdInfo implements java.io.Serializable {

	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月12日
	 */
	private static final long serialVersionUID = 1109239287951817495L;
	@Id
	@DbField("ACDIndex")
	private Integer acdindex;
	@DbField("ACDGetNo")
	private Integer acdgetno;
	@DbField("ACDGetTime")
	private Date acdgettime;
	@DbField("ACDSetNo")
	private Integer acdsetno;
	@DbField("ACDSetTime")
	private Date acdsettime;
	@DbField("CurDate")
	private Integer curdate;
	@DbField("InitNo")
	private Integer initno;
	@DbField("UnitNo")
	private Integer unitno;
	@DbField("Server_Name")
	private String serverName;
	@DbField("ACDurl")
	private String acdurl;
	
	public Integer getAcdindex() {
		return acdindex;
	}
	public void setAcdindex(Integer acdindex) {
		this.acdindex = acdindex;
	}
	
	public Integer getAcdgetno() {
		return acdgetno;
	}
	public void setAcdgetno(Integer acdgetno) {
		this.acdgetno = acdgetno;
	}
	public Date getAcdgettime() {
		return acdgettime;
	}
	public void setAcdgettime(Date acdgettime) {
		this.acdgettime = acdgettime;
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
	public Integer getCurdate() {
		return curdate;
	}
	public void setCurdate(Integer curdate) {
		this.curdate = curdate;
	}
	public Integer getInitno() {
		return initno;
	}
	public void setInitno(Integer initno) {
		this.initno = initno;
	}
	public Integer getUnitno() {
		return unitno;
	}
	public void setUnitno(Integer unitno) {
		this.unitno = unitno;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getAcdurl() {
		return acdurl;
	}
	public void setAcdurl(String acdurl) {
		this.acdurl = acdurl;
	}

	

}