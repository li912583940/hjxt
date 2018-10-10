package com.sl.ue.entity.other;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Table;

@Table("Dept")
public class Dept  implements java.io.Serializable {

	/**  */
	private static final long serialVersionUID = -6803186411018016772L;
	
	@DbField("Dept_Name")
	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
