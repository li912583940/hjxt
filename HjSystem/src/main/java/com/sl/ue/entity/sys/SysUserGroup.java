package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_USER_GROUP")
public class SysUserGroup implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -1913118304670514119L;
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Group_No")
	private String groupNo;
	@DbField("Group_Name")
	private String groupName;
	@DbField("Is_Admin")
	private Integer isAdmin;
	@DbField("User_Depart")
	private String userDepart;
	@DbField("Is_SP_Depart")
	private Integer isSpDept;
	@DbField("SP_QX_JB")
	private Integer spQxJb;
//	private Integer isSuper;


	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserDepart() {
		return userDepart;
	}

	public void setUserDepart(String userDepart) {
		this.userDepart = userDepart;
	}

	public Integer getIsSpDept() {
		return isSpDept;
	}

	public void setIsSpDept(Integer isSpDept) {
		this.isSpDept = isSpDept;
	}

	public Integer getSpQxJb() {
		return spQxJb;
	}

	public void setSpQxJb(Integer spQxJb) {
		this.spQxJb = spQxJb;
	}

//	public Integer getIsSuper() {
//		return isSuper;
//	}
//
//	public void setIsSuper(Integer isSuper) {
//		this.isSuper = isSuper;
//	}
//	
	
	
}