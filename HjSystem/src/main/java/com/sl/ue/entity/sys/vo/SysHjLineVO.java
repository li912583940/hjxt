package com.sl.ue.entity.sys.vo;

import java.util.List;

import com.sl.ue.entity.sys.SysHjLine;

public class SysHjLineVO extends SysHjLine{

    /** 序列化 */
    private static final long serialVersionUID = 1L;
    
    private String ip; // 服务器ip

    private String port; // 服务器端口
    
    private String audioPort; // 服务器监听Socket端口号  默认值：9001    暂时未使用
    
    private String writeTxt; // 录音注释
    
    private String qsInfo1;
    private String qsInfo2;
    private String qsInfo3;
    private String qsInfo4;
    private String qsInfo5;
    private String qsInfo6;
    private String qsInfo7;
    private String qsInfo8;
    private String qsInfo9;
    private String qsInfos;
    private String frNo;
    private String infoZm;
    
    private List<String> qsList; //会见监控亲属数组
    
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getAudioPort() {
		return audioPort;
	}

	public void setAudioPort(String audioPort) {
		this.audioPort = audioPort;
	}
	
	public String getWriteTxt() {
		return writeTxt;
	}

	public void setWriteTxt(String writeTxt) {
		this.writeTxt = writeTxt;
	}
	
	public String getQsInfo1() {
		return qsInfo1;
	}

	public void setQsInfo1(String qsInfo1) {
		this.qsInfo1 = qsInfo1;
	}

	public String getQsInfo2() {
		return qsInfo2;
	}

	public void setQsInfo2(String qsInfo2) {
		this.qsInfo2 = qsInfo2;
	}

	public String getQsInfo3() {
		return qsInfo3;
	}

	public void setQsInfo3(String qsInfo3) {
		this.qsInfo3 = qsInfo3;
	}

	public String getQsInfo4() {
		return qsInfo4;
	}

	public void setQsInfo4(String qsInfo4) {
		this.qsInfo4 = qsInfo4;
	}

	public String getQsInfo5() {
		return qsInfo5;
	}

	public void setQsInfo5(String qsInfo5) {
		this.qsInfo5 = qsInfo5;
	}

	public String getQsInfo6() {
		return qsInfo6;
	}

	public void setQsInfo6(String qsInfo6) {
		this.qsInfo6 = qsInfo6;
	}

	public String getQsInfo7() {
		return qsInfo7;
	}

	public void setQsInfo7(String qsInfo7) {
		this.qsInfo7 = qsInfo7;
	}

	public String getQsInfo8() {
		return qsInfo8;
	}

	public void setQsInfo8(String qsInfo8) {
		this.qsInfo8 = qsInfo8;
	}

	public String getQsInfo9() {
		return qsInfo9;
	}

	public void setQsInfo9(String qsInfo9) {
		this.qsInfo9 = qsInfo9;
	}

	public String getQsInfos() {
		return qsInfos;
	}

	public void setQsInfos(String qsInfos) {
		this.qsInfos = qsInfos;
	}

	public String getFrNo() {
		return frNo;
	}

	public void setFrNo(String frNo) {
		this.frNo = frNo;
	}

	public String getInfoZm() {
		return infoZm;
	}

	public void setInfoZm(String infoZm) {
		this.infoZm = infoZm;
	}

	public List<String> getQsList() {
		return qsList;
	}

	public void setQsList(List<String> qsList) {
		this.qsList = qsList;
	}

	
	
    /*---------------------------  处理关联表  -----------------------------*/

    


	private String leftJoinField; // 关联表字段

    private String leftJoinTable; // 关联表

    private String leftJoinWhere; // 关联表条件

    public String getLeftJoinField() {
        return leftJoinField;
    }

    public void setLeftJoinField(String leftJoinField) {
        this.leftJoinField = leftJoinField;
    }

    public String getLeftJoinTable() {
        return leftJoinTable;
    }

    public void setLeftJoinTable(String leftJoinTable) {
        this.leftJoinTable = leftJoinTable;
    }

    public String getLeftJoinWhere() {
        return leftJoinWhere;
    }

    public void setLeftJoinWhere(String leftJoinWhere) {
        this.leftJoinWhere = leftJoinWhere;
    }


}
