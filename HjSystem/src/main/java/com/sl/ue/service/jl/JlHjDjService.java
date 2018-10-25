package com.sl.ue.service.jl;

import java.util.List;
import java.util.Map;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjDjService extends BaseService<JlHjDjVO>{

	public Map<String, Object> findPojoJoin(JlHjDjVO model, Integer pageSize, Integer pageNum);
	
	public String addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjsm, // 会见说明
			Integer hjType, // 会见类型
			Integer callNo, //排队号
			Integer tpQsNum, //特批亲属个数
			Integer qzSp // 强制审批
			);
	
	/**
	 * 说明 [打印小票]
	 * L_晓天  @2018年10月17日
	 */
	public String printXp(Long id);
	
	/**
	 * 说明 [取消登记]
	 * L_晓天  @2018年10月17日
	 */
	public String cancelDj(Long id, String cancelInfo);
	
	
	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum, String qsName, String qsSfz);
}