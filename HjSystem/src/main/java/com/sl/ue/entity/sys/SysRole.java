package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

@Table("sys_role")
public class SysRole implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("id")
    private Integer id;

    /** 名称 */
    @DbField("name")
    private String name;

    /** 描述 */
    @DbField("description")
    private String description;

    /** 是否可用 1：可用， 0：不可用 */
    @DbField("useble")
    private Integer useble;

    /** 创建时间 */
    @DbField("create_time")
    private Date createTime;

    /** 创建人id */
    @DbField("create_user_id")
    private Integer createUserId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getUseble() {
        return this.useble;
    }

    public void setUseble(Integer useble) {
        this.useble = useble;
    }
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}
