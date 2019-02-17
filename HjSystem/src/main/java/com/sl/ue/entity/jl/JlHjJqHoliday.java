package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [监区特殊会见日]
 * L_晓天  @2019年2月3日
 */
@Table("jl_hj_jq_holiday")
public class JlHjJqHoliday implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 监区编号 */
    @DbField("jq_no")
    private String jqNo;

    /**  */
    @Id
    @DbField("id")
    private Integer id;

    public String getJqNo() {
        return this.jqNo;
    }

    public void setJqNo(String jqNo) {
        this.jqNo = jqNo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
