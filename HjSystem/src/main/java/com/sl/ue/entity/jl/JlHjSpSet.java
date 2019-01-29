package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("jl_hj_sp_set")
public class JlHjSpSet implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 主键，自增 */
    @Id
    @DbField("id")
    private Integer id;

    @DbField("sp_no")
    private String spNo;
    
    /** 审批流程名称 */
    @DbField("sp_name")
    private String spName;

    /** 审批流程说明 */
    @DbField("sp_explain")
    private String spExplain;

    /** 是否可用；1：可用，0：不可用。默认1 */
    @DbField("usable")
    private Integer usable;

    /** 审批最大次数 */
    @DbField("max_num")
    private Integer maxNum;

    /** 审批内容，暂时只有亲属关系使用该字段，表示那些关系需要审批。多个关系用“,”分隔 */
    @DbField("sp_value")
    private String spValue;
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getSpNo() {
		return spNo;
	}

	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}

	public String getSpName() {
        return this.spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }
    public String getSpExplain() {
        return this.spExplain;
    }

    public void setSpExplain(String spExplain) {
        this.spExplain = spExplain;
    }
    public Integer getUsable() {
        return this.usable;
    }

    public void setUsable(Integer usable) {
        this.usable = usable;
    }
    public Integer getMaxNum() {
        return this.maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

	public String getSpValue() {
		return spValue;
	}

	public void setSpValue(String spValue) {
		this.spValue = spValue;
	}
    
}
