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
	@DbField("User_Depart")
	private String userDepart;
	@DbField("User_Name")
	private String userName;
	@DbField("Group_No")
	private String groupNo;
	@DbField("Is_Super")
	private Integer isSuper;
	@DbField("Is_Sp")
	private Integer isSp;
	@DbField("Is_Bj")
	private Integer isBj;
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

	public String getUserDepart() {
		return userDepart;
	}

	public void setUserDepart(String userDepart) {
		this.userDepart = userDepart;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Integer getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}

	public Integer getIsSp() {
		return isSp;
	}

	public void setIsSp(Integer isSp) {
		this.isSp = isSp;
	}

	public Integer getIsBj() {
		return isBj;
	}

	public void setIsBj(Integer isBj) {
		this.isBj = isBj;
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
