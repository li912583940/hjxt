package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("jl_hj_holiday")
public class JlHjHoliday implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("holiday")
    private String holiday;

    public String getHoliday() {
        return this.holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }
}
