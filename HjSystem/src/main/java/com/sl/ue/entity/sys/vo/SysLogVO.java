package com.sl.ue.entity.sys.vo;

import com.sl.ue.entity.sys.SysLog;
import com.sl.ue.util.anno.DbField;

public class SysLogVO extends SysLog{

    /** 序列化 */
    private static final long serialVersionUID = 1L;
    
    private String callTimeStart; // 开始时间-查询条件
	private String callTimeEnd;   // 结束时间-查询条件

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
