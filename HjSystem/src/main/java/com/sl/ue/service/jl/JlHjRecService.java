package com.sl.ue.service.jl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjRecService extends BaseService<JlHjRecVO>{

	public Map<String, Object> findPojoLeft(JlHjRecVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [获取当前用户在此次会见记录的注释]
	 * L_晓天  @2018年11月24日
	 */
	public String getZs(String callId);
	
	/**
	 * 说明 [添加当前用户的在会见记录中的注释]
	 * L_晓天  @2018年11月24日
	 */
	public String addRecordFlag(String callId, String writeTxt);
	
}