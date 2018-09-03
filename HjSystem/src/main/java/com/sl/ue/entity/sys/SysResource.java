package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

@Table("sys_resource")
public class SysResource implements java.io.Serializable{

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

    /** 资源类型，menu：目录，button：按钮 */
    @DbField("type")
    private String type;

    /** 父id(目录id)，代表类型为button的按钮属于哪个目录 */
    @DbField("parent_id")
    private Integer parentId;

    /** 资源请求的URL */
    @DbField("path_url")
    private String pathUrl;

    /** 标识，类型为button时，区分这个资源，如 1：新增，2：修改，3：删除，4导出。查询为基本功能，默认为0，数据库中可以不存这条记录 */
    @DbField("iden")
    private Integer iden;

    /** 期望标识，意思为当权限配置出现第三种时，可以随意配置类型为button，只要标识一样，就作为一种权限处理 */
    @DbField("iden_will")
    private Integer idenWill;

    /** 期望标识的名称 */
    @DbField("iden_will_name")
    private String idenWillName;

    /** 是否可用 1：可用， 0：不可用 */
    @DbField("useble")
    private Integer useble;

    /** 创建时间 */
    @DbField("create_time")
    private Date createTime;

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
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getPathUrl() {
        return this.pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }
    public Integer getIden() {
        return this.iden;
    }

    public void setIden(Integer iden) {
        this.iden = iden;
    }
    public Integer getIdenWill() {
        return this.idenWill;
    }

    public void setIdenWill(Integer idenWill) {
        this.idenWill = idenWill;
    }
    public String getIdenWillName() {
        return this.idenWillName;
    }

    public void setIdenWillName(String idenWillName) {
        this.idenWillName = idenWillName;
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
}
