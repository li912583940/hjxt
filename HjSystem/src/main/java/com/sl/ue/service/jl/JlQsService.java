package com.sl.ue.service.jl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.base.BaseService;

public interface JlQsService extends BaseService<JlQsVO>{

	public Map<String, Object> findPojoJoin(JlQsVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [导出excel]
	 * @作者 LXT @2018年9月30日
	 */
	public void exportExcel(JlQsVO model, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 说明 [导入excel]
	 * @param request
	 * @param response
	 * @return
	 * L_晓天  @2018年12月19日
	 */
	public String importExcel(HttpServletRequest request, HttpServletResponse response);
	
	public boolean qsExist(String frNo, String qsSfz);
	
	/**
	 * 说明 [上传亲属附件]
	 * @param request
	 * @param response
	 * @return
	 * L_晓天  @2019年2月19日
	 */
	public String uploadWord(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 说明 [导出excel]
	 * @作者 LXT @2018年9月30日
	 */
	public void wordDownload(JlQsVO model, HttpServletRequest request, HttpServletResponse response);
}