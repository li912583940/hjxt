package com.sl.ue.entity.hj;

import com.sl.ue.util.anno.Table;

@Table("HJDJ_ACD_LOG")
public class HjdjAcdLog  implements java.io.Serializable {


     /** */
	private static final long serialVersionUID = -2498027446064673729L;
	private HjdjAcdLogId id;


    // Constructors

    /** default constructor */
    public HjdjAcdLog() {
    }

    
    /** full constructor */
    public HjdjAcdLog(HjdjAcdLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public HjdjAcdLogId getId() {
        return this.id;
    }
    
    public void setId(HjdjAcdLogId id) {
        this.id = id;
    }
   








}