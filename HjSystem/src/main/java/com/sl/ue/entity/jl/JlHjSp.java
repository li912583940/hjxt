package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("jl_hj_sp")
public class JlHjSp implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    @Id
    @DbField("id")
    private Integer id;

    /** 审批设置的编号 */
    @DbField("set_no")
    private String setNo;

    /** 审批设置的名称 */
    @DbField("set_name")
    private String setName;

    /** 审批次数 */
    @DbField("max_num")
    private Integer maxNum;

    /** 会见id */
    @DbField("hjid")
    private Long hjid;
    
    /** JL_QS_SP表 亲属id */
    @DbField("qs_id")
    private Integer qsId;
    
    /** 审批类型，1：会见；2：亲属 */
    @DbField("type")
    private Integer type;
    
    /** 审批进行状态。0：进行中，1：审批通过，2：审批失败， 3：取消审批；  默认 0 */
    @DbField("state")
    private Integer state;

    /** 进度。对应级别 */
    @DbField("speed_progress")
    private Integer speedProgress;
    
    /** 说明 添加审批记录时，对当前审批的补充说明 */
    @DbField("explain")
    private String explain;
    
    /** 评论 备注 */
    @DbField("remark")
    private String remark;
    
    /** 最后审批时间 */
    @DbField("last_sp_time")
    private Date lastSpTime;
    
    /** 犯人编号 */
    @DbField("fr_no")
    private String frNo;
    
    /** 犯人姓名 */
    @DbField("fr_name")
    private String frName;
    
    /** 监区编号 */
    @DbField("jq_no")
    private String jqNo;
    
    /** 监区名称 */
    @DbField("jq_name")
    private String jqName;
    
    /** 亲属信息 */
    @DbField("qs_info")
    private String qsInfo;
    
    /** 提交时间 */
    @DbField("tj_time")
    private Date tjTime;
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getSetNo() {
        return this.setNo;
    }

    public void setSetNo(String setNo) {
        this.setNo = setNo;
    }
    public String getSetName() {
        return this.setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }
    public Integer getMaxNum() {
        return this.maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }
    public Long getHjid() {
        return this.hjid;
    }

    public void setHjid(Long hjid) {
        this.hjid = hjid;
    }
    
    public Integer getQsId() {
		return qsId;
	}

	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public Integer getSpeedProgress() {
        return this.speedProgress;
    }

    public void setSpeedProgress(Integer speedProgress) {
        this.speedProgress = speedProgress;
    }

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLastSpTime() {
		return lastSpTime;
	}

	public void setLastSpTime(Date lastSpTime) {
		this.lastSpTime = lastSpTime;
	}

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

	public String getJqNo() {
		return jqNo;
	}

	public void setJqNo(String jqNo) {
		this.jqNo = jqNo;
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

	public Date getTjTime() {
		return tjTime;
	}

	public void setTjTime(Date tjTime) {
		this.tjTime = tjTime;
	}
    
}
