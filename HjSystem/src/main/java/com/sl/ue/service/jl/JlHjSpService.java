package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjSpService extends BaseService<JlHjSpVO>{

	 public Map<String, Object> findPojoLeft(JlHjSpVO model, Integer pageSize, Integer pageNum);
}