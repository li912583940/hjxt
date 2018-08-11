package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_FR_TEMP")
public class JlFrTemp implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -8761334227226396105L;
	@Id
	@DbField("ryid")
	private Long ryid;
	@DbField("bm")
	private String bm;
	@DbField("xm")
	private String xm;
	@DbField("xb")
	private String xb;
	@DbField("mz")
	private String mz;
	@DbField("csrq")
	private String csrq;
	@DbField("dw")
	private String dw;
	@DbField("zzm")
	private String zzm;
	@DbField("jg")
	private String jg;
	@DbField("jtzzcs")
	private String jtzzcs;
	@DbField("jtzz")
	private String jtzz;
	@DbField("rjrq")
	private String rjrq;
	@DbField("syrq")
	private String syrq;
	@DbField("pjxq")
	private String pjxq;
	@DbField("pjqr")
	private String pjqr;
	@DbField("pjzr")
	private String pjzr;
	@DbField("zxxq")
	private String zxxq;
	@DbField("zxxqqr")
	private String zxxqqr;
	@DbField("zxxqzr")
	private String zxxqzr;
	@DbField("jb")
	private String jb;
	@DbField("dah")
	private String dah;

	
	public Long getRyid() {
		return this.ryid;
	}

	public String getDah() {
		return dah;
	}

	public void setDah(String dah) {
		this.dah = dah;
	}

	public void setRyid(Long ryid) {
		this.ryid = ryid;
	}

	public String getBm() {
		return this.bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXb() {
		return this.xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getMz() {
		return this.mz;
	}

	public void setMz(String mz) {
		this.mz = mz;
	}

	public String getCsrq() {
		return this.csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getDw() {
		return this.dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public String getZzm() {
		return this.zzm;
	}

	public void setZzm(String zzm) {
		this.zzm = zzm;
	}

	public String getJg() {
		return this.jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getJtzzcs() {
		return this.jtzzcs;
	}

	public void setJtzzcs(String jtzzcs) {
		this.jtzzcs = jtzzcs;
	}

	public String getJtzz() {
		return this.jtzz;
	}

	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}

	public String getRjrq() {
		return this.rjrq;
	}

	public void setRjrq(String rjrq) {
		this.rjrq = rjrq;
	}

	public String getSyrq() {
		return this.syrq;
	}

	public void setSyrq(String syrq) {
		this.syrq = syrq;
	}

	public String getPjxq() {
		return this.pjxq;
	}

	public void setPjxq(String pjxq) {
		this.pjxq = pjxq;
	}

	public String getPjqr() {
		return this.pjqr;
	}

	public void setPjqr(String pjqr) {
		this.pjqr = pjqr;
	}

	public String getPjzr() {
		return this.pjzr;
	}

	public void setPjzr(String pjzr) {
		this.pjzr = pjzr;
	}

	public String getZxxq() {
		return this.zxxq;
	}

	public void setZxxq(String zxxq) {
		this.zxxq = zxxq;
	}

	public String getZxxqqr() {
		return this.zxxqqr;
	}

	public void setZxxqqr(String zxxqqr) {
		this.zxxqqr = zxxqqr;
	}

	public String getZxxqzr() {
		return this.zxxqzr;
	}

	public void setZxxqzr(String zxxqzr) {
		this.zxxqzr = zxxqzr;
	}

	public String getJb() {
		return this.jb;
	}

	public void setJb(String jb) {
		this.jb = jb;
	}

}