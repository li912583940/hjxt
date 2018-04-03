package com.sl.ue.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sl.ue.util.Table;

@Table("sys_user")
public class User  implements RowMapper<User>,Serializable{

	/**
	 * 说明 []
	 * L_晓天  @2018年4月3日
	 */
	private static final long serialVersionUID = 1L;
	private Integer webid;
	private String userNo;
	private String userPwd;
	private String userDepart;
	private String userName;
	private String groupNo;
	private Integer isSuper;
	private Integer isSp;
	private Integer isBj;
	
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();
		user.setWebid(rs.getInt("webid"));
		user.setUserNo(rs.getString("user_no"));
		user.setUserPwd(rs.getString("user_pwd"));
		user.setUserDepart(rs.getString("user_depart"));
		user.setUserName(rs.getString("group_no"));
		user.setIsSuper(rs.getInt("is_super"));
		user.setIsSp(rs.getInt("is_sp"));
		user.setIsBj(rs.getInt("is_bj"));
		return user;
	}

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
