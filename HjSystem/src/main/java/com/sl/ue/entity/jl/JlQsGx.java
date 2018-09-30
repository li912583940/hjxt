package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_QS_GX")
public class JlQsGx implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @DbField("QS_GX")
    private String qsGx;

    public String getQsGx() {
        return this.qsGx;
    }

    public void setQsGx(String qsGx) {
        this.qsGx = qsGx;
    }
}
