package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_MONITOR_VOC")
public class JlMonitorVoc implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = -4073620127524360795L;
	@Id
	@DbField("WebID")
	private Integer webid;
	@DbField("Voc_ID")
	private Integer vocId;
	@DbField("Voc_Info")
	private String vocInfo;




	public Integer getWebid() {
		return webid;
	}

	public void setWebid(Integer webid) {
		this.webid = webid;
	}

	public Integer getVocId() {
		return this.vocId;
	}

	public void setVocId(Integer vocId) {
		this.vocId = vocId;
	}

	public String getVocInfo() {
		return this.vocInfo;
	}

	public void setVocInfo(String vocInfo) {
		this.vocInfo = vocInfo;
	}

}