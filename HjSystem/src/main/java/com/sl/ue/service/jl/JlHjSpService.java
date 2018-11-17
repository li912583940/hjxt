package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjSpService extends BaseService<JlHjSpVO>{

	
	public Map<String, Object> findPojoJoin(JlHjSpVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [查看详情]
	 * L_晓天  @2018年11月16日
	 */
	public String findDetails(Long spId);
}