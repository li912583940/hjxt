package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjRecService extends BaseService<JlHjRecVO>{

	public Map<String, Object> findPojoLeft(JlHjRecVO model, Integer pageSize, Integer pageNum);
}