package com.sl.ue.entity.hj;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("HJDJ_ACD_SHOW")
public class HjdjAcdShow implements java.io.Serializable {
	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月12日
	 */
	private static final long serialVersionUID = -4330093272287932510L;
	
	@Id
	@DbField("ShowID")
	private Long showId;
	@DbField("ShowWindowsIndex")
	private Integer showWindowsIndex;
	@DbField("ShowTime")
	private Date showTime;
	@DbField("ShowFlag")
	private Integer showFlag;
	@DbField("ShowText")
	private String showText;
	@DbField("ShowVoc")
	private String showVoc;
	
	public Long getShowId() {
		return showId;
	}
	public void setShowId(Long showId) {
		this.showId = showId;
	}
	public Integer getShowWindowsIndex() {
		return showWindowsIndex;
	}
	public void setShowWindowsIndex(Integer showWindowsIndex) {
		this.showWindowsIndex = showWindowsIndex;
	}
	public Date getShowTime() {
		return showTime;
	}
	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}
	public Integer getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}
	public String getShowText() {
		return showText;
	}
	public void setShowText(String showText) {
		this.showText = showText;
	}
	public String getShowVoc() {
		return showVoc;
	}
	public void setShowVoc(String showVoc) {
		this.showVoc = showVoc;
	}

	
}