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
	private Integer acdgetNo;
	@DbField("ACDGetTime")
	private Date acdgetTime;
	@DbField("ACDSetNo")
	private Integer acdsetNo;
	@DbField("ACDSetTime")
	private Date acdsetTime;
	@DbField("CurDate")
	private Integer curDate;
	@DbField("InitNo")
	private Integer initNo;
	@DbField("UnitNo")
	private Integer unitNo;
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
	public Integer getAcdgetNo() {
		return acdgetNo;
	}
	public void setAcdgetNo(Integer acdgetNo) {
		this.acdgetNo = acdgetNo;
	}
	public Date getAcdgetTime() {
		return acdgetTime;
	}
	public void setAcdgetTime(Date acdgetTime) {
		this.acdgetTime = acdgetTime;
	}
	public Integer getAcdsetNo() {
		return acdsetNo;
	}
	public void setAcdsetNo(Integer acdsetNo) {
		this.acdsetNo = acdsetNo;
	}
	public Date getAcdsetTime() {
		return acdsetTime;
	}
	public void setAcdsetTime(Date acdsetTime) {
		this.acdsetTime = acdsetTime;
	}
	public Integer getCurDate() {
		return curDate;
	}
	public void setCurDate(Integer curDate) {
		this.curDate = curDate;
	}
	public Integer getInitNo() {
		return initNo;
	}
	public void setInitNo(Integer initNo) {
		this.initNo = initNo;
	}
	public Integer getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
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