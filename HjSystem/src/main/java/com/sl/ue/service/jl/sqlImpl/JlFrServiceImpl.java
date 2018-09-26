package com.sl.ue.service.jl.sqlImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;

@Service("jlFrSQL")
public class JlFrServiceImpl extends BaseSqlImpl<JlFrVO> implements JlFrService{

	@Override
	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum) {
		StringBuffer field = new StringBuffer();
		field.append(",b.JQ_Name");
		
		StringBuffer table = new StringBuffer();
		table.append(" left join JL_JQ b ON a.JQ=b.JQ_No");
		model.setJoinField(field.toString());
		model.setJoinTable(table.toString());
		return this.findPojo(model, pageSize, pageNum);
	}


}
