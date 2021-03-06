package com.sl.ue.service.sys;

import java.util.Map;

import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.service.base.BaseService;

public interface SysHjLineService extends BaseService<SysHjLineVO>{

	public Map<String, Object> findPojoMonitor(Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [修改时间]
	 * L_晓天  @2018年11月21日
	 */
	public String updateSJ(Integer webid, Integer timeUp);
	
	/**
	 * 说明 [更新监听警察信息]
	 * @param webid
	 * @return
	 * L_晓天  @2018年12月28日
	 */
	public String updateYJ(Integer webid, String monitorCallid, Long hjid, Integer state);
	
	/**
	 * 说明 [获取当前用户在此次通话的注释]
	 * L_晓天  @2018年11月21日
	 */
	public String getZs(String monitorCallid);
	
	/**
	 * 说明 [监听注释]
	 * L_晓天  @2018年11月21日
	 */
	public String addMonitorFlag(String monitorCallid, Long hjid, String writeTxt);
	
	/**
	 * 说明 [切断]
	 * L_晓天  @2018年11月22日
	 */
	public String qieduanHj(Long hjid, Integer lineId);
	
	/**
	 * 说明 [获取剩余座位数]
	 * @return
	 * L_晓天  @2019年4月17日
	 */
	public String getSurplusZwCount();
	
	/**
	 * 说明 [插话]
	 * @param monitorCallid
	 * @param hjid
	 * @param writeTxt
	 * @return
	 * L_晓天  @2019年4月24日
	 */
	public String requestCH(String monitorCallid, Long hjid, Integer vocId);
	
	public String getSpMonitor(Integer id);
}