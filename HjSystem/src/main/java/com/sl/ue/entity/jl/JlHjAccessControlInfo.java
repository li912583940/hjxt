package com.sl.ue.entity.jl;

import java.sql.Timestamp;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_ACCESS_CONTROL_INFO")
public class JlHjAccessControlInfo implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = 6780927106507499731L;
	@DbField("HJID")
	private Long hjid;
	@DbField("IP")
	private String ip;
	@DbField("KzqAddress")
	private String kzqAddress;
	@DbField("DianAddress")
	private String dianAddress;
	@DbField("Card")
	private String card;
	@DbField("Card_QH")
	private String cardQH;
	@DbField("InTime")
	private Timestamp inTime;
	@DbField("OutTime")
	private Timestamp outTime;
	@DbField("state")
	private Integer state;
	@Id
	@DbField("WebID")
	private Integer webId;

	

	public Long getHjid() {
		return hjid;
	}

	public void setHjid(Long hjid) {
		this.hjid = hjid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getKzqAddress() {
		return kzqAddress;
	}

	public void setKzqAddress(String kzqAddress) {
		this.kzqAddress = kzqAddress;
	}

	public String getDianAddress() {
		return dianAddress;
	}

	public void setDianAddress(String dianAddress) {
		this.dianAddress = dianAddress;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getCardQH() {
		return cardQH;
	}

	public void setCardQH(String cardQH) {
		this.cardQH = cardQH;
	}

	public Timestamp getInTime() {
		return inTime;
	}

	public void setInTime(Timestamp inTime) {
		this.inTime = inTime;
	}

	public Timestamp getOutTime() {
		return outTime;
	}

	public void setOutTime(Timestamp outTime) {
		this.outTime = outTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getWebId() {
		return webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	// Property accessors

	

}