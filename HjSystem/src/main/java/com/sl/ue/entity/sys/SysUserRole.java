package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_user_role")
public class SysUserRole implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @Id
    @DbField("user_id")
    private Integer userId;

    /** 角色id */
    @Id
    @DbField("role_id")
    private Integer roleId;

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
