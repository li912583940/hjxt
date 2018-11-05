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

    /** 是否可用 1：可用， 0：不可用  默认：1*/
    @DbField("useble")
    private Integer useble;

    /** 创建时间 */
    @DbField("create_time")
    private Date createTime;

    /** 创建人id */
    @DbField("create_user_id")
    private Integer createUserId;
    
    /** 当前角色是否已分配菜单权限， 1：已分配，0：未分配， 默认：0 */
    @DbField("authority_resource")
    private Integer authorityResource;
    
    /** 当前角色是否已分配监区权限， 1：已分配，0：未分配， 默认：0 */
    @DbField("authority_jq")
    private Integer authorityJq;
    
    /** 是否位管理员 1：是， 0：不是  默认：0 */
    @DbField("is_admin")
    private Integer isAdmin;
    
    /** 用于权限管理中element控件反选的。并非最终的角色与资源的关联关系 */
    @DbField("resources")
    private String resources;
    
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

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getAuthorityResource() {
		return authorityResource;
	}

	public void setAuthorityResource(Integer authorityResource) {
		this.authorityResource = authorityResource;
	}

	public Integer getAuthorityJq() {
		return authorityJq;
	}

	public void setAuthorityJq(Integer authorityJq) {
		this.authorityJq = authorityJq;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}


    
    
}
