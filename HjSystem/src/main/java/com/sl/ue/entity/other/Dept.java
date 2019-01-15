package com.sl.ue.entity.other;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("Dept")
public class Dept  implements java.io.Serializable {

	/**  */
	private static final long serialVersionUID = -6803186411018016772L;
	
	@Id
	@DbField("id")
	private Integer id;
	
	@DbField("Dept_Name")
	private String deptName;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
