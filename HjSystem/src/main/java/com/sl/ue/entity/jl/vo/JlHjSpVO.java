package com.sl.ue.entity.jl.vo;

import java.util.Date;

import com.sl.ue.entity.jl.JlHjSp;
import com.sl.ue.util.anno.DbField;

public class JlHjSpVO extends JlHjSp{

    /** 序列化 */
    private static final long serialVersionUID = 1L;
    
    private String enclosureUrl; // 亲属审批时的亲属附件url
    
    private String qsInfo1;
	private String qsInfo2;
	private String qsInfo3;
	private String qsInfo4;
	private String qsInfo5;
	private String qsInfo6;
	private String qsInfo7;
	private String qsInfo8;
	private String qsInfo9;
	
    private Integer spPermission; // 1:有审批权限。0：没有审批权限
    
    
	public String getEnclosureUrl() {
		return enclosureUrl;
	}

	public void setEnclosureUrl(String enclosureUrl) {
		this.enclosureUrl = enclosureUrl;
	}

	public String getQsInfo1() {
		return qsInfo1;
	}

	public void setQsInfo1(String qsInfo1) {
		this.qsInfo1 = qsInfo1;
	}

	public String getQsInfo2() {
		return qsInfo2;
	}

	public void setQsInfo2(String qsInfo2) {
		this.qsInfo2 = qsInfo2;
	}

	public String getQsInfo3() {
		return qsInfo3;
	}

	public void setQsInfo3(String qsInfo3) {
		this.qsInfo3 = qsInfo3;
	}

	public String getQsInfo4() {
		return qsInfo4;
	}

	public void setQsInfo4(String qsInfo4) {
		this.qsInfo4 = qsInfo4;
	}

	public String getQsInfo5() {
		return qsInfo5;
	}

	public void setQsInfo5(String qsInfo5) {
		this.qsInfo5 = qsInfo5;
	}

	public String getQsInfo6() {
		return qsInfo6;
	}

	public void setQsInfo6(String qsInfo6) {
		this.qsInfo6 = qsInfo6;
	}

	public String getQsInfo7() {
		return qsInfo7;
	}

	public void setQsInfo7(String qsInfo7) {
		this.qsInfo7 = qsInfo7;
	}

	public String getQsInfo8() {
		return qsInfo8;
	}

	public void setQsInfo8(String qsInfo8) {
		this.qsInfo8 = qsInfo8;
	}

	public String getQsInfo9() {
		return qsInfo9;
	}

	public void setQsInfo9(String qsInfo9) {
		this.qsInfo9 = qsInfo9;
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
