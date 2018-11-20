package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjMonitorTimeAddVO;
import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjMonService;
import com.sl.ue.service.jl.JlHjMonitorTimeAddService;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.http.Result;

@Service("sysHjLineSQL")
public class SysHjLineServiceImpl extends BaseSqlImpl<SysHjLineVO> implements SysHjLineService{

	@Autowired
	private JlHjMonitorTimeAddService jlHjMonitorTimeAddSQL;
	@Autowired
	private JlHjMonService jlHjMonSQL;
	
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
		Map<String, Object> map = this.findPojo(sysHjLine, pageSize, pageNum, "ASC");
		List<SysHjLineVO> list = (List<SysHjLineVO>) map.get("list");
		for(SysHjLineVO t : list){
			if(StringUtils.isNotBlank(t.getMonitorQs())){
				String[] ss = t.getMonitorQs().split(";");
				List<String> ls = new ArrayList<String>();
				for(int i=0;i<ss.length;i++){
					if(i==3){
						break;
					}
					ls.add(ss[i]);
				}
				t.setQsList(ls);
			}
		}
		return map;
	}

	
	public String updateSJ(Integer webId, Integer timeUp){
		Result result = new Result();
		if(webId == null){
			result.error(Result.error_102, "webId为NULL");
			return result.toResult();
		}
		SysHjLineVO sysHjLine = this.findOne(webId);
		
		if(sysHjLine == null){
			result.error(Result.error_103, "查询不到此记录");
			return result.toResult();
		}
		JlHjMonitorTimeAddVO jlHjMonitorTimeAdd = new JlHjMonitorTimeAddVO();
		jlHjMonitorTimeAdd.setLineNo(sysHjLine.getLineNo());
		jlHjMonitorTimeAdd.setJy(sysHjLine.getJy());
		jlHjMonitorTimeAdd.setCallId(sysHjLine.getMonitorCallid());
		jlHjMonitorTimeAdd.setAddTime(timeUp*60);
		jlHjMonitorTimeAdd.setState(0);
		jlHjMonitorTimeAdd = jlHjMonitorTimeAddSQL.add(jlHjMonitorTimeAdd);
		
//		boolean flag=false;
//		Long begin=System.currentTimeMillis();
//		Long end=begin+10000;
//		Long begining=begin;
//		while(flag==false && begining<=end){
//			if(begin+1000==begining){
//				JlHjMonitorTimeAddVO jlHjMonitorTime = jlHjMonitorTimeAddSQL.findOne(jlHjMonitorTimeAdd.getWebId());
//				if(jlHjMonitorTime.getState()==1){
//					flag=true;
//				}
//			    begin=begining;
//			}
//			begining=System.currentTimeMillis();
//		}
//		if(flag==false){
//			result.error(Result.error_103, "修改通话时间失败，可能当前线路已经没有在通话");
//			return result.toResult();
//		}
		result.msg("时间已经修改，但是需要等待几秒查看最终是否成功");
		return result.toResult();
	}
	
	public String addMonitorFlag(String callId, String writeTxtLx){
		
	}
}
