package com.sl.ue.entity.other;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("http_abms_dept")
public class HttpAbmsDept implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("depId")
    private String depid;

    /**  */
    @DbField("depName")
    private String depname;

    public String getDepid() {
        return this.depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }
    public String getDepname() {
        return this.depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }
}
