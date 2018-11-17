package com.sl.ue.entity.jl;


import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_SP_QS")
public class JlHjSpQs implements java.io.Serializable {
	/** */
	private static final long serialVersionUID = 4599728834762505732L;
	@DbField("SPID")
	private Long spId;
	@DbField("QS_Info")
	private String qsInfo;
	@DbField("QS_SFZ")
	private String qsSfz;
	@DbField("Special")
	private Integer special;
	@DbField("SP_Remarks")
	private String spRemarks;
	@DbField("QS_Annex")
	private String qsAnnex;
	@DbField("QS_XB")
	private String qsXb;
	@DbField("QS_ZP")
	private byte[] qsZp;
	@DbField("SP_Reason")
	private String spReason;
	@DbField("QS_DZ")
	private String qsDZ;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Face_State")
	private Integer faceState;
	@DbField("Face_ID")
	private Long faceId;
	@DbField("QS_ZJLB")
	private Integer qsZjlb;
	@DbField("SPBZ")
	private String spbz;
	
	
	public Long getSpId() {
		return spId;
	}
	public void setSpId(Long spId) {
		this.spId = spId;
	}
	public String getQsInfo() {
		return qsInfo;
	}
	public void setQsInfo(String qsInfo) {
		this.qsInfo = qsInfo;
	}
	public String getQsSfz() {
		return qsSfz;
	}
	public void setQsSfz(String qsSfz) {
		this.qsSfz = qsSfz;
	}
	public Integer getSpecial() {
		return special;
	}
	public void setSpecial(Integer special) {
		this.special = special;
	}
	public String getSpRemarks() {
		return spRemarks;
	}
	public void setSpRemarks(String spRemarks) {
		this.spRemarks = spRemarks;
	}
	public String getQsAnnex() {
		return qsAnnex;
	}
	public void setQsAnnex(String qsAnnex) {
		this.qsAnnex = qsAnnex;
	}
	public String getQsXb() {
		return qsXb;
	}
	public void setQsXb(String qsXb) {
		this.qsXb = qsXb;
	}
	public byte[] getQsZp() {
		return qsZp;
	}
	public void setQsZp(byte[] qsZp) {
		this.qsZp = qsZp;
	}
	public String getSpReason() {
		return spReason;
	}
	public void setSpReason(String spReason) {
		this.spReason = spReason;
	}
	public String getQsDZ() {
		return qsDZ;
	}
	public void setQsDZ(String qsDZ) {
		this.qsDZ = qsDZ;
	}
	public Integer getWebId() {
		return webId;
	}
	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public Integer getFaceState() {
		return faceState;
	}

	public void setFaceState(Integer faceState) {
		this.faceState = faceState;
	}

	public Long getFaceId() {
		return faceId;
	}

	public void setFaceId(Long faceId) {
		this.faceId = faceId;
	}

	public Integer getQsZjlb() {
		return qsZjlb;
	}

	public void setQsZjlb(Integer qsZjlb) {
		this.qsZjlb = qsZjlb;
	}

	public String getSpbz() {
		return spbz;
	}

	public void setSpbz(String spbz) {
		this.spbz = spbz;
	}
	
	
}
