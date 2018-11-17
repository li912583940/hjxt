package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_HJ_SP_FR")
public class JlHjSpFr implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("SPID")
    private Long spid;

    /**  */
    @DbField("FR_No")
    private String frNo;

    /**  */
    @DbField("FR_Name")
    private String frName;

    /**  */
    @DbField("SP_Reason")
    private String spReason;

    /**  */
    @DbField("SP_Remarks")
    private String spRemarks;

    public Long getSpid() {
        return this.spid;
    }

    public void setSpid(Long spid) {
        this.spid = spid;
    }
    public String getFrNo() {
        return this.frNo;
    }

    public void setFrNo(String frNo) {
        this.frNo = frNo;
    }
    public String getFrName() {
        return this.frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }
    public String getSpReason() {
        return this.spReason;
    }

    public void setSpReason(String spReason) {
        this.spReason = spReason;
    }
    public String getSpRemarks() {
        return this.spRemarks;
    }

    public void setSpRemarks(String spRemarks) {
        this.spRemarks = spRemarks;
    }
}
