package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.service.base.BaseService;

public interface JlJbService extends BaseService<JlJbVO>{

	public Map<String, Object> findPojoJoin(JlJbVO model, Integer pageSize, Integer pageNum);
}