package com.sl.ue.service.jl.sqlImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.entity.sys.vo.SysParamVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjSpService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.service.sys.SysParamService;
import com.sl.ue.util.DateUtil;
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
	@Autowired
	private JlHjSpService jlHjSpSQL;
	@Autowired
	private SysHjLineService sysHjLineSQL;
	
	
	@Override
	public String addHjdj(
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
			return result.toResult();
		}
		
		JlFrVO jlFr = new JlFrVO();  //当前罪犯信息
		jlFr.setFrNo(frNo);
		List<JlFrVO> jlFrList = jlFrSQL.findList(jlFr);
		if(jlFrList.size()>0){
			jlFr = jlFrList.get(0);
		}else{
			result.error(Result.error_103, "当前服刑人员不存在");
			return result.toResult();
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
			return result.toResult();
		}
		System.out.println(addJlHjDj.getHjid());
		result.msg("提交登记成功");
		return result.toResult();
	}

	@Override
	public String printXp(Long id) {
		Result result = new Result();
		JlHjDjVO jlHjDj = this.findOne(id);
		if(jlHjDj == null){
			result.error(Result.error_103, "当前登记记录不存在");
			return result.toResult();
		}
		List<String> list = new ArrayList<String>();
		list.add("会见准见证");
		list.add(DateUtil.getDefault(new Date())); //打印时间
		list.add("会见编号: "+jlHjDj.getHjIndex().toString().substring(8));
		if(StringUtils.isNoneBlank(jlHjDj.getFrName())){
			list.add("罪犯姓名: "+jlHjDj.getFrName());
		}
		if(StringUtils.isNotBlank(jlHjDj.getJqName())){
			list.add("监区: "+jlHjDj.getJqName());
		}
		list.add("本次会见时长: "+jlHjDj.getHjTime()/60+"分钟");
		int i=0;
		if(jlHjDj.getQsInfo1()!=null && jlHjDj.getQsInfo1()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo1());
		}
		if(jlHjDj.getQsInfo2()!=null && jlHjDj.getQsInfo2()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo2());
		}
		if(jlHjDj.getQsInfo3()!=null && jlHjDj.getQsInfo3()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo3());
		}
		if(jlHjDj.getQsInfo4()!=null && jlHjDj.getQsInfo4()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo4());
		}
		if(jlHjDj.getQsInfo5()!=null && jlHjDj.getQsInfo5()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo5());
		}
		if(jlHjDj.getQsInfo6()!=null && jlHjDj.getQsInfo6()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo6());
		}
		if(jlHjDj.getQsInfo7()!=null && jlHjDj.getQsInfo7()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo7());
		}
		if(jlHjDj.getQsInfo8()!=null && jlHjDj.getQsInfo8()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo8());
		}
		if(jlHjDj.getQsInfo9()!=null && jlHjDj.getQsInfo9()!=""){
			i++;
			list.add(i+"号亲属: "+jlHjDj.getQsInfo9());
		}
		list.add("会见总人数: "+i+"人");
		result.putData(list);
		return result.toResult();
	}

	public String cancelDj(Long id, String cancelInfo){
		Result result = new Result();
		JlHjDjVO jlHjDj = this.findOne(id);
		if(jlHjDj == null){
			result.error(Result.error_103, "当前登记记录不存在");
			return result.toResult();
		}
		
		if(jlHjDj.getFpFlag()==0){
			jlHjDj.setState(2);
			jlHjDj.setCancelInfo(cancelInfo);
			this.edit(jlHjDj);
			
			JlHjSpVO jlHjSp = new JlHjSpVO();
			jlHjSp.setHjId(jlHjDj.getHjid());
			jlHjSpSQL.delete(jlHjSp);
			
			result.putJson(0);
			return result.toResult();
		}else if(jlHjDj.getFpFlag()==1){
			String sql="update SysHjLine set hjid=null where hjid="+jlHjDj.getHjid();
			this.jdbcTemplate.update(sql);
			
			jlHjDj.setState(2);
			jlHjDj.setCancelInfo(cancelInfo);
			this.edit(jlHjDj);
			
			result.putJson(0);
			return result.toResult();
		}else{
			result.putJson(1);
			return result.toResult();
		}
	}

	@Override
	public Map<String, Object> findPojoJoin(JlHjDjVO model, Integer pageSize, Integer pageNum) {
		StringBuffer leftJoinField = new StringBuffer(); // 字段
		leftJoinField.append(",(CASE WHEN a.FP_Flag=0 THEN '未分配' ELSE dbo.get_ck(a.FP_Line_No,a.JY) END) AS zw");
		
		StringBuffer leftJoinWhere = new StringBuffer();
		leftJoinWhere.append(" AND (a.state=0 OR a.state=3)"); // 条件
		
		model.setLeftJoinField(leftJoinField.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		return map;
	}

	@Override
	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum, String qsName,
			String qsSfz) {
		String frNos = "";
		if(StringUtils.isNotBlank(qsName) ||  StringUtils.isNotBlank(qsSfz)){
			JlQsVO queryJlQs = new JlQsVO();
			queryJlQs.setQsSfz(qsSfz);
			StringBuffer WhereJlQs = new StringBuffer(); // sql条件
			if(StringUtils.isNotBlank(qsName)){
				WhereJlQs.append(" AND a.QS_Name LIKE '%"+qsName+"%' ");
			}
			queryJlQs.setLeftJoinWhere(WhereJlQs.toString());
			List<JlQsVO> jlQsList = jlQsSQL.findList(queryJlQs, pageSize, pageNum);
			if(jlQsList.size()>0){
				for(int i=0; i<jlQsList.size();i++){
					JlQsVO jlQs = jlQsList.get(i);
					if(i==0){
						frNos = jlQs.getFrNo();
					}else{
						frNos +=","+jlQs.getFrNo();
					}
				}
			}else{
				frNos = "-1";
			}
			
		}
		
		StringBuffer field = new StringBuffer(); // sql关联字段
		field.append(",b.JQ_Name");
		field.append(",c.JB_Name");
		
		StringBuffer table = new StringBuffer(); // sql关联表
		table.append(" left join JL_JQ b ON a.JQ=b.JQ_No");
		table.append(" left join JL_JB c ON a.JB_No=c.JB_No");
		
		StringBuffer Where = new StringBuffer(); // sql条件
    	if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		Where.append(" AND a.FR_Name LIKE '%"+str+"%' ");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(frNos)){
    		Where.append(" AND a.FR_No in ("+frNos+") ");
    	}
    	model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
    	model.setLeftJoinWhere(Where.toString());
		Map<String, Object> map = jlFrSQL.findPojo(model, pageSize, pageNum);
//		if(map.containsKey("list")) { //查询亲属个数
//			List<JlFrVO> list = (List<JlFrVO>) map.get("list");
//			for(JlFrVO jlFr : list) {
//				JlQsVO jlQs = new JlQsVO();
//				jlQs.setFrNo(jlFr.getFrNo());
//				Integer qsNum= jlQsSQL.count(jlQs);
//				jlFr.setQsNum(qsNum);
//			}
//		}
		return map;
	}

	
	public Map<String, Object> findPojoByHjSign(JlHjDjVO model, Integer pageSize, Integer pageNum){
		StringBuffer leftJoinField = new StringBuffer(); // 字段 
		leftJoinField.append(",dbo.get_ck(a.FP_Line_No,a.JY) as zw");
		
		StringBuffer leftJoinWhere = new StringBuffer();  // 条件
		leftJoinWhere.append(" AND a.State=0 and (a.DJ_Type=0 or a.DJ_Type=2)");
		
		model.setLeftJoinField(leftJoinField.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		List<JlHjDjVO> list = (List<JlHjDjVO>) map.get("list");
		for(JlHjDjVO t : list){
			String qsName = "";
			if(StringUtils.isNotBlank(t.getQsInfo1())){
				qsName+=t.getQsInfo1();
			}
			if(StringUtils.isNotBlank(t.getQsInfo2())){
				qsName+=t.getQsInfo2();
			}
			if(StringUtils.isNotBlank(t.getQsInfo3())){
				qsName+=t.getQsInfo3();
			}
			if(StringUtils.isNotBlank(t.getQsInfo4())){
				qsName+=t.getQsInfo4();
			}
			if(StringUtils.isNotBlank(t.getQsInfo5())){
				qsName+=t.getQsInfo5();
			}
			if(StringUtils.isNotBlank(t.getQsInfo6())){
				qsName+=t.getQsInfo6();
			}
			if(StringUtils.isNotBlank(t.getQsInfo7())){
				qsName+=t.getQsInfo7();
			}
			if(StringUtils.isNotBlank(t.getQsInfo8())){
				qsName+=t.getQsInfo8();
			}
			if(StringUtils.isNotBlank(t.getQsInfo9())){
				qsName+=t.getQsInfo9();
			}
			t.setQsInfo1(qsName);
			t.setHjTime(t.getHjTime()/60);
		}
		return map;
	}
	
	public String fpZw(Long hjId){
		Result result = new Result();
		if(hjId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjDjVO jlHjDj = this.findOne(hjId);
		if(jlHjDj == null){
			result.error(Result.error_103, "查询不到此条记录");
			return result.toResult();
		}
		if(jlHjDj.getFpFlag() == 0){
			Integer resu = (Integer) jdbcTemplate.execute(  // 调用存储过程 获取会见批次号
				     new CallableStatementCreator() {
						@Override
						public CallableStatement createCallableStatement(Connection con) throws SQLException {
							 String storedProc = "{call set_ZW_ex_new1(?,?,?)}";// 调用的sql   
					           CallableStatement cs = con.prepareCall(storedProc); 
					           cs.setInt(1, jlHjDj.getHjType());
					           cs.setString(2, jlHjDj.getFrNo());// 设置输入参数的值   
					           cs.registerOutParameter(3, java.sql.Types.INTEGER);// 注册输出参数的类型   
					           return cs;   
						}
					}, 
				    new CallableStatementCallback<Integer>() {  
						@Override
				        public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
				           cs.execute();   
				           return cs.getInt(3);// 获取输出参数的值   
				        }   
			});
			
			if(resu==0){
				
			}else if(resu==1){
				result.error(Result.error_103,"当前已没有空闲座位可供使用");
			}
		}else{
			result.error(Result.error_103,"当前记录已分配座位");
		}
		return result.toResult();
	}
	
	public String qxFpZw(Long hjId, HttpServletRequest request){
		Result result = new Result();
		
		SysParamVO sysParam = new SysParamVO();
		sysParam.setParamName("HJ_Client4");
		List<SysParamVO> sysParamList = sysParamSQL.findList(sysParam);
		if(sysParamList.size()>0){
			SysParamVO t = sysParamList.get(0);
			if(!request.getRemoteAddr().equals(t.getParamData1())){
				result.error(Result.error_103, "电脑IP地址非法");
				return result.toResult();
			}
		}
		
		if(hjId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjDjVO jlHjDj = this.findOne(hjId);
		if(jlHjDj == null){
			result.error(Result.error_103, "查询不到此条记录");
			return result.toResult();
		}
		if(jlHjDj.getFpFlag()==2){
			result.error(Result.error_103, "已处于会见通话状态，无法取消");
			return result.toResult();
		}else if(jlHjDj.getFpFlag()==0){
			
		}else{
			String sql="update SysHjLine set hjid=null where hjid="+jlHjDj.getHjid();
			this.jdbcTemplate.update(sql);
			
			/** 原会见系统的逻辑*/
//			String hql1="from JlFr where frNo='"+jlHjDj.getFrNo()+"'";
//			List<JlFr> list=ycs.searchAll(hql1);
//			if(list.size()>0){
//				jlHjDj.setJy(list.get(0).getJy());
//			}else{
//				jlHjDj.setJy("Server1");
//			}
			
			String hjSql = "update JL_HJ_DJ set FP_Flag=0,FP_Line_No=null,FP_Time=null,FP_Time_FR=null,FP_Time_QS=null where hjid="+jlHjDj.getHjid();
			this.jdbcTemplate.update(hjSql);
			
		}
		return result.toResult();
	}
	
	
	public String getSurplusZw(Long hjId){
		Result result = new Result();
		if(hjId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjDjVO jlHjDj = this.findOne(hjId);
		if(jlHjDj == null){
			result.error(Result.error_103, "查询不到此条记录");
			return result.toResult();
		}
		result.putJson("jlHjDj", jlHjDj);
		
		SysHjLineVO sysHjLine = new SysHjLineVO();
		sysHjLine.setState(1);
		sysHjLine.setHjstate(0);
		if(jlHjDj.getHjType()==2){
			sysHjLine.setLineType(1);
		}else{
			sysHjLine.setLineType(0);
		}
		sysHjLine.setLeftJoinWhere(" AND hjid is null");
		List<SysHjLineVO> sysHjLineList = sysHjLineSQL.findList(sysHjLine);
		result.putData("sysHjLineList", sysHjLineList);
		return result.toResult();
	}
	
	public String rgFpZw(Long hjId, Integer lineNo, HttpServletRequest request){
		Result result = new Result();
		
		SysParamVO sysParam = new SysParamVO();
		sysParam.setParamName("HJ_Client4");
		List<SysParamVO> sysParamList = sysParamSQL.findList(sysParam);
		if(sysParamList.size()>0){
			SysParamVO t = sysParamList.get(0);
			if(!request.getRemoteAddr().equals(t.getParamData1())){
				result.error(Result.error_103, "电脑IP地址非法");
				return result.toResult();
			}
		}
		
		if(hjId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjDjVO jlHjDj = this.findOne(hjId);
		if(jlHjDj == null){
			result.error(Result.error_103, "查询不到此条记录");
			return result.toResult();
		}
		SysHjLineVO sysHjLine = new SysHjLineVO();
		sysHjLine.setLineNo(lineNo);
		List<SysHjLineVO> sysHjLineList = sysHjLineSQL.findList(sysHjLine);
		sysHjLine = sysHjLineList.get(0);
		String jy = sysHjLine.getJy();
		if(jlHjDj.getFpFlag() == 0){
			Integer resu = (Integer) jdbcTemplate.execute(  // 调用存储过程 获取会见批次号
				     new CallableStatementCreator() {
						@Override
						public CallableStatement createCallableStatement(Connection con) throws SQLException {
							 String storedProc = "{call set_ZW_ts(?,?,?,?)}";// 调用的sql   
					           CallableStatement cs = con.prepareCall(storedProc); 
					           cs.setString(1, jlHjDj.getFrNo());// 设置输入参数的值   
					           cs.setString(2, jy);// 设置输入参数的值   
					           cs.setInt(3, lineNo);
					           cs.registerOutParameter(4, java.sql.Types.INTEGER);// 注册输出参数的类型   
					           return cs;   
						}
					}, 
				    new CallableStatementCallback<Integer>() {  
						@Override
				        public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
				           cs.execute();   
				           return cs.getInt(4);// 获取输出参数的值   
				        }   
			});
			
			if(resu==0){
				
			}else if(resu==1){
				result.error(Result.error_103,"当前已没有空闲座位可供使用");
			}
		}else{
			result.error(Result.error_103,"当前记录已分配座位");
		}
		return result.toResult();
	}
	
	public String grantCall(Long hjId, HttpServletRequest request){
		Result result = new Result();
		
		SysParamVO sysParam = new SysParamVO();
		sysParam.setParamName("HJ_Client4");
		List<SysParamVO> sysParamList = sysParamSQL.findList(sysParam);
		if(sysParamList.size()>0){
			SysParamVO t = sysParamList.get(0);
			if(!request.getRemoteAddr().equals(t.getParamData1())){
				result.error(Result.error_103, "电脑IP地址非法");
				return result.toResult();
			}
		}
		
		if(hjId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		
		JlHjDjVO jlHjDj = this.findOne(hjId);
		if(jlHjDj == null){
			result.error(Result.error_103, "查询不到此条记录");
			return result.toResult();
		}
		SysUserVO user = TokenUser.getUser();
		jlHjDj.setShState(1);
		jlHjDj.setYjNo(user.getUserNo());
		jlHjDj.setYjName(user.getUserName());
		jlHjDj.setFrInUser(user.getUserNo());
		jlHjDj.setFrInTime(new Date());
		this.edit(jlHjDj);
		
		return result.toResult();
	}
	
	public String cancelGrantCall(Long hjId, HttpServletRequest request){
		Result result = new Result();
		
		SysParamVO sysParam = new SysParamVO();
		sysParam.setParamName("HJ_Client4");
		List<SysParamVO> sysParamList = sysParamSQL.findList(sysParam);
		if(sysParamList.size()>0){
			SysParamVO t = sysParamList.get(0);
			if(!request.getRemoteAddr().equals(t.getParamData1())){
				result.error(Result.error_103, "电脑IP地址非法");
				return result.toResult();
			}
		}
		
		if(hjId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		
		JlHjDjVO jlHjDj = this.findOne(hjId);
		if(jlHjDj == null){
			result.error(Result.error_103, "查询不到此条记录");
			return result.toResult();
		}
		jlHjDj.setShState(0);
		this.edit(jlHjDj);
		
		return result.toResult();
	}
}
