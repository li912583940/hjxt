package com.sl.ue.service.jl.sqlImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.entity.sys.vo.SysParamVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.service.sys.SysParamService;
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
	@Autowired
	private SysParamService sysParamSQL;
	
	@Override
	public Result addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjsm, // 会见说明
			Integer hjType, // 会见类型
			Integer callNo, //排队号
			Integer tpQsNum, //特批亲属个数
			Integer qzSp // 强制审批
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
		if(hjType == null ){
			addJlHjDj.setHjType(1); // 会见类型   1 严见，2 宽见
		}else{
			addJlHjDj.setHjType(hjType); // 会见类型   1 严见，2 宽见
		}
		
		addJlHjDj.setDjTime(new Date());
		if(hjsc == null){
			addJlHjDj.setHjTime(30*60); // 单位：秒  , 30分钟
		}else{
			addJlHjDj.setHjTime(hjsc*60); // 单位：秒
		}
		addJlHjDj.setHjInfo(hjsm); // 会见说明
		addJlHjDj.setDjUser(TokenUser.getUser().getUserNo()); // 登记人
		addJlHjDj.setFpFlag(0);
		addJlHjDj.setFpTzfrFlag(0);
		addJlHjDj.setFpTzqsFlag(0);
		addJlHjDj.setHjOrder(callNo);
		addJlHjDj.setMonitorFlag("");
		addJlHjDj.setPageTzState(0);
		
		SysParamVO sysParam = new SysParamVO();
		sysParam.setParamName("HJ_Client1");
		List<SysParamVO> sysParamList =  sysParamSQL.findList(sysParam);
		addJlHjDj.setDjType(0); // 会见登记是否需要审批 0 否 1是
		if(sysParamList.size()>0){
			sysParam = sysParamList.get(0);
			if(StringUtils.isNotBlank(sysParam.getParamData4()) && "1".equals(sysParam.getParamData4())){
				addJlHjDj.setDjType(1);
			}
		}
		if(addJlHjDj.getHjType() == 1){
			addJlHjDj.setState(0); // 登记状态，0 未完成会见，1已完成会见，2 已取消会见 默认值：0
		}else{
			addJlHjDj.setState(1);
		}
		Long hjIndex = (Long) jdbcTemplate.execute(  // 调用存储过程 获取会见批次号
		     new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection con) throws SQLException {
					 String storedProc = "{call get_hj_index(?,?)}";// 调用的sql   
			           CallableStatement cs = con.prepareCall(storedProc);   
			           cs.setString(1, frNo);// 设置输入参数的值   
			           cs.registerOutParameter(2, java.sql.Types.BIGINT);// 注册输出参数的类型   
			           return cs;   
				}
			}, 
		    new CallableStatementCallback<Long>() {  
				@Override
		        public Long doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
		           cs.execute();   
		           return cs.getLong(2);// 获取输出参数的值   
		        }   
		});
		addJlHjDj.setHjIndex(hjIndex);
		addJlHjDj.setImportFlag(0);
		if(tpQsNum == null){
			addJlHjDj.setTpQsNum(0);
		}else{
			addJlHjDj.setTpQsNum(tpQsNum);
		}
		if(qzSp == null){
			addJlHjDj.setQzSp(0);
		}else{
			addJlHjDj.setQzSp(qzSp);
		}
		
		String[] qsIdss = qsIds.split(",");
		for(int i=0; i<qsIdss.length;i++){ // 亲属
			JlQsVO jlQs = jlQsSQL.findOne(qsIdss[i]);
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
		try {
			addJlHjDj = this.add(addJlHjDj);
		} catch (Exception e) {
			result.error(Result.error_103, "添加会见登记失败");
			return result;
		}
		System.out.println(addJlHjDj.getHjid());
		result.msg("提交登记成功");
		return result;
	}

	

}
