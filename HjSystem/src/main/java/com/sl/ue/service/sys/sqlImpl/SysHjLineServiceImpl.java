package com.sl.ue.service.sys.sqlImpl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.util.Constants;

@Service("sysHjLineSQL")
public class SysHjLineServiceImpl extends BaseSqlImpl<SysHjLineVO> implements SysHjLineService{

	@Override
	public Map<String, Object> findPojoMonitor(Integer pageSize, Integer pageNum) {
		
		StringBuffer leftJoinField = new StringBuffer();
		leftJoinField.append(",b.IP AS ip");
		leftJoinField.append(",b.Port AS port");
		leftJoinField.append(",b.AudioPort AS audioPort");
		leftJoinField.append(",c.Write_Txt");
		
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN SYS_HJ_SERVER AS b ON a.JY=b.Server_Name");
		//leftJoinTable.append(" LEFT JOIN JL_HJ_MON AS c ON a.Monitor_CallID=c.Call_ID AND c.User_No='"+Constants.sysUser.getUserNo()+"'");
		leftJoinTable.append(" LEFT JOIN JL_HJ_MON AS c ON a.Monitor_CallID=c.Call_ID");
		
		StringBuffer leftJoinWhere = new StringBuffer();
		leftJoinWhere.append("");
		
		SysHjLineVO sysHjLine = new SysHjLineVO();
		sysHjLine.setLeftJoinField(leftJoinField.toString());
		sysHjLine.setLeftJoinTable(leftJoinTable.toString());
		Map<String, Object> map = this.findPojo(sysHjLine, pageSize, pageNum);
		return map;
	}

}
