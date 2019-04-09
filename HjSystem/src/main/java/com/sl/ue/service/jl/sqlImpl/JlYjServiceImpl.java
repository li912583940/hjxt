package com.sl.ue.service.jl.sqlImpl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlYjVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlYjService;
import com.sl.ue.util.http.Result;

@Service("jlYjSQL")
public class JlYjServiceImpl extends BaseSqlImpl<JlYjVO> implements JlYjService{

	public String findPojoJoin(JlYjVO model, Integer pageSize, Integer pageNum){
		Result result = new Result();
		StringBuffer leftJoinField = new StringBuffer();
		leftJoinField.append(",b.JQ_Name AS jqName");
		
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN JL_JQ b ON a.JQ=b.JQ_NO");
		
		StringBuffer leftJoinWhere = new StringBuffer();
    	if(StringUtils.isNotBlank(model.getYjNo())){
    		String str = model.getYjNo();
    		leftJoinWhere.append(" AND a.YJ_No LIKE '%"+str+"%' ");
    		model.setYjNo(null);
    	}
    	if(StringUtils.isNotBlank(model.getYjNum())){
    		String str = model.getYjNum();
    		leftJoinWhere.append(" AND a.YJ_Num LIKE '%"+str+"%' ");
    		model.setYjNum(null);
    	}
    	if(StringUtils.isNotBlank(model.getYjName())){
    		String str = model.getYjName();
    		leftJoinWhere.append(" AND a.YJ_Name LIKE '%"+str+"%' ");
    		model.setYjName(null);
    	}
    	if(StringUtils.isNotBlank(model.getYjCard())){
    		String str = model.getYjCard();
    		leftJoinWhere.append(" AND a.YJ_Card LIKE '%"+str+"%' ");
    		model.setYjCard(null);
    	}
    	model.setLeftJoinField(leftJoinField.toString());
    	model.setLeftJoinTable(leftJoinTable.toString());
    	model.setLeftJoinWhere(leftJoinWhere.toString());
        Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
        result.putPojo(map);
        return result.toResult();
	}
}
