package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

@Table("jl_qs_sp")
public class JlQsSp implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @DbField("FR_No")
    private String frNo;

    /**  */
    @DbField("QS_ZJLB")
    private Integer qsZjlb;

    /**  */
    @DbField("QS_SFZ")
    private String qsSfz;

    /**  */
    @DbField("QS_Name")
    private String qsName;

    /**  */
    @DbField("QS_Card")
    private String qsCard;

    /**  */
    @DbField("GX")
    private String gx;

    /**  */
    @DbField("XB")
    private String xb;

    /**  */
    @DbField("DZ")
    private String dz;

    /**  */
    @DbField("ZP")
    private Byte[] zp;

    /**  */
    @DbField("JZ")
    private Byte[] jz;

    /**  */
    @Id(inc=true)
    @DbField("WebID")
    private Integer webid;

    /**  */
    @DbField("CreateTime")
    private Date createtime;

    /**  */
    @DbField("SQ_ID")
    private String sqId;

    /**  */
    @DbField("FJ_Address")
    private String fjAddress;

    /**  */
    @DbField("ZP_URL")
    private String zpUrl;

    /**  */
    @DbField("jz_url")
    private String jzUrl;

    /**  */
    @DbField("TELE")
    private String tele;
    
    /* 亲属附件url */
	@DbField("enclosure_url")
	private String enclosureUrl;
	
    /** 审批状态 0：进行中；1：审批通过；2：审批不通过；3：审批失败；默认0 */
    @DbField("state")
    private Integer state;
    
    public String getFrNo() {
        return this.frNo;
    }

    public void setFrNo(String frNo) {
        this.frNo = frNo;
    }
    public Integer getQsZjlb() {
        return this.qsZjlb;
    }

    public void setQsZjlb(Integer qsZjlb) {
        this.qsZjlb = qsZjlb;
    }
    public String getQsSfz() {
        return this.qsSfz;
    }

    public void setQsSfz(String qsSfz) {
        this.qsSfz = qsSfz;
    }
    public String getQsName() {
        return this.qsName;
    }

    public void setQsName(String qsName) {
        this.qsName = qsName;
    }
    public String getQsCard() {
        return this.qsCard;
    }

    public void setQsCard(String qsCard) {
        this.qsCard = qsCard;
    }
    public String getGx() {
        return this.gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }
    public String getXb() {
        return this.xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }
    public String getDz() {
        return this.dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }
    public Byte[] getZp() {
        return this.zp;
    }

    public void setZp(Byte[] zp) {
        this.zp = zp;
    }
    public Byte[] getJz() {
        return this.jz;
    }

    public void setJz(Byte[] jz) {
        this.jz = jz;
    }
    public Integer getWebid() {
        return this.webid;
    }

    public void setWebid(Integer webid) {
        this.webid = webid;
    }
    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public String getSqId() {
        return this.sqId;
    }

    public void setSqId(String sqId) {
        this.sqId = sqId;
    }
    public String getFjAddress() {
        return this.fjAddress;
    }

    public void setFjAddress(String fjAddress) {
        this.fjAddress = fjAddress;
    }
    public String getZpUrl() {
        return this.zpUrl;
    }

    public void setZpUrl(String zpUrl) {
        this.zpUrl = zpUrl;
    }
    public String getJzUrl() {
        return this.jzUrl;
    }

    public void setJzUrl(String jzUrl) {
        this.jzUrl = jzUrl;
    }
    public String getTele() {
        return this.tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

	public String getEnclosureUrl() {
		return enclosureUrl;
	}

	public void setEnclosureUrl(String enclosureUrl) {
		this.enclosureUrl = enclosureUrl;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
    
}
