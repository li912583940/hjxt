package com.sl.ue.entity;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("ERROR_INFO_WEBSERVICE")
public class ErrorInfoWebService implements java.io.Serializable {

	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月12日
	 */
	private static final long serialVersionUID = -596974046009476078L;
	
	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Action_Name")
	private String actionName;
	@DbField("Error_Info")
	private String errorInfo;
	@DbField("Create_Time")
	private Date createTime;

	

	public Integer getWebId() {
		return this.webId;
	}

	public void setWebId(Integer webId) {
		this.webId = webId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	
}