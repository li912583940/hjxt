package com.sl.ue.service.jl;

import java.util.Map;

import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjSpService extends BaseService<JlHjSpVO>{

	 public Map<String, Object> findPojoLeft(JlHjSpVO model, Integer pageSize, Integer pageNum);
	 
	 public Map<String, Object> findPojoLog(JlHjSpVO model, Integer pageSize, Integer pageNum);
	 /**
	 * 说明 [提交会见审批结果]
	 * @param spId
	 * @param speedProgress当前进度
	 * @param explain
	 * @param state
	 * @return
	 * L_晓天  @2019年1月16日
	 */
	public String saveSpResult(Integer spId, Integer speedProgress, String explain, Integer state);
	
	public String findSpNotice();
}