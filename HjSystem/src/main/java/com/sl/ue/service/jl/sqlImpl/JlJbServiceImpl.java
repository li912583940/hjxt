package com.sl.ue.service.jl.sqlImpl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlJbService;

@Service("jlJbSQL")
public class JlJbServiceImpl extends BaseSqlImpl<JlJbVO> implements JlJbService{

	@Override
	public Map<String, Object> findPojoJoin(JlJbVO model, Integer pageSize, Integer pageNum) {
		StringBuffer where = new StringBuffer();
		if(StringUtils.isNotBlank(model.getJbName())){
			String str = model.getJbName();
			where.append(" AND a.JB_Name LIKE '%"+str+"%' ");
			model.setJbName(null);
		}
		model.setLeftJoinWhere(where.toString());
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		return map;
	}

}
