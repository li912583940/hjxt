package com.sl.ue.service.sys.sqlImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjMonVO;
import com.sl.ue.entity.jl.vo.JlHjMonitorTimeAddVO;
import com.sl.ue.entity.jl.vo.JlMonitorVocVO;
import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.entity.sys.vo.SysHjVideoVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjMonService;
import com.sl.ue.service.jl.JlHjMonitorTimeAddService;
import com.sl.ue.service.jl.JlMonitorVocService;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.service.sys.SysHjVideoService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@Service("sysHjLineSQL")
public class SysHjLineServiceImpl extends BaseSqlImpl<SysHjLineVO> implements SysHjLineService{

	@Autowired
	private JlHjMonitorTimeAddService jlHjMonitorTimeAddSQL;
	@Autowired
	private JlHjMonService jlHjMonSQL;
	@Autowired
	private JlHjDjService jlHjDjSQL;
	@Autowired
	private JlMonitorVocService jlMonitorVocSQL;
	@Autowired
	private SysHjVideoService sysHjVideoSQL;
	
	@Override
	public Map<String, Object> findPojoMonitor(Integer pageSize, Integer pageNum) {
		SysUserVO sysUser = TokenUser.getUser();
		
		StringBuffer leftJoinField = new StringBuffer();
		leftJoinField.append(",b.QS_Info1 AS qsInfo1");
		leftJoinField.append(",b.QS_Info2 AS qsInfo2");
		leftJoinField.append(",b.QS_Info3 AS qsInfo3");
		leftJoinField.append(",b.QS_Info4 AS qsInfo4");
		leftJoinField.append(",b.QS_Info5 AS qsInfo5");
		leftJoinField.append(",b.QS_Info6 AS qsInfo6");
		leftJoinField.append(",b.QS_Info7 AS qsInfo7");
		leftJoinField.append(",b.QS_Info8 AS qsInfo8");
		leftJoinField.append(",b.QS_Info9 AS qsInfo9");
		
		leftJoinField.append(",c.FR_No AS frNo");
		leftJoinField.append(",c.Info_ZM AS infoZm");
		
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ b ON a.HJID=b.HJID");
		leftJoinTable.append(" LEFT JOIN JL_FR c ON b.FR_No=c.FR_No");
		
		
		SysHjLineVO sysHjLine = new SysHjLineVO();
		sysHjLine.setLeftJoinField(leftJoinField.toString());
		sysHjLine.setLeftJoinTable(leftJoinTable.toString());
		Map<String, Object> map = this.findPojo(sysHjLine, pageSize, pageNum, "ASC");
		List<SysHjLineVO> list = (List<SysHjLineVO>) map.get("list");
		for(SysHjLineVO t : list){
			String qsInfos = "";
			if(StringUtils.isNotBlank(t.getQsInfo1())){
				qsInfos=t.getQsInfo1();
			}
			if(StringUtils.isNotBlank(t.getQsInfo2())){
				qsInfos+=","+t.getQsInfo2();
			}
			if(StringUtils.isNotBlank(t.getQsInfo3())){
				qsInfos+=","+t.getQsInfo3();
			}
			if(StringUtils.isNotBlank(t.getQsInfo4())){
				qsInfos+=","+t.getQsInfo4();
			}
			if(StringUtils.isNotBlank(t.getQsInfo5())){
				qsInfos+=","+t.getQsInfo5();
			}
			if(StringUtils.isNotBlank(t.getQsInfo6())){
				qsInfos+=","+t.getQsInfo6();
			}
			if(StringUtils.isNotBlank(t.getQsInfo7())){
				qsInfos+=","+t.getQsInfo7();
			}
			if(StringUtils.isNotBlank(t.getQsInfo8())){
				qsInfos+=","+t.getQsInfo8();
			}
			if(StringUtils.isNotBlank(t.getQsInfo9())){
				qsInfos+=","+t.getQsInfo9();
			}
			t.setQsInfos(qsInfos);
//			JlHjMonVO jlHjMon = new JlHjMonVO();
//			jlHjMon.setCallId(t.getMonitorCallid());
//			jlHjMon.setUserNo(sysUser.getUserNo());
//			List<JlHjMonVO> jlHjMonList = jlHjMonSQL.findList(jlHjMon);
//			if(jlHjMonList.size()>0){
//				t.setWriteTxt(jlHjMonList.get(0).getWriteTxt());
//			}
//			if(StringUtils.isNotBlank(t.getMonitorQs())){
//				String[] ss = t.getMonitorQs().split(";");
//				List<String> ls = new ArrayList<String>();
//				for(int i=0;i<ss.length;i++){
//					if(i==3){
//						break;
//					}
//					ls.add(ss[i]);
//				}
//				t.setQsList(ls);
//			}
		}
		return map;
	}

	
	public String updateSJ(Integer webid, Integer timeUp){
		Result result = new Result();
		if(webid == null){
			result.error(Result.error_102, "webid为NULL");
			return result.toResult();
		}
		SysHjLineVO sysHjLine = this.findOne(webid);
		
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
//				JlHjMonitorTimeAddVO jlHjMonitorTime = jlHjMonitorTimeAddSQL.findOne(jlHjMonitorTimeAdd.getWebid());
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
	
	public String updateYJ(Integer webid, String monitorCallid, Long hjid, Integer state){
		Result result = new Result();
		if(webid == null){
			result.error(Result.error_102, "webid为NULL");
			return result.toResult();
		}
		SysHjLineVO sysHjLine = this.findOne(webid);
		
		if(sysHjLine == null){
			result.error(Result.error_103, "查询不到此记录");
			return result.toResult();
		}
		SysUserVO user = TokenUser.getUser();
		JlHjMonVO jlHjMon = new JlHjMonVO();
		jlHjMon.setCallId(monitorCallid);
		jlHjMon.setHjid(hjid);
		jlHjMon.setUserNo(user.getUserNo());
		jlHjMon.setUserName(user.getUserName());
		jlHjMon.setCreateTime(new Date());
		if(state==1){
			jlHjMon.setType(1);
			
			sysHjLine.setMonitorYj(user.getUserName());
		}else if(state==2){
			jlHjMon.setType(2);
			
			sysHjLine.setMonitorYj("");
		}else if(state==3){
			jlHjMon.setType(3);
			
			sysHjLine.setMonitorYj("");
		}
		jlHjMonSQL.add(jlHjMon);
		this.edit(sysHjLine);
		return result.toResult();
	}
	
	public String getZs(String monitorCallid){
		Result result = new Result();
		if(StringUtils.isBlank(monitorCallid)){
			return result.toResult();
		}
		SysUserVO sysUser = TokenUser.getUser();
		JlHjMonVO jlHjMon = new JlHjMonVO();
		jlHjMon.setCallId(monitorCallid);
		jlHjMon.setUserNo(sysUser.getUserNo());
		List<JlHjMonVO> jlHjMonList = jlHjMonSQL.findList(jlHjMon);
		if(jlHjMonList.size()>0){
			jlHjMon = jlHjMonList.get(0);
		}
		result.putJson(jlHjMon);
		return result.toResult();
	}
	
	public String addMonitorFlag(String monitorCallid, Long hjid, String writeTxt){
		Result result = new Result();
		if(StringUtils.isBlank(monitorCallid)){
			result.error(Result.error_102);
			return result.toResult();
		}
		SysUserVO sysUser = TokenUser.getUser();
		JlHjMonVO jlHjMon = new JlHjMonVO();
		jlHjMon.setCallId(monitorCallid);
		jlHjMon.setUserNo(sysUser.getUserNo());
		List<JlHjMonVO> jlHjMonList = jlHjMonSQL.findList(jlHjMon);
		if(jlHjMonList.size()>0){
			jlHjMon = jlHjMonList.get(0);
			jlHjMon.setWriteTxt(writeTxt);
			jlHjMonSQL.edit(jlHjMon);
		}else{
			jlHjMon.setUserName(sysUser.getUserName());
			jlHjMon.setHjid(hjid);
			jlHjMon.setType(5);
			jlHjMon.setWriteTxt(writeTxt);
			jlHjMon.setCreateTime(new Date());
			jlHjMonSQL.add(jlHjMon);
		}
		return result.toResult();
	}
	
	public String qieduanHj(Long hjid){
		Result result = new Result();
		if(hjid == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjDjVO jlHjDj = jlHjDjSQL.findOne(hjid);
		if(jlHjDj != null){
			jlHjDj.setState(4);
			jlHjDjSQL.edit(jlHjDj);
		}
		return result.toResult();
	}
	
	public String getSurplusZwCount(){
		Result result = new Result();
		SysHjLineVO sysHjLine0 = new SysHjLineVO();
		sysHjLine0.setState(1);
		sysHjLine0.setHjstate(0);
		sysHjLine0.setLineType(0);
		sysHjLine0.setLeftJoinWhere(" AND hjid is null");
		Integer yanjian = this.count(sysHjLine0);
		result.putJson("yanjian", yanjian);
		
		SysHjLineVO sysHjLine1 = new SysHjLineVO();
		sysHjLine1.setState(1);
		sysHjLine1.setHjstate(0);
		sysHjLine1.setLineType(1);
		sysHjLine1.setLeftJoinWhere(" AND hjid is null");
		Integer kuanjian = this.count(sysHjLine1);
		result.putJson("kuanjian", kuanjian);
		return result.toResult();
	}
	
	public String requestCH(String monitorCallid, Long hjid, Integer vocId){
		Result result = new Result();
		JlMonitorVocVO jlMonitorVoc = new JlMonitorVocVO();
		jlMonitorVoc.setVocId(vocId);
		List<JlMonitorVocVO> vocList = jlMonitorVocSQL.findList(jlMonitorVoc);
		if(vocList.size()==0){
			result.error(Result.error_103, "查询不到插话记录");
			return result.toResult();
		}
		jlMonitorVoc=vocList.get(0);
		SysUserVO user = TokenUser.getUser();
		JlHjMonVO jlHjMon = new JlHjMonVO();
		jlHjMon.setCallId(monitorCallid);
		jlHjMon.setHjid(hjid);
		jlHjMon.setType(4);
		jlHjMon.setWriteTxt(jlMonitorVoc.getVocInfo());
		jlHjMon.setUserNo(user.getUserNo());
		jlHjMon.setUserName(user.getUserName());
		jlHjMon.setCreateTime(new Date());
		jlHjMonSQL.add(jlHjMon);
		return result.toResult();
	}
	
	public String getSpMonitor(Integer id){
		Result result = new Result();
		SysHjLineVO sysHjLine = this.findOne(id);
		result.putJson("sysHjLine", sysHjLine);
		SysHjVideoVO sysHjVideo = sysHjVideoSQL.findOne(sysHjLine.getVideochan1Server());
		result.putJson("sysHjVideo", sysHjVideo);
		return result.toResult();
	}
}
