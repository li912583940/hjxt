package com.sl.ue.entity.jl.vo;

import com.sl.ue.entity.jl.JlHjRec;

public class JlHjRecVO extends JlHjRec{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    private String qsName; // 亲属  查询条件 
    private Integer qsIndex; //亲属个数
    private String qsInfo; // 亲属信息
    
    private String callRecfileUrl;
	private String callVideofile1Url;
	private String callVideofile2Url;
	
    public String getQsName() {
  		return qsName;
  	}

  	public void setQsName(String qsName) {
  		this.qsName = qsName;
  	}
  	
	public Integer getQsIndex() {
		return qsIndex;
	}

	public void setQsIndex(Integer qsIndex) {
		this.qsIndex = qsIndex;
	}

	public String getQsInfo() {
		return qsInfo;
	}

	public void setQsInfo(String qsInfo) {
		this.qsInfo = qsInfo;
	}

	public String getCallRecfileUrl() {
		return callRecfileUrl;
	}

	public void setCallRecfileUrl(String callRecfileUrl) {
		this.callRecfileUrl = callRecfileUrl;
	}

	public String getCallVideofile1Url() {
		return callVideofile1Url;
	}

	public void setCallVideofile1Url(String callVideofile1Url) {
		this.callVideofile1Url = callVideofile1Url;
	}

	public String getCallVideofile2Url() {
		return callVideofile2Url;
	}

	public void setCallVideofile2Url(String callVideofile2Url) {
		this.callVideofile2Url = callVideofile2Url;
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
