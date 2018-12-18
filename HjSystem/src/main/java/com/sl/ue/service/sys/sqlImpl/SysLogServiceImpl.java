package com.sl.ue.service.sys.sqlImpl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysLogService;

@Service("sysLogSQL")
public class SysLogServiceImpl extends BaseSqlImpl<SysLogVO> implements SysLogService{

	public Map<String, Object> findPojoLeft(SysLogVO model, Integer pageSize, Integer pageNum){
		StringBuffer leftJoinWhere = new StringBuffer();
    	if(StringUtils.isNotBlank(model.getCallTimeStart())){ // 开始时间
    		leftJoinWhere.append(" AND a.LOG_TIME>='"+ model.getCallTimeStart() + "' ");
    		model.setCallTimeStart(null);
    	}
    	if(StringUtils.isNotBlank(model.getCallTimeEnd())){ // 结束时间
    		leftJoinWhere.append(" AND a.LOG_TIME<='"+ model.getCallTimeEnd() + "' ");
    		model.setCallTimeEnd(null);
    	}
    	if(StringUtils.isNotBlank(model.getUserNo())){
    		leftJoinWhere.append(" AND a.USER_NO LIKE '%"+model.getUserNo()+"%' ");
    		model.setUserNo(null);
    	}
    	if(StringUtils.isNotBlank(model.getUserName())){
    		leftJoinWhere.append(" AND a.USER_NAME LIKE '%"+model.getUserName()+"%' ");
    		model.setUserName(null);
    	}
    	if(StringUtils.isNotBlank(model.getUserIp())){
    		leftJoinWhere.append(" AND a.USER_IP LIKE '%"+model.getUserIp()+"%' ");
    		model.setUserIp(null);
    	}
    	model.setLeftJoinWhere(leftJoinWhere.toString());
    	
    	Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
    	return map;
	}
}
