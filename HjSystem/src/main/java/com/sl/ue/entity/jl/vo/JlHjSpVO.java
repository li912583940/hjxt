package com.sl.ue.entity.jl.vo;

import com.sl.ue.entity.jl.JlHjSp;
import com.sl.ue.util.anno.DbField;

public class JlHjSpVO extends JlHjSp{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    private String frNo;
    
    private String frName;

    private String jqName;
    
    private String callTimeStart;
    
	private String callTimeEnd;
	
	private String spGroupName;
	
	
    public String getFrNo() {
		return frNo;
	}

	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}

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
	
	public String getCallTimeStart() {
		return callTimeStart;
	}

	public void setCallTimeStart(String callTimeStart) {
		this.callTimeStart = callTimeStart;
	}

	public String getCallTimeEnd() {
		return callTimeEnd;
	}

	public void setCallTimeEnd(String callTimeEnd) {
		this.callTimeEnd = callTimeEnd;
	}
	
	public String getSpGroupName() {
		return spGroupName;
	}

	public void setSpGroupName(String spGroupName) {
		this.spGroupName = spGroupName;
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
