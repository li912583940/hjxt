package com.sl.ue.service.jl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.service.base.BaseService;

public interface JlFrService extends BaseService<JlFrVO>{

	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [导出excel]
	 * @作者 LXT @2018年9月30日
	 */
	public void exportExcel(JlFrVO model, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 说明 [导入excel]
	 * @param request
	 * @param response
	 * @return
	 * L_晓天  @2018年12月19日
	 */
	public String importExcel(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 说明 [根据犯人编号查看当前罪犯是否存在]
	 * @param frNo
	 * @return
	 * L_晓天  @2019年1月20日
	 */
	public boolean frExist(String frNo);
}