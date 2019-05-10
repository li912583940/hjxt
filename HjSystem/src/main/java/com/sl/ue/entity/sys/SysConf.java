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

    /** 会见登记验证总开关。默认：0； 0：开启会见登记验证总开关，1：关闭会见登记验证总开关 */
    @DbField("hjdj_switch")
    private Integer hjdjSwitch;
    
    /** 默认0; 会见通知。 0：登记完自动发起。1：身份验证成功后发起 */
    @DbField("hj_notice")
    private Integer hjNotice;
    
    /** 默认0; 0:关闭系统自动打印小票，1：登记完成打印小票 */
    @DbField("print_xp")
    private Integer printXp;
    
    /** 默认0; 0:修改登记不打印小票，1：修改登记自动打印小票 */
    @DbField("edit_dj_xp")
    private Integer editDjXp;
    
    /** 打印格式： 默认0;  0:A4打印，1：热敏打印 */
    @DbField("print_format")
    private Integer printFormat;
    
    /** 默认0; 0：登记完成不自动分配座位。1：登记完成自动分配座位 */
    @DbField("fp_zw")
    private Integer fpZw;
    
    /** 默认0; 当会见登记自动分配座位自动分配座位时，即fp_zw=1。0:座位不够，不让登记。1：座位不够，可以继续提交登记 */
    @DbField("save_hjdj")
    private Integer saveHjdj;
    
    /** 会见类型不记录罪犯会见次数,会见类型id,逗号分隔  */
    @DbField("hj_types")
    private String hjTypes;
    
    /** 是否开启AB门数据同步 0:不开启，1：开启  默认：0 */
    @DbField("abms_http")
    private Integer abmsHttp;
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getHjdjSwitch() {
		return hjdjSwitch;
	}

	public void setHjdjSwitch(Integer hjdjSwitch) {
		this.hjdjSwitch = hjdjSwitch;
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

	public Integer getEditDjXp() {
		return editDjXp;
	}

	public void setEditDjXp(Integer editDjXp) {
		this.editDjXp = editDjXp;
	}

	public Integer getPrintFormat() {
		return printFormat;
	}

	public void setPrintFormat(Integer printFormat) {
		this.printFormat = printFormat;
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

	public String getHjTypes() {
		return hjTypes;
	}

	public void setHjTypes(String hjTypes) {
		this.hjTypes = hjTypes;
	}

	public Integer getAbmsHttp() {
		return abmsHttp;
	}

	public void setAbmsHttp(Integer abmsHttp) {
		this.abmsHttp = abmsHttp;
	}
    
	
}
