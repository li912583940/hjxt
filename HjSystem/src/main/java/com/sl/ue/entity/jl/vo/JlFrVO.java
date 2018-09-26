package com.sl.ue.entity.jl.vo;

import com.sl.ue.entity.jl.JlFr;

public class JlFrVO extends JlFr{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    private String jqName;
    
    
    
    
    public String getJqName() {
		return jqName;
	}

	public void setJqName(String jqName) {
		this.jqName = jqName;
	}

	
	/*---------------------------  处理关联表  -----------------------------*/
	
	private String joinField; // 字段
    
    private String joinTable;
    
    private String joinWhere;

	public String getJoinField() {
		return joinField;
	}

	public void setJoinField(String joinField) {
		this.joinField = joinField;
	}

	public String getJoinTable() {
		return joinTable;
	}

	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}

	public String getJoinWhere() {
		return joinWhere;
	}

	public void setJoinWhere(String joinWhere) {
		this.joinWhere = joinWhere;
	}
    
    
    
}
