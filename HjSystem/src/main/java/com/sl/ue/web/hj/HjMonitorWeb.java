package com.sl.ue.web.hj;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [会见监控]
 * L_晓天  @2018年10月7日
 */
@RestController
@RequestMapping("/hjMonitor")
public class HjMonitorWeb extends Result{

	@Autowired
	private SysHjLineService sysHjLineSQL;
	
	@RequestMapping("/findPojo")
	public String findPojo(Integer pageSize, Integer pageNum){
		Map<String, Object> map = sysHjLineSQL.findPojoMonitor(pageSize, pageNum);
		this.putPojo(map);
		return this.toResult();
	}
	
	/**
	 * 说明 [修改时间]
	 * L_晓天  @2018年11月20日
	 */
	@RequestMapping("/updateSJ")
	public String updateSJ(Integer webId, Integer timeUp){
		return sysHjLineSQL.updateSJ(webId, timeUp);
	}
	
	/**
	 * 说明 [更新监听警察信息]
	 * @param webId
	 * @param state 1:添加警察信息， 0:清除警察信息
	 * @return
	 * L_晓天  @2018年12月28日
	 */
	@RequestMapping("/updateYJ")
	public String updateYJ(Integer webId, Integer state){
		return sysHjLineSQL.updateYJ(webId, state);
	}
	
	/**
	 * 说明 [获取当前用户在此次通话的注释]
	 * L_晓天  @2018年11月21日
	 */
	@RequestMapping("/getZs")
	public String getZs(String monitorCallid){
		return sysHjLineSQL.getZs(monitorCallid);
	}
	/**
	 * 说明 [监听注释]
	 * L_晓天  @2018年11月20日
	 */
	@RequestMapping("/addMonitorFlag")
	public String addMonitorFlag(String monitorCallid, String writeTxt){
		return sysHjLineSQL.addMonitorFlag(monitorCallid, writeTxt);
	}
	
	/**
	 * 说明 [切断]
	 * L_晓天  @2018年11月22日
	 */
	@RequestMapping("/qieduanHj")
	public String qieduanHj(Long hjid){
		return sysHjLineSQL.qieduanHj(hjid);
	}
}
