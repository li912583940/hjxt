package com.sl.ue.service.sys;

import java.util.Map;

import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.service.base.BaseService;

public interface SysLogService extends BaseService<SysLogVO>{

	public Map<String, Object> findPojoLeft(SysLogVO model, Integer pageSize, Integer pageNum);
	
}