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
		field.append(",c.JB_Name");
		
		
		StringBuffer table = new StringBuffer();
		table.append(" left join JL_JQ b ON a.JQ=b.JQ_No");
		table.append(" left join JL_JB c ON a.JB_No=c.JB_No");
		
		model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
		return this.findPojo(model, pageSize, pageNum);
	}


}
