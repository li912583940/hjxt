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
	
	/**
	 * 说明 [获取录音评级]
	 * L_晓天  @2018年11月26日
	 */
	public String getRatingState(String callId);
	
	/**
	 * 说明 [修改录音评级]
	 * L_晓天  @2018年11月26日
	 */
	public String updateRatingState(Long webid, Integer recRatingState, String writeTxt);
	
	
	/**
	 * 说明 [导出excel]
	 * L_晓天  @2018年11月30日
	 */
	public void exportExcel(JlHjRecVO model, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 说明 [获取一个星期内每天会见总数]
	 * L_晓天  @2018年12月2日
	 */
	public String getWeekCount();
	
	/**
	 * 说明 [下载录音录像]
	 * @param webid
	 * @param request
	 * @param response
	 * L_晓天  @2019年4月9日
	 */
	public String downVideo(Long webid, HttpServletRequest request, HttpServletResponse response);
	
	public String downVideo1(Long webid, HttpServletRequest request, HttpServletResponse response);
	
	public void downAudio(Long webid, HttpServletRequest request, HttpServletResponse response);
	
	public void downTest(Long webid, HttpServletRequest request, HttpServletResponse response);
	
	public String getFileUrl(Long id);
	
	public String recAssessment(Long id);
}