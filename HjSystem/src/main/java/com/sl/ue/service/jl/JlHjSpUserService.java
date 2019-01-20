package com.sl.ue.service.jl;

import com.sl.ue.entity.jl.vo.JlHjSpUserVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjSpUserService extends BaseService<JlHjSpUserVO>{

	 public String findSpeedSpUser(String spSetNo, Integer speedProgress);
}