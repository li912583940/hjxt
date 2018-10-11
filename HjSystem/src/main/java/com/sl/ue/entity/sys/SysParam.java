package com.sl.ue.entity.sys;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

@Table("SYS_PARAM")
public class SysParam implements java.io.Serializable{

	/**  */
	private static final long serialVersionUID = 6273385812501871294L;

	@Id
	@DbField("WebID")
	private Integer webId;
	@DbField("Param_Name")
	private String paramName;
	@DbField("Param_Data1")
	private String paramData1;
	@DbField("Param_Data2")
	private String paramData2;
	@DbField("Param_Data3")
	private String paramData3;
	@DbField("Param_Data4")
	private String paramData4;
	@DbField("Param_Data5")
	private String paramData5;
	
	
	
	
	public Integer getWebId() {
		return webId;
	}
	public void setWebId(Integer webId) {
		this.webId = webId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamData1() {
		return paramData1;
	}
	public void setParamData1(String paramData1) {
		this.paramData1 = paramData1;
	}
	public String getParamData2() {
		return paramData2;
	}
	public void setParamData2(String paramData2) {
		this.paramData2 = paramData2;
	}
	public String getParamData3() {
		return paramData3;
	}
	public void setParamData3(String paramData3) {
		this.paramData3 = paramData3;
	}
	public String getParamData4() {
		return paramData4;
	}
	public void setParamData4(String paramData4) {
		this.paramData4 = paramData4;
	}
	public String getParamData5() {
		return paramData5;
	}
	public void setParamData5(String paramData5) {
		this.paramData5 = paramData5;
	}
	
	
	
}
