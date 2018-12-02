package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_role_jq")
public class SysRoleJq implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 角色id */
    @Id
    @DbField("role_id")
    private Integer roleId;

    /** 监区id */
    @Id
    @DbField("jq_id")
    private String jqId;

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public String getJqId() {
        return this.jqId;
    }

    public void setJqId(String jqId) {
        this.jqId = jqId;
    }
}
