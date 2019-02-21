package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_notice_conf")
public class SysNoticeConf implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("id")
    private Integer id;

    /** 会见通知。 0：登记完自动发起。1：身份验证成功后发起 */
    @DbField("hj_notice")
    private Integer hjNotice;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getHjNotice() {
        return this.hjNotice;
    }

    public void setHjNotice(Integer hjNotice) {
        this.hjNotice = hjNotice;
    }
}
