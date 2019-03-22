package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Table("JL_QS")
public class JlQs implements java.io.Serializable{

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
    @DbField("QS_SFZ_WLH")
    private String qsSfzWlh;

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
    @DbField("TELE")
    private String tele;

    /**  */
    @DbField("SW")
    private Integer sw;

    /**  */
    @DbField("ZP")
    private Byte[] zp;

    /**  */
    @DbField("JZ")
    private Byte[] jz;

    /**  */
    @DbField("SP_State")
    private Integer spState;

    /**  */
    @DbField("SP_UserNo")
    private String spUserno;

    /**  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DbField("SP_Time")
    private Date spTime;

    /**  */
    @DbField("SP_Info")
    private String spInfo;

    /**  */
    @DbField("SP_Mon")
    private String spMon;

    /**  */
    @Id
    @DbField("WebID")
    private Integer webid;

    /**  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DbField("CreateTime")
    private Date createtime;

    /**  */
    @DbField("Talk_TimeLen")
    private Integer talkTimelen;

    /**  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DbField("Talk_TimeOver")
    private Date talkTimeover;

    /**  */
    @DbField("Face_ZP")
    private Byte[] faceZp;

    /**  */
    @DbField("Face_State")
    private Integer faceState;

    /**  */
    @DbField("Face_ID")
    private Integer faceId;

    /**  */
    @DbField("BZ")
    private String bz;

    /**  */
    @DbField("ZP_URL")
    private String zpUrl;

    /**  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DbField("HJ_STOP_TIME")
    private Date hjStopTime;

    /**  */
    @DbField("jz_url")
    private String jzUrl;

    /** 亲属附件url */
    @DbField("enclosure_url")
    private String enclosureUrl;

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
    public String getQsSfzWlh() {
        return this.qsSfzWlh;
    }

    public void setQsSfzWlh(String qsSfzWlh) {
        this.qsSfzWlh = qsSfzWlh;
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
    public String getTele() {
        return this.tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }
    public Integer getSw() {
        return this.sw;
    }

    public void setSw(Integer sw) {
        this.sw = sw;
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
    public Integer getSpState() {
        return this.spState;
    }

    public void setSpState(Integer spState) {
        this.spState = spState;
    }
    public String getSpUserno() {
        return this.spUserno;
    }

    public void setSpUserno(String spUserno) {
        this.spUserno = spUserno;
    }
    public Date getSpTime() {
        return this.spTime;
    }

    public void setSpTime(Date spTime) {
        this.spTime = spTime;
    }
    public String getSpInfo() {
        return this.spInfo;
    }

    public void setSpInfo(String spInfo) {
        this.spInfo = spInfo;
    }
    public String getSpMon() {
        return this.spMon;
    }

    public void setSpMon(String spMon) {
        this.spMon = spMon;
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
    public Integer getTalkTimelen() {
        return this.talkTimelen;
    }

    public void setTalkTimelen(Integer talkTimelen) {
        this.talkTimelen = talkTimelen;
    }
    public Date getTalkTimeover() {
        return this.talkTimeover;
    }

    public void setTalkTimeover(Date talkTimeover) {
        this.talkTimeover = talkTimeover;
    }
    public Byte[] getFaceZp() {
        return this.faceZp;
    }

    public void setFaceZp(Byte[] faceZp) {
        this.faceZp = faceZp;
    }
    public Integer getFaceState() {
        return this.faceState;
    }

    public void setFaceState(Integer faceState) {
        this.faceState = faceState;
    }
    public Integer getFaceId() {
        return this.faceId;
    }

    public void setFaceId(Integer faceId) {
        this.faceId = faceId;
    }
    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
    public String getZpUrl() {
        return this.zpUrl;
    }

    public void setZpUrl(String zpUrl) {
        this.zpUrl = zpUrl;
    }
    public Date getHjStopTime() {
        return this.hjStopTime;
    }

    public void setHjStopTime(Date hjStopTime) {
        this.hjStopTime = hjStopTime;
    }
    public String getJzUrl() {
        return this.jzUrl;
    }

    public void setJzUrl(String jzUrl) {
        this.jzUrl = jzUrl;
    }
    public String getEnclosureUrl() {
        return this.enclosureUrl;
    }

    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl;
    }
}
