package com.slxt.rs.form;

import org.apache.struts.action.ActionForm;

public class HjReportForm extends ActionForm{
	private String callTimeStart;
	private String callTimeEnd;
	private String jq;
	
	public String getJq() {
		return jq;
	}
	public void setJq(String jq) {
		this.jq = jq;
	}
	public String getCallTimeStart() {
		return callTimeStart;
	}
	public void setCallTimeStart(String callTimeStart) {
		this.callTimeStart = callTimeStart;
	}
	public String getCallTimeEnd() {
		return callTimeEnd;
	}
	public void setCallTimeEnd(String callTimeEnd) {
		this.callTimeEnd = callTimeEnd;
	}
	

}
