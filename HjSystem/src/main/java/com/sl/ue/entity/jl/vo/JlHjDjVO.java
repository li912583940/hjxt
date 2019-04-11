package com.sl.ue.entity.jl.vo;

import com.sl.ue.entity.jl.JlHjDj;
import com.sl.ue.util.anno.DbField;

public class JlHjDjVO extends JlHjDj{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    private String zw; // 座位号

    private String qsName; // 亲属姓名 ，登记查询中的条件
    
    private String qsInfo; // 登记中的亲属信息
    
    private String djUserName; //登记人姓名
    
    private Integer qsNum; //亲属人数
    
    private String infoZm;
    
    private String callTimeStart; //查询时间 开始
	private String callTimeEnd; //查询时间 结束
	
	private Integer stateZdzf;
	
	private String infoZdzf;
	
	private String jbName;
	
    public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}
    
	public String getQsName() {
		return qsName;
	}

	public void setQsName(String qsName) {
		this.qsName = qsName;
	}
	
	public String getQsInfo() {
		return qsInfo;
	}

	public void setQsInfo(String qsInfo) {
		this.qsInfo = qsInfo;
	}

	public String getDjUserName() {
		return djUserName;
	}

	public void setDjUserName(String djUserName) {
		this.djUserName = djUserName;
	}
	
	public Integer getQsNum() {
		return qsNum;
	}

	public void setQsNum(Integer qsNum) {
		this.qsNum = qsNum;
	}
	
	public String getInfoZm() {
		return infoZm;
	}

	public void setInfoZm(String infoZm) {
		this.infoZm = infoZm;
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
	
	public Integer getStateZdzf() {
		return stateZdzf;
	}

	public void setStateZdzf(Integer stateZdzf) {
		this.stateZdzf = stateZdzf;
	}

	public String getInfoZdzf() {
		return infoZdzf;
	}

	public void setInfoZdzf(String infoZdzf) {
		this.infoZdzf = infoZdzf;
	}
	
	public String getJbName() {
		return jbName;
	}

	public void setJbName(String jbName) {
		this.jbName = jbName;
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
