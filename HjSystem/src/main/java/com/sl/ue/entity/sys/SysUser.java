package com.sl.ue.entity.sys;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("sys_user")
public class SysUser  implements Serializable{
//public class User  implements RowMapper<User>,Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@DbField("WebID")
	private Integer webid;
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
	
//	public User mapRow(ResultSet rs, int arg1) throws SQLException {
//		User user = new User();
//		user.setWebid(rs.getInt("webid"));
//		user.setUserNo(rs.getString("user_no"));
//		user.setUserPwd(rs.getString("user_pwd"));
//		user.setUserDepart(rs.getString("user_depart"));
//		user.setUserName(rs.getString("group_no"));
//		user.setIsSuper(rs.getInt("is_super"));
//		user.setIsSp(rs.getInt("is_sp"));
//		user.setIsBj(rs.getInt("is_bj"));
//		return user;
//	}

	public Integer getWebid() {
		return webid;
	}

	public void setWebid(Integer webid) {
		this.webid = webid;
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
	

	
	
	
}