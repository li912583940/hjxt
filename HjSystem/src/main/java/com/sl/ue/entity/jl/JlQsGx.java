package com.sl.ue.entity.jl;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("JL_QS_GX")
public class JlQsGx implements java.io.Serializable{

    /** 序列化 */
    private static final long serialVersionUID = 1L;
    
    @Id
    @DbField("id")
    private Integer id;
    /**  */
    @DbField("QS_GX")
    private String qsGx;

    
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQsGx() {
        return this.qsGx;
    }

    public void setQsGx(String qsGx) {
        this.qsGx = qsGx;
    }
}
