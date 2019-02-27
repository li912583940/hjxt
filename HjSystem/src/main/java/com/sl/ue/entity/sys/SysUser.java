package com.sl.ue.entity.sys;

import java.io.Serializable;
import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 []
 * L_晓天  @2018年10月10日
 */
@Table("SYS_USER")
public class SysUser  implements Serializable{
//public class User  implements RowMapper<User>,Serializable{

	/** */
	private static final long serialVersionUID = 8693624485480715358L;
	
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("User_No")
	private String userNo;
	@DbField("User_Pwd")
	private String userPwd;
	@DbField("dept_id")
	private Integer deptId;
	@DbField("dept_name")
	private String deptName;
	@DbField("User_Name")
	private String userName;
	@DbField("Is_Super")
	private Integer isSuper;
	@DbField("token")
	private String token;
	@DbField("token_time")
	private Date tokenTime;

	public Integer getWebId() {
		return webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(Date tokenTime) {
		this.tokenTime = tokenTime;
	}
	

	
	
	
}
