package com.sl.ue.entity.jl;

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

    /** 审批设置的id */
    @DbField("set_id")
    private Integer setId;

    /** 审批设置的名称 */
    @DbField("set_name")
    private String setName;

    /** 审批次数 */
    @DbField("max_num")
    private Integer maxNum;

    /** 会见id */
    @DbField("hjid")
    private Long hjid;

    /** 审批进行状态。0：进行中，1：审批通过，2：审批失败，默认 0 */
    @DbField("state")
    private Integer state;

    /** 进度。对应级别 */
    @DbField("speed_progress")
    private Integer speedProgress;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSetId() {
        return this.setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
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
}
