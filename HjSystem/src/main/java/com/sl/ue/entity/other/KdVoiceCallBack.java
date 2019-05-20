package com.sl.ue.entity.other;

public class KdVoiceCallBack implements java.io.Serializable{

	/** 序列化 */
    private static final long serialVersionUID = 1L;
    
	private String id;
	
	private String time;
	
	private String pgs;
	
	private String sessionId;
	
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPgs() {
		return pgs;
	}

	public void setPgs(String pgs) {
		this.pgs = pgs;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
