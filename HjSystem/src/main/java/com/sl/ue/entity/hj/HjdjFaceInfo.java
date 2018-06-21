package com.sl.ue.entity.hj;

import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;


@Table("HJDJ_FACE_INFO")
public class HjdjFaceInfo implements java.io.Serializable {


	/**
	 * 说明 []
	 * @author L_晓天    @2018年4月12日
	 */
	private static final long serialVersionUID = -5123533827772285445L;
	@Id
	@DbField("FaceIndex")
	private Integer faceIndex;
	@DbField("EnrollFace_PC_IP")
	private String enrollFacePcIp;
	@DbField("EnrollFace_ID")
	private String enrollFaceId;
	
	public Integer getFaceIndex() {
		return faceIndex;
	}
	public void setFaceIndex(Integer faceIndex) {
		this.faceIndex = faceIndex;
	}
	public String getEnrollFacePcIp() {
		return enrollFacePcIp;
	}
	public void setEnrollFacePcIp(String enrollFacePcIp) {
		this.enrollFacePcIp = enrollFacePcIp;
	}
	public String getEnrollFaceId() {
		return enrollFaceId;
	}
	public void setEnrollFaceId(String enrollFaceId) {
		this.enrollFaceId = enrollFaceId;
	}

	

}