package com.sl.ue.service.other;

import com.sl.ue.entity.other.vo.HttpAbmsDeptVO;
import com.sl.ue.service.base.BaseService;

public interface HttpAbmsDeptService extends BaseService<HttpAbmsDeptVO>{

	public String syncAbmsDept();
	
	public String httpToAbmsHjDj(Long hjid);
}