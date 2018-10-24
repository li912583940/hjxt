package com.sl.ue.entity.jl.vo;

import com.sl.ue.entity.jl.JlFr;

public class JlFrVO extends JlFr{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    private String jqName; // 监区名称
    
    private String jbName; // 级别名称
    
    private Integer qsNum; // 亲属数量
    
    public String getJqName() {
		return jqName;
	}

	public void setJqName(String jqName) {
		this.jqName = jqName;
	}

	public String getJbName() {
		return jbName;
	}

	public void setJbName(String jbName) {
		this.jbName = jbName;
	}
	public Integer getQsNum() {
		return qsNum;
	}

	public void setQsNum(Integer qsNum) {
		this.qsNum = qsNum;
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
