package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

@Table("JL_TTS")
public class JlTts implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("TtsID")
    private String ttsid;

    /**  */
    @DbField("TtsType")
    private Integer ttstype;

    /**  */
    @DbField("TtsResult")
    private Integer ttsresult;

    /**  */
    @DbField("TtsTime")
    private Date ttstime;

    /**  */
    @DbField("TtsFile")
    private String ttsfile;

    /**  */
    @DbField("TtsTxt")
    private String ttstxt;

    /**  */
    @DbField("TtsUrl")
    private String ttsurl;

    public String getTtsid() {
        return this.ttsid;
    }

    public void setTtsid(String ttsid) {
        this.ttsid = ttsid;
    }
    public Integer getTtstype() {
        return this.ttstype;
    }

    public void setTtstype(Integer ttstype) {
        this.ttstype = ttstype;
    }
    public Integer getTtsresult() {
        return this.ttsresult;
    }

    public void setTtsresult(Integer ttsresult) {
        this.ttsresult = ttsresult;
    }
    public Date getTtstime() {
        return this.ttstime;
    }

    public void setTtstime(Date ttstime) {
        this.ttstime = ttstime;
    }
    public String getTtsfile() {
        return this.ttsfile;
    }

    public void setTtsfile(String ttsfile) {
        this.ttsfile = ttsfile;
    }
    public String getTtstxt() {
        return this.ttstxt;
    }

    public void setTtstxt(String ttstxt) {
        this.ttstxt = ttstxt;
    }
    public String getTtsurl() {
        return this.ttsurl;
    }

    public void setTtsurl(String ttsurl) {
        this.ttsurl = ttsurl;
    }
}
