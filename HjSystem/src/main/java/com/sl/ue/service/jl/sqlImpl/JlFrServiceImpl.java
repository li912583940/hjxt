package com.sl.ue.service.jl.sqlImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlQsService;

@Service("jlFrSQL")
public class JlFrServiceImpl extends BaseSqlImpl<JlFrVO> implements JlFrService{
	
	@Autowired
	private JlQsService jlQsSQL;
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
		
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		if(map.containsKey("list")) {
			List<JlFrVO> list = (List<JlFrVO>) map.get("list");
			for(JlFrVO jlFr : list) {
				JlQsVO jlQs = new JlQsVO();
				jlQs.setFrNo(jlFr.getFrNo());
				Integer qsNum= jlQsSQL.count(jlQs);
				jlFr.setQsNum(qsNum);
			}
		}
		return map;
	}


}
