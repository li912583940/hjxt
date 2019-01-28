package com.sl.ue.service.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjDjService extends BaseService<JlHjDjVO>{

	public Map<String, Object> findPojoJoin(JlHjDjVO model, Integer pageSize, Integer pageNum);
	
	public String addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjInfo, // 会见说明
			Integer hjType, // 会见类型
			Integer hjMode, //会见方式
			Integer callNo, //排队号
			Integer tpQsNum, //特批亲属个数
			Integer qzSp // 强制审批
			);
	
	/**
	 * 说明 [打印小票]
	 * L_晓天  @2018年10月17日
	 */
	public String printXp(Long id);
	
	/**
	 * 说明 [获取当前会见登记的亲属id集合]
	 * @param hjid
	 * @return
	 * L_晓天  @2018年12月27日
	 */
	public String getQsListByHjid(Long hjid);
	
	/**
	 * 说明 [修改会见登记]
	 * @param model
	 * @return
	 * L_晓天  @2018年12月27日
	 */
	public String editDj(Long hjid, Integer hjTime, Integer hjType, String hjInfo, String qsIds);
	
	/**
	 * 说明 [取消登记]
	 * L_晓天  @2018年10月17日
	 */
	public String cancelDj(Long id, String cancelInfo);
	
	
	/**
	 * 说明 [会见登记 根据亲属查询罪犯信息]
	 * L_晓天  @2018年11月8日
	 */
	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum, String qsName, String qsSfz);
	
	/**
	 * 说明 [会见签到 查询会见登记信息]
	 * L_晓天  @2018年11月8日
	 */
	public Map<String, Object> findPojoByHjSign(JlHjDjVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [分配座位]
	 * L_晓天  @2018年11月8日
	 */
	public String fpZw(Long hjId);
	
	/**
	 * 说明 [取消分配座位]
	 * L_晓天  @2018年11月8日
	 */
	public String qxFpZw(Long hjId, HttpServletRequest request);
	
	/**
	 * 说明 [获取剩余的座位号]
	 * L_晓天  @2018年11月9日
	 */
	public String getSurplusZw(Long hjId);
	
	/**
	 * 说明 [人工分配座位]
	 * L_晓天  @2018年11月9日
	 */
	public String rgFpZw(Long hjId, Integer lineNo, HttpServletRequest request);
	
	/**
	 * 说明 [授权才能使用电话]
	 * L_晓天  @2018年11月12日
	 */
	public String grantCall(Long hjId, HttpServletRequest request);
	
	/**
	 * 说明 []
	 * L_晓天  @2018年11月12日
	 */
	public String cancelGrantCall(Long hjId, HttpServletRequest request);
	
	/**
	 * 说明 [登记记录查询]
	 * @param model
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * L_晓天  @2018年12月25日
	 */
	public Map<String, Object> findPojoByLog(JlHjDjVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [登记记录导出]
	 * @param model
	 * @param request
	 * @param response
	 * L_晓天  @2018年12月28日
	 */
	public void exportExcelByLog(JlHjDjVO model, 
			HttpServletRequest request, HttpServletResponse response);
	

	/**
	 * 说明 [会见通知 查询会见登记信息]
	 * L_晓天  @2018年11月8日
	 */
	public Map<String, Object> findPojoByNotice(JlHjDjVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [会见通知修改通知状态]
	 * @param model
	 * @return
	 * L_晓天  @2018年12月28日
	 */
	public String editTz(JlHjDjVO model);
}