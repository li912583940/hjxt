package com.sl.ue.service.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjDjService extends BaseService<JlHjDjVO>{

	public Map<String, Object> findPojoJoin(JlHjDjVO model, Integer pageSize, Integer pageNum);
	
	public String addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjsm, // 会见说明
			Integer hjType, // 会见类型
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
}