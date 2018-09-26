package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.service.base.BaseService;

public interface JlFrService extends BaseService<JlFrVO>{

	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum);
}