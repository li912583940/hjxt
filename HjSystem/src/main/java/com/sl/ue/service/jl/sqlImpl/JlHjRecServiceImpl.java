package com.sl.ue.service.jl.sqlImpl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjRecService;

@Service("jlHjRecSQL")
public class JlHjRecServiceImpl extends BaseSqlImpl<JlHjRecVO> implements JlHjRecService{

	@Override
	public Map<String, Object> findPojoLeft(JlHjRecVO model, Integer pageSize, Integer pageNum) {
		StringBuffer leftJoinWhere = new StringBuffer();
    	if(StringUtils.isNotBlank(model.getCallTimeStart())){ // 开始时间
    		leftJoinWhere.append(" AND a.Call_Time_Start>='"+ model.getCallTimeStart() + "' ");
    		model.setCallTimeStart(null);
    	}
    	if(StringUtils.isNotBlank(model.getCallTimeEnd())){ // 结束时间
    		leftJoinWhere.append(" AND a.Call_Time_Start<='"+ model.getCallTimeEnd() + "' ");
    		model.setCallTimeEnd(null);
    	}
    	if(StringUtils.isNotBlank(model.getFrName())){
    		leftJoinWhere.append(" AND a.FR_Name LIKE '%"+model.getFrName()+"%' ");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(model.getQsName())){
    		
    	}
    	model.setLeftJoinWhere(leftJoinWhere.toString());
    	Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		return map;
	}

}
