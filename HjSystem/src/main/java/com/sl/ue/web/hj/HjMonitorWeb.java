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
}
