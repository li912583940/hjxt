package com.sl.ue.service.jl;

import java.util.List;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.base.BaseService;
import com.sl.ue.util.http.Result;

public interface JlHjDjService extends BaseService<JlHjDjVO>{

	public Result addHjdj(
			String frNo, // 罪犯编号
			List<Integer> qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjsm, // 会见说明
			Integer hjType, // 会见类型
			Integer callNo //排队号
			);
}