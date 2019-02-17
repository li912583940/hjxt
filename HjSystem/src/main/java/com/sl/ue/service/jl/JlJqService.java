package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.service.base.BaseService;

public interface JlJqService extends BaseService<JlJqVO>{

	public Map<String, Object> findPojoJoin(JlJqVO model, Integer pageSize, Integer pageNum);
	
	/**
	 * 说明 [获取当前监区选中的会见星期日]
	 * L_晓天  @2018年11月2日
	 */
	public String getCheckedWeek(String jqNo);
	
	/**
	 * 说明 [提交当前监区的会见星期日]
	 * L_晓天  @2018年11月2日
	 */
	public String addJqWeek(String jqNo, String weeks);

}