package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_QS_TEMP")
public class JlQsTemp implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -783340985763536113L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("relCriNumb")
	private String relCriNumb;
	@DbField("UnitCode")
	private String unitCode;
	@DbField("relName")
	private String relName;
	@DbField("relSex")
	private String relSex;
	@DbField("relNation")
	private String relNation;
	@DbField("relBirthday")
	private String relBirthday;
	@DbField("relIdNumb")
	private String relIdNumb;
	@DbField("relAddr")
	private String relAddr;
	@DbField("relUnit")
	private String relUnit;
	@DbField("relTel")
	private String relTel;
	@DbField("relPolity")
	private String relPolity;
	@DbField("relOccup")
	private String relOccup;
	@DbField("relSpecial")
	private String relSpecial;


	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getRelCriNumb() {
		return this.relCriNumb;
	}

	public void setRelCriNumb(String relCriNumb) {
		this.relCriNumb = relCriNumb;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getRelName() {
		return this.relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelSex() {
		return this.relSex;
	}

	public void setRelSex(String relSex) {
		this.relSex = relSex;
	}

	public String getRelNation() {
		return this.relNation;
	}

	public void setRelNation(String relNation) {
		this.relNation = relNation;
	}

	public String getRelBirthday() {
		return this.relBirthday;
	}

	public void setRelBirthday(String relBirthday) {
		this.relBirthday = relBirthday;
	}

	public String getRelIdNumb() {
		return this.relIdNumb;
	}

	public void setRelIdNumb(String relIdNumb) {
		this.relIdNumb = relIdNumb;
	}

	public String getRelAddr() {
		return this.relAddr;
	}

	public void setRelAddr(String relAddr) {
		this.relAddr = relAddr;
	}

	public String getRelUnit() {
		return this.relUnit;
	}

	public void setRelUnit(String relUnit) {
		this.relUnit = relUnit;
	}

	public String getRelTel() {
		return this.relTel;
	}

	public void setRelTel(String relTel) {
		this.relTel = relTel;
	}

	public String getRelPolity() {
		return this.relPolity;
	}

	public void setRelPolity(String relPolity) {
		this.relPolity = relPolity;
	}

	public String getRelOccup() {
		return this.relOccup;
	}

	public void setRelOccup(String relOccup) {
		this.relOccup = relOccup;
	}

	public String getRelSpecial() {
		return this.relSpecial;
	}

	public void setRelSpecial(String relSpecial) {
		this.relSpecial = relSpecial;
	}

}