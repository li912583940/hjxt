package com.sl.ue.service.sys;

import java.util.Map;

import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.service.base.BaseService;

public interface SysHjLineService extends BaseService<SysHjLineVO>{

	public Map<String, Object> findPojoMonitor(Integer pageSize, Integer pageNum);
}