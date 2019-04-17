package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_conf")
public class SysConf implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**  */
    @Id
    @DbField("id")
    private Integer id;

    /** 默认0; 会见通知。 0：登记完自动发起。1：身份验证成功后发起 */
    @DbField("hj_notice")
    private Integer hjNotice;
    
    /** 默认0; 0:关闭系统自动打印小票，1：登记完成打印小票 */
    @DbField("print_xp")
    private Integer printXp;
    
    /** 默认0; 0：登记完成不自动分配座位。1：登记完成自动分配座位 */
    @DbField("fp_zw")
    private Integer fpZw;
    
    /** 默认0; 当会见登记自动分配座位自动分配座位时，即fp_zw=1。0:座位不够，不让登记。1：座位不够，可以继续提交登记 */
    @DbField("save_hjdj")
    private Integer saveHjdj;
    
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getHjNotice() {
        return this.hjNotice;
    }

    public void setHjNotice(Integer hjNotice) {
        this.hjNotice = hjNotice;
    }

	public Integer getPrintXp() {
		return printXp;
	}

	public void setPrintXp(Integer printXp) {
		this.printXp = printXp;
	}

	public Integer getFpZw() {
		return fpZw;
	}

	public void setFpZw(Integer fpZw) {
		this.fpZw = fpZw;
	}

	public Integer getSaveHjdj() {
		return saveHjdj;
	}

	public void setSaveHjdj(Integer saveHjdj) {
		this.saveHjdj = saveHjdj;
	}
    
}
