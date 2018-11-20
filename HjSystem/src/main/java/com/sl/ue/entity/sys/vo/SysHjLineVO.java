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
