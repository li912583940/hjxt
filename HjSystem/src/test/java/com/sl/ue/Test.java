package com.sl.ue;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.jl.vo.JlHjDjQsVO;
import com.sl.ue.util.Config;
import com.sl.ue.util.DateUtil;

public class Test {

	public static void main(String[] args){
		Date date = new Date();
		JSONObject approvalJson = new JSONObject();
		/** 审批单生效开始时间，时间格式：YYYYMMDDHHmmss ，注意必须在审批单生效期内才允许发卡 */
		approvalJson.put("startTime", DateUtil.getFormat(date, "yyyyMMddHHmmss"));
		
		/** 审批单生效结束时间，时间格式：YYYYMMDDHHmmss */
		String endTime = DateUtil.getFormat(date, "yyyyMMdd");
		endTime = endTime+"235959";
		approvalJson.put("endTime", endTime);
		
		String abmsId = Config.getPropertiesValue("abmsId");
		System.out.println("abmsId="+abmsId);
		/** 会见人员信息集合 */
		JSONArray guestInfoJson = new JSONArray();
		for(int i=0;i<2;i++){
			JSONObject t = new JSONObject();
			t.put("guestNam", "空");  // 姓名
			t.put("identityNo", "空"); // 身份证
			t.put("sex", "空"); //性别
			t.put("nation", "未知"); // 民族
			t.put("address", "空"); // 地址
			t.put("depId", "45,103"); // 陪同部门，多个陪同部门使用“,”符号隔开
			guestInfoJson.add(t);
		}
		
		approvalJson.put("guestInfoJson", guestInfoJson);
		/** 入参Request数据定义 */
		//params.put("approvalJson", approvalJson.toJSONString());
		
		System.out.println(approvalJson.toJSONString());
	}
}
