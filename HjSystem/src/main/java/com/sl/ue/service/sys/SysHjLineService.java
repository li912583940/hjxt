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
	public String updateSJ(Integer webId, Integer timeUp);
	
	/**
	 * 说明 [更新监听警察信息]
	 * @param webId
	 * @return
	 * L_晓天  @2018年12月28日
	 */
	public String updateYJ(Integer webId, Integer state);
	
	/**
	 * 说明 [获取当前用户在此次通话的注释]
	 * L_晓天  @2018年11月21日
	 */
	public String getZs(String monitorCallid);
	
	/**
	 * 说明 [监听注释]
	 * L_晓天  @2018年11月21日
	 */
	public String addMonitorFlag(String monitorCallid, String writeTxt);
	
	/**
	 * 说明 [切断]
	 * L_晓天  @2018年11月22日
	 */
	public String qieduanHj(Long hjid);
}