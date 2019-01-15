package com.sl.ue.entity.jl.vo;

import java.util.Date;

import com.sl.ue.entity.jl.JlHjSp;

public class JlHjSpVO extends JlHjSp{

    /** 序列化 */
    private static final long serialVersionUID = 1L;
    
    private String frName;
    private String jqName;
    private String qsInfo;
    private Date djTime;
    
    private Integer spPermission; // 1:有审批权限。0：没有审批权限
    
    public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getJqName() {
		return jqName;
	}

	public void setJqName(String jqName) {
		this.jqName = jqName;
	}

	public String getQsInfo() {
		return qsInfo;
	}

	public void setQsInfo(String qsInfo) {
		this.qsInfo = qsInfo;
	}

	public Date getDjTime() {
		return djTime;
	}

	public void setDjTime(Date djTime) {
		this.djTime = djTime;
	}

	public Integer getSpPermission() {
		return spPermission;
	}

	public void setSpPermission(Integer spPermission) {
		this.spPermission = spPermission;
	}

    /*---------------------------  处理关联表  -----------------------------*/




	private String leftJoinField; // 关联表字段

    private String leftJoinTable; // 关联表

    private String leftJoinWhere; // 关联表条件

    public String getLeftJoinField() {
        return leftJoinField;
    }

    public void setLeftJoinField(String leftJoinField) {
        this.leftJoinField = leftJoinField;
    }

    public String getLeftJoinTable() {
        return leftJoinTable;
    }

    public void setLeftJoinTable(String leftJoinTable) {
        this.leftJoinTable = leftJoinTable;
    }

    public String getLeftJoinWhere() {
        return leftJoinWhere;
    }

    public void setLeftJoinWhere(String leftJoinWhere) {
        this.leftJoinWhere = leftJoinWhere;
    }


}
