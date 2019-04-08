package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

@Table("sys_access_token")
public class SysAccessToken implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @Id
    @DbField("id")
    private Integer id;

    /**  */
    @DbField("user_id")
    private Integer userId;

    /**  */
    @DbField("access_token")
    private String accessToken;

    /**  */
    @DbField("due_time")
    private Date dueTime;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Date getDueTime() {
        return this.dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
}
