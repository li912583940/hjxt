package com.sl.ue.service.jl.sqlImpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@Service("jlHjDjSQL")
public class JlHjDjServiceImpl extends BaseSqlImpl<JlHjDjVO> implements JlHjDjService{
	
	@Autowired
	private JlFrService jlFrSQL;
	@Autowired
	private JlJqService jlJqSQL;
	@Autowired
	private JlQsService jlQsSQL;
	
	
	@Override
	public Result addHjdj(
			String frNo, // 罪犯编号
			List<Integer> qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjsm, // 会见说明
			Integer hjType, // 会见类型
			Integer callNo //排队号
			) {
		Result result = new Result();
		
		JlHjDjVO jlHjDj1 = new JlHjDjVO();
		jlHjDj1.setFrNo(frNo);
		String sqlLeftJoinWhere = " AND (a.state=0 or a.state=3)";
		jlHjDj1.setLeftJoinWhere(sqlLeftJoinWhere);
		List<JlHjDjVO> list1 = this.findList(jlHjDj1);
		if(list1.size()>0){
			result.error(Result.error_103, "服刑人员处于会见中");
			return result;
		}
		
		JlFrVO jlFr = new JlFrVO();  //当前罪犯信息
		jlFr.setFrNo(frNo);
		List<JlFrVO> jlFrList = jlFrSQL.findList(jlFr);
		if(jlFrList.size()>0){
			jlFr = jlFrList.get(0);
		}else{
			result.error(Result.error_103, "当前服刑人员不存在");
			return result;
		}
		
		JlJqVO jlJq = new JlJqVO(); // 当前罪犯监区
		jlJq.setJqNo(jlFr.getJq());
		List<JlJqVO> jlJqList = jlJqSQL.findList(jlJq);
		if(jlJqList.size()>0){
			jlJq = jlJqList.get(0);
		}
		
		
		JlHjDjVO addJlHjDj = new JlHjDjVO(); // 创建会见登记
		addJlHjDj.setJy(jlJq.getJy());
		addJlHjDj.setJqNo(jlJq.getJqNo());
		addJlHjDj.setJqName(jlJq.getJqName());
		addJlHjDj.setFrNo(jlFr.getFrNo());
		addJlHjDj.setFrName(jlFr.getFrName());
		addJlHjDj.setHjType(hjType); // 会见类型
		addJlHjDj.setDjTime(new Date());
		addJlHjDj.setHjTime(hjsc*60); // 单位：秒
		addJlHjDj.setHjInfo(hjsm); // 会见说明
		addJlHjDj.setDjUser(TokenUser.getUser().getUserNo()); // 登记人
		addJlHjDj.setFpFlag(0);
		addJlHjDj.setFpTzfrFlag(0);
		addJlHjDj.setFpTzqsFlag(0);
		addJlHjDj.setHjOrder(callNo);
		addJlHjDj.setMonitorFlag("");
		addJlHjDj.setPageTzState(0);
		String hql10="from SysParam where paramName='HJ_Client1'";
		for(int i=0; i<qsIds.size();i++){ // 亲属
			JlQsVO jlQs = jlQsSQL.findOne(qsIds.get(i));
			String gx = StringUtils.isNotBlank(jlQs.getGx())?"["+jlQs.getGx()+"]":"";
			String name = StringUtils.isNotBlank(jlQs.getQsName())?jlQs.getQsName():"";
			if(i==0){
				addJlHjDj.setQsInfo1(gx+name);
				addJlHjDj.setQsZp1(jlQs.getJz());
				addJlHjDj.setQsCard1(jlQs.getQsCard());
			}else if(i==1){
				addJlHjDj.setQsInfo2(gx+name);
				addJlHjDj.setQsZp2(jlQs.getJz());
				addJlHjDj.setQsCard2(jlQs.getQsCard());
			}else if(i==2){
				addJlHjDj.setQsInfo3(gx+name);
				addJlHjDj.setQsZp3(jlQs.getJz());
				addJlHjDj.setQsCard3(jlQs.getQsCard());
			}else if(i==3){
				addJlHjDj.setQsInfo4(gx+name);
				addJlHjDj.setQsZp4(jlQs.getJz());
				addJlHjDj.setQsCard4(jlQs.getQsCard());
			}else if(i==4){
				addJlHjDj.setQsInfo5(gx+name);
				addJlHjDj.setQsZp5(jlQs.getJz());
				addJlHjDj.setQsCard5(jlQs.getQsCard());
			}else if(i==5){
				addJlHjDj.setQsInfo6(gx+name);
				addJlHjDj.setQsZp6(jlQs.getJz());
				addJlHjDj.setQsCard6(jlQs.getQsCard());
			}else if(i==6){
				addJlHjDj.setQsInfo7(gx+name);
				addJlHjDj.setQsZp7(jlQs.getJz());
				addJlHjDj.setQsCard7(jlQs.getQsCard());
			}else if(i==7){
				addJlHjDj.setQsInfo8(gx+name);
				addJlHjDj.setQsZp8(jlQs.getJz());
				addJlHjDj.setQsCard8(jlQs.getQsCard());
			}else if(i==8){
				addJlHjDj.setQsInfo9(gx+name);
				addJlHjDj.setQsZp9(jlQs.getJz());
				addJlHjDj.setQsCard9(jlQs.getQsCard());
			}
		}
		return null;
	}

	

}
