package com.sl.ue.entity.jl;

import java.util.Date;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("JL_TRADE_CARD_INFO")
public class JlTradeCardInfo implements java.io.Serializable {


	/** */
	private static final long serialVersionUID = -8286389908494362535L;
	@Id
	@DbField("WebID")
	private Integer webid;
	@DbField("SJ")
	private Date sj;
	@DbField("CardNum")
	private String cardNum;
	@DbField("FR_No")
	private String frNo;
	@DbField("FR_Name")
	private String frName;
	@DbField("Qs_Name")
	private String qsName;
	@DbField("OP_Name")
	private String opName;
	@DbField("JQ_Name")
	private String jqName;
	@DbField("QS_ID")
	private Integer qsId;




	public Integer getWebid() {
		return webid;
	}

	public void setWebid(Integer webid) {
		this.webid = webid;
	}

	public Date getSj() {
		return this.sj;
	}

	public void setSj(Date sj) {
		this.sj = sj;
	}

	public String getCardNum() {
		return this.cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getFrNo() {
		return this.frNo;
	}

	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}

	public String getFrName() {
		return this.frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getQsName() {
		return this.qsName;
	}

	public void setQsName(String qsName) {
		this.qsName = qsName;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getJqName() {
		return this.jqName;
	}

	public void setJqName(String jqName) {
		this.jqName = jqName;
	}

	public Integer getQsId() {
		return this.qsId;
	}

	public void setQsId(Integer qsId) {
		this.qsId = qsId;
	}

}