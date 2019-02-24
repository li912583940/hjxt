package com.sl.ue.entity.jl;


import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_DJ_QS")
public class JlHjDjQs implements java.io.Serializable {

	/** */
	private static final long serialVersionUID = 5939221614805498854L;
	// Fields
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("HJID")
	private Long hjId;
	@DbField("qs_id")
	private Integer qsId;
	@DbField("FR_No")
	private String frNo;
	@DbField("QS_ZJLB")
	private Integer qsZjlb;
	@DbField("QS_SFZ")
	private String qsSfz;
	@DbField("QS_Name")
	private String qsName;
	@DbField("QS_Card")
	private String qsCard;
	@DbField("GX")
	private String gx;
	@DbField("XB")
	private String xb;
	@DbField("DZ")
	private String dz;
	@DbField("TELE")
	private String tele;
	@DbField("ZP")
	private byte[] zp;
	@DbField("JZ")
	private byte[] jz;
	@DbField("QS_SFZ_WLH")
	private String qsSfzWlh;
	@DbField("ZP_URL")
	private String zpUrl;
	@DbField("jz_url")
	private String jzUrl;
	

	// Constructors

	/** default constructor */
	public JlHjDjQs() {
	}

	/** minimal constructor */
	public JlHjDjQs(Long hjId, Integer qsId) {
		this.hjId = hjId;
		this.qsId = qsId;
	}
	
	/** full constructor */
	public JlHjDjQs(String frNo, String qsSfz, String qsName, String qsCard,
			String gx, String xb, String dz, String tele,
			byte[] zp, byte[] jz,Integer qsZjlb,String qsSfzWlh) {
		this.frNo = frNo;
		this.qsSfz = qsSfz;
		this.qsName = qsName;
		this.qsCard = qsCard;
		this.gx = gx;
		this.xb = xb;
		this.dz = dz;
		this.tele = tele;
		this.zp = zp;
		this.jz = jz;
		this.qsZjlb = qsZjlb;
		this.qsSfzWlh = qsSfzWlh;
	}
	public Integer getWebId() {
		return webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public Long getHjId() {
		return hjId;
	}

	public void setHjId(Long hjId) {
		this.hjId = hjId;
	}

	public Integer getQsId() {
		return qsId;
	}

	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}

	public String getFrNo() {
		return frNo;
	}

	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}

	public Integer getQsZjlb() {
		return qsZjlb;
	}

	public void setQsZjlb(Integer qsZjlb) {
		this.qsZjlb = qsZjlb;
	}

	public String getQsSfz() {
		return qsSfz;
	}

	public void setQsSfz(String qsSfz) {
		this.qsSfz = qsSfz;
	}

	public String getQsName() {
		return qsName;
	}

	public void setQsName(String qsName) {
		this.qsName = qsName;
	}

	public String getQsCard() {
		return qsCard;
	}

	public void setQsCard(String qsCard) {
		this.qsCard = qsCard;
	}

	public String getGx() {
		return gx;
	}

	public void setGx(String gx) {
		this.gx = gx;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

	public byte[] getZp() {
		return zp;
	}

	public void setZp(byte[] zp) {
		this.zp = zp;
	}

	public byte[] getJz() {
		return jz;
	}

	public void setJz(byte[] jz) {
		this.jz = jz;
	}

	public String getQsSfzWlh() {
		return qsSfzWlh;
	}

	public void setQsSfzWlh(String qsSfzWlh) {
		this.qsSfzWlh = qsSfzWlh;
	}

	public String getZpUrl() {
		return zpUrl;
	}

	public void setZpUrl(String zpUrl) {
		this.zpUrl = zpUrl;
	}

	public String getJzUrl() {
		return jzUrl;
	}

	public void setJzUrl(String jzUrl) {
		this.jzUrl = jzUrl;
	}

	

}