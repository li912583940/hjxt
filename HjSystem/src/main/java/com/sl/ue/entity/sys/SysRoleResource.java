package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_role_resource")
public class SysRoleResource implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @Id
    @DbField("role_id")
    private Integer roleId;

    /** 资源ID */
    @Id
    @DbField("resource_id")
    private Integer resourceId;

    /** 资源类型，menu：目录，button：按钮 */
    @DbField("type")
    private String type;

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    public Integer getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
