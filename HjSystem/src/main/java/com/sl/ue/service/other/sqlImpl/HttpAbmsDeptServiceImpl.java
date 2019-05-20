package com.sl.ue.service.other.sqlImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.jl.vo.JlHjDjQsVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.other.vo.HttpAbmsDeptVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjDjQsService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.other.HttpAbmsDeptService;
import com.sl.ue.util.Config;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.HttpUtil;
import com.sl.ue.util.http.Result;

@Service("httpAbmsDeptSQL")
public class HttpAbmsDeptServiceImpl extends BaseSqlImpl<HttpAbmsDeptVO> implements HttpAbmsDeptService{

	@Autowired
	private JlHjDjService jlHjDjSQL;
	@Autowired
	private JlHjDjQsService jlHjDjQsSQL;
	
	public String syncAbmsDept(){
		Result result = new Result();
		String url = "http://10.41.71.61/abms/external/queryDepInfo";
		
		String response = HttpUtil.post(url, "");
		if(response != null){
			JSONObject json = JSONObject.parseObject(response);
			if(json.getString("code").equals("0000")){ // 调用成功
				JSONArray array = json.getJSONArray("data");
				for(int i=0; i<array.size();i++){
					JSONObject j = array.getJSONObject(i);
					HttpAbmsDeptVO model = this.findOne(j.getString("depId"));
					if(model==null){
						model = new HttpAbmsDeptVO();
						model.setDepid(j.getString("depId"));
						model.setDepname(j.getString("depName"));
						this.add(model);
					}else{
						if(!j.getString("depName").equals(model.getDepname())){
							model.setDepname(j.getString("depName"));
							this.edit(model);
						}
					}
				}
			}else{ //调用失败
				String msg = "";
				if(json.getString("code").equals("0001")){
					msg = "参数不能为空";
				}else if(json.getString("code").equals("0099")){
					msg = "AB门系统异常";
				}
				result.error(Result.error_103, msg);
			}
		}else{
			result.error(Result.error_103, "接口调用失败");
		}
		return result.toResult();
	}
	
	public String httpToAbmsHjDj(Long hjid){
		Result result = new Result();
		if(hjid == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjDjVO model = jlHjDjSQL.findOne(hjid);
		if(model == null){
			result.error(Result.error_103, "查询不到相应的会见记录");
			return result.toResult();
		}
		
		JSONObject params = new JSONObject();
		JSONObject approvalJson = new JSONObject();
		String sApprovalJson="{";
		/** 审批单生效开始时间，时间格式：YYYYMMDDHHmmss ，注意必须在审批单生效期内才允许发卡 */
		approvalJson.put("startTime", DateUtil.getFormat(model.getDjTime(), "yyyyMMddHHmmss"));
		sApprovalJson += "\"startTime\":\""+DateUtil.getFormat(model.getDjTime(), "yyyyMMddHHmmss")+"\",";
		
		/** 审批单生效结束时间，时间格式：YYYYMMDDHHmmss */
		String endTime = DateUtil.getFormat(model.getDjTime(), "yyyyMMdd");
		endTime = endTime+"235959";
		approvalJson.put("endTime", endTime);
		sApprovalJson += "\"endTime\":\""+endTime+"\",";
		
		JlHjDjQsVO jlHjDjQs = new JlHjDjQsVO();
		jlHjDjQs.setHjId(hjid);
		List<JlHjDjQsVO> qsList = jlHjDjQsSQL.findList(jlHjDjQs);
		
		String abmsId = Config.getPropertiesValue("abmsId");
		/** 会见人员信息集合 */
		JSONArray guestInfoJson = new JSONArray();
		
		String sGuestInfoJson="[";
		int index =1;
		for(JlHjDjQsVO qs : qsList){
			JSONObject t = new JSONObject();
			String sMap = "{";
			
			t.put("guestNam", StringUtils.isNotBlank(qs.getQsName())?qs.getQsName():"空");  // 姓名
			sMap += "\"guestNam\":\""+(StringUtils.isNotBlank(qs.getQsName())?qs.getQsName():"空")+"\",";
			
			t.put("identityNo", StringUtils.isNotBlank(qs.getQsSfz())?qs.getQsSfz():"空"); // 身份证
			sMap += "\"identityNo\":\""+(StringUtils.isNotBlank(qs.getQsSfz())?qs.getQsSfz():"空")+"\",";
			
			t.put("sex", StringUtils.isNotBlank(qs.getXb())?qs.getXb():"空"); //性别
			sMap += "\"sex\":\""+(StringUtils.isNotBlank(qs.getXb())?qs.getXb():"空")+"\",";
			
			t.put("nation", qs.getQsZjlb()==1?"汉":"未知"); // 民族
			sMap += "\"nation\":\""+(qs.getQsZjlb()==1?"汉":"未知")+"\",";
			
			t.put("address", StringUtils.isNotBlank(qs.getDz())?qs.getDz():"空"); // 地址
			sMap += "\"address\":\""+(StringUtils.isNotBlank(qs.getDz())?qs.getDz():"空")+"\",";
			
			t.put("depId", StringUtils.isNotBlank(abmsId)?abmsId:"45,103"); // 陪同部门，多个陪同部门使用“,”符号隔开
			sMap += "\"depId\":\""+(StringUtils.isNotBlank(abmsId)?abmsId:"45,103")+"\"";
			
			sMap += "}";
			
			guestInfoJson.add(t);
			if(index==1){
				sGuestInfoJson+=sMap;
			}else{
				sGuestInfoJson+=","+sMap;
			}
			index++;
		}
		sGuestInfoJson+="]";
		
		sApprovalJson += "\"guestInfoJson\":"+sGuestInfoJson;
		sApprovalJson+="}";
		
		approvalJson.put("guestInfoJson", guestInfoJson);
		/** 入参Request数据定义 */
		params.put("approvalJson", approvalJson);
		String httpParams = "approvalJson="+sApprovalJson;
		
		//result.putJson("params", httpParams);
		
		String url = "http://10.41.71.61/abms/external/saveApprovalInfo";
		String response = HttpUtil.post2(url, httpParams);
		
		if(response != null){
			JSONObject json = JSONObject.parseObject(response);
			if(json.getString("code").equals("0000")){ // 调用成功
				
			}else{ //调用失败
				String msg = "";
				if(json.getString("code").equals("0099")){
					msg = "AB门系统参数解析异常！";
				}
				result.error(Result.error_103, msg);
			}
		}else{
			result.error(Result.error_103, "接口调用失败");
		}
		return result.toResult();
	}
}
