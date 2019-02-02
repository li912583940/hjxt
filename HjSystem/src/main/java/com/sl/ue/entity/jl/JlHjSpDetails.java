package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import java.util.Date;

@Table("jl_hj_sp_details")
public class JlHjSpDetails implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    @Id
    @DbField("id")
    private Integer id;

    /** 审批id */
    @DbField("sp_id")
    private Integer spId;

    /** 审批用户编号 */
    @DbField("user_no")
    private String userNo;

    /** 审批用户姓名 */
    @DbField("user_name")
    private String userName;

    /** 审批人所在部门编号 */
    @DbField("dept_id")
    private Integer deptId;

    /** 审批人所在部门名称 */
    @DbField("dept_name")
    private String deptName;

    /** 审批状态。1：通过，0：不通过，默认 1 */
    @DbField("state")
    private Integer state;

    /** 审批说明 */
    @DbField("explain")
    private String explain;

    /** 审批时间 */
    @DbField("sp_time")
    private Date spTime;
    
    /** 进度，阶段，指在哪个阶段的审批 */
    @DbField("speed_progress")
    private Integer speedProgress;
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSpId() {
        return this.spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }
    public String getUserNo() {
        return this.userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public String getExplain() {
        return this.explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
    public Date getSpTime() {
        return this.spTime;
    }

    public void setSpTime(Date spTime) {
        this.spTime = spTime;
    }

	public Integer getSpeedProgress() {
		return speedProgress;
	}

	public void setSpeedProgress(Integer speedProgress) {
		this.speedProgress = speedProgress;
	}
    
}
