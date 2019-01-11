package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("jl_hj_sp_user")
public class JlHjSpUser implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 主键，自增。 */
    @Id
    @DbField("id")
    private Integer id;

    /** 审批设置主键 */
    @DbField("sp_set_id")
    private Integer spSetId;

    /** 审批人的编号，当类型为部门时，为部门编号；类型用户时，为用户编号 */
    @DbField("sp_user_id")
    private String spUserId;

    /** 审批人的名称，当类型为部门时，为部门名称；类型用户时，为用户名称 */
    @DbField("sp_user_name")
    private String spUserName;

    /** 审批类型，0为用户，1为部门 */
    @DbField("sp_type")
    private Integer spType;

    /** 审批的级别，用来判断当前用户或部门处在第几级别 */
    @DbField("sp_level")
    private Integer spLevel;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSpSetId() {
        return this.spSetId;
    }

    public void setSpSetId(Integer spSetId) {
        this.spSetId = spSetId;
    }
    public String getSpUserId() {
        return this.spUserId;
    }

    public void setSpUserId(String spUserId) {
        this.spUserId = spUserId;
    }
    public String getSpUserName() {
        return this.spUserName;
    }

    public void setSpUserName(String spUserName) {
        this.spUserName = spUserName;
    }
    public Integer getSpType() {
        return this.spType;
    }

    public void setSpType(Integer spType) {
        this.spType = spType;
    }
    public Integer getSpLevel() {
        return this.spLevel;
    }

    public void setSpLevel(Integer spLevel) {
        this.spLevel = spLevel;
    }
}
