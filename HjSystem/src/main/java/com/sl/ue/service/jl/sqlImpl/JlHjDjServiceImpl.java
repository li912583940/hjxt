package com.sl.ue.service.jl.sqlImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjQsVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjHolidayVO;
import com.sl.ue.entity.jl.vo.JlHjJqHolidayVO;
import com.sl.ue.entity.jl.vo.JlHjJqWeekVO;
import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.entity.jl.vo.JlHjSpSetVO;
import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.entity.sys.vo.SysParamVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjQsService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjHolidayService;
import com.sl.ue.service.jl.JlHjJqHolidayService;
import com.sl.ue.service.jl.JlHjJqWeekService;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.service.jl.JlHjSpService;
import com.sl.ue.service.jl.JlHjSpSetService;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.service.sys.SysNoticeConfService;
import com.sl.ue.service.sys.SysParamService;
import com.sl.ue.util.Config;
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
	private JlHjJqWeekService jlHjJqWeekSQL;
	@Autowired
	private JlQsService jlQsSQL;
	@Autowired
	private SysParamService sysParamSQL;
	@Autowired
	private SysHjLineService sysHjLineSQL;
	@Autowired
	private JlHjSpSetService jlHjSpSetSQL;
	@Autowired
	private JlHjSpService jlHjSpSQL;
	@Autowired
	private JlJbService jlJbSQL;
	@Autowired
	private JlHjRecService jlHjRecSQL;
	@Autowired
    private JlHjJqHolidayService jlHjJqHolidaySQL;
	@Autowired
    private JlHjHolidayService jlHjHolidaySQL;
	@Autowired
	private SysNoticeConfService sysNoticeConfSQL;
	@Autowired
	private JlHjDjQsService jlHjDjQsSQL;
	
	@Override
	public String addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjInfo, // 会见说明
			Integer hjType, // 会见类型
			Integer hjMode, //会见方式
			Integer callNo, //排队号
			Integer tpQsNum, //特批亲属个数
			Integer qzSp // 强制审批
			) {
		Result result = new Result();
		
		List<SysNoticeConfVO> sysNoticeConfList = sysNoticeConfSQL.findList(new SysNoticeConfVO());
		int notice = 0; // 会见通知。 0：登记完自动发起。1：身份验证成功后发起
		if(sysNoticeConfList.size()>0){
			SysNoticeConfVO sysNoticeConf = sysNoticeConfList.get(0);
			notice = sysNoticeConf.getHjNotice();
		}
		List<String> qsGxList = new ArrayList(); // 将登记的亲属关系存储起来， 最后判断其中是否有家属关系需要审批
		JlHjDjVO addJlHjDj = new JlHjDjVO(); // 创建会见登记
		
		if(notice==0){
			addJlHjDj.setPageTzMode(0);
		}else if(notice==1){
			addJlHjDj.setPageTzMode(1);
		}
		//会见登记家属表，用来作身份验证
		List<JlHjDjQsVO> jlHjDjQsList = new ArrayList<>();
		String[] qsIdss = qsIds.split(",");
		String qsInfo="";
		for(int i=0; i<qsIdss.length;i++){ // 亲属
			JlQsVO jlQs = jlQsSQL.findOne(qsIdss[i]);
			if(StringUtils.isBlank(jlQs.getGx()) || StringUtils.isBlank(jlQs.getQsSfz())){ //先判断提交的亲属中是否有身份证号码和亲属关系没有的
				result.error(Result.error_103, "提交的登记中，有家属没有绑定身份证号码或者亲属关系");
				return result.toResult();
			}
			if(StringUtils.isNotBlank(jlQs.getGx())){
				qsGxList.add(jlQs.getGx());
			}
			//添加登记家属，用来作验证
			JlHjDjQsVO jlHjDjQs =JSON.parseObject(JSONObject.toJSONString(jlQs), JlHjDjQsVO.class);
			jlHjDjQs.setWebId(null);
			jlHjDjQs.setQsId(jlQs.getWebId());
			jlHjDjQsList.add(jlHjDjQs);
			
			String gx = "["+jlQs.getGx()+"]";
			String name = StringUtils.isNotBlank(jlQs.getQsName())?jlQs.getQsName():"";
			if(i==0){
				addJlHjDj.setQsInfo1(gx+name);
				addJlHjDj.setQsCard1(jlQs.getQsCard());
				qsInfo=addJlHjDj.getQsInfo1();
			}else if(i==1){
				addJlHjDj.setQsInfo2(gx+name);
				addJlHjDj.setQsCard2(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo2();
			}else if(i==2){
				addJlHjDj.setQsInfo3(gx+name);
				addJlHjDj.setQsCard3(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo3();
			}else if(i==3){
				addJlHjDj.setQsInfo4(gx+name);
				addJlHjDj.setQsCard4(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo4();
			}else if(i==4){
				addJlHjDj.setQsInfo5(gx+name);
				addJlHjDj.setQsCard5(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo5();
			}else if(i==5){
				addJlHjDj.setQsInfo6(gx+name);
				addJlHjDj.setQsCard6(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo6();
			}else if(i==6){
				addJlHjDj.setQsInfo7(gx+name);
				addJlHjDj.setQsCard7(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo7();
			}else if(i==7){
				addJlHjDj.setQsInfo8(gx+name);
				addJlHjDj.setQsCard8(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo8();
			}else if(i==8){
				addJlHjDj.setQsInfo9(gx+name);
				addJlHjDj.setQsCard9(jlQs.getQsCard());
				qsInfo+=addJlHjDj.getQsInfo9();
			}
		}
		
		
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
		
		JlJbVO jlJb = new JlJbVO();
		jlJb.setJbNo(jlFr.getJbNo());
		List<JlJbVO> jlJbList = jlJbSQL.findList(jlJb);
		if(jlJbList.size()>0){
			jlJb = jlJbList.get(0);
		}
		
		
		/** 查看当前罪犯是否符合会见登记条件 开始 */
		boolean is_sp=false; //是否需要审批
		JlHjSpSetVO jlHjSpSet = null;
		String explain = "";
		if(jlJb.getHjCount() > 0){
			JlHjRecVO jlHjRecQuery = new JlHjRecVO();
			jlHjRecQuery.setFrNo(frNo);
			Calendar c = Calendar.getInstance();    
			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
			String ymd = DateUtil.getFormat(c.getTime(), "yyyy-MM-dd");
			ymd+=" 00:00:00";
			jlHjRecQuery.setLeftJoinWhere(" AND a.Call_Time_Start>'"+ymd+"'");
			int count = jlHjRecSQL.count(jlHjRecQuery); // 犯人当月会见次数
			if(jlJb.getHjCount()<=count){
				// 查看 《罪犯本月会见次数已用完》是否开始了审批，如果没有开启，直接返回提示信息
				JlHjSpSetVO jlHjSpSetQuery = new JlHjSpSetVO();
				jlHjSpSetQuery.setUsable(1);
				jlHjSpSetQuery.setSpNo("1");
				List<JlHjSpSetVO> jlHjSpSetList = jlHjSpSetSQL.findList(jlHjSpSetQuery);
				if(jlHjSpSetList.size()>0){ // 开启了《罪犯本月会见次数已用完》的审批
					jlHjSpSet=jlHjSpSetList.get(0);
					explain=jlHjSpSet.getSpName();
					is_sp=true;
				}else{
					result.error(Result.error_103, "罪犯本月会见次数已用完");
					return result.toResult();
				}
			}
		}else if(jlJb.getHjCount() == 0){
			// 罪犯级别不允许会见
			JlHjSpSetVO jlHjSpSetQuery = new JlHjSpSetVO();
			jlHjSpSetQuery.setUsable(1);
			jlHjSpSetQuery.setSpNo("2");
			List<JlHjSpSetVO> jlHjSpSetList = jlHjSpSetSQL.findList(jlHjSpSetQuery);
			if(jlHjSpSetList.size()>0){ // 开启了《罪犯本月会见次数已用完》的审批
				jlHjSpSet=jlHjSpSetList.get(0);
				if(StringUtils.isNotBlank(explain)){
					explain+=";"+jlHjSpSet.getSpName();
				}else{
					explain=jlHjSpSet.getSpName();
				}
				is_sp=true;
			}else{
				result.error(Result.error_103, "罪犯级别不允许会见");
				return result.toResult();
			}
		}
		
		if(jlFr.getHjStopTime()!=null){ // 当前罪犯处于会见禁止日期
			if(new Date().before(jlFr.getHjStopTime())){  
				// 罪犯当前被禁止会见
				JlHjSpSetVO jlHjSpSetQuery = new JlHjSpSetVO();
				jlHjSpSetQuery.setUsable(1);
				jlHjSpSetQuery.setSpNo("3");
				List<JlHjSpSetVO> jlHjSpSetList = jlHjSpSetSQL.findList(jlHjSpSetQuery);
				if(jlHjSpSetList.size()>0){ // 开启了《罪犯本月会见次数已用完》的审批
					jlHjSpSet=jlHjSpSetList.get(0);
					if(StringUtils.isNotBlank(explain)){
						explain+=";"+jlHjSpSet.getSpName();
					}else{
						explain=jlHjSpSet.getSpName();
					}
					is_sp=true;
				}else{
					result.error(Result.error_103, "罪犯当前被禁止会见");
					return result.toResult();
				}
			}
		}
		
		// 查看当前罪犯所在监区是否是会见星期日
		JlHjJqWeekVO jlHjJqWeekQuery = new JlHjJqWeekVO();
		jlHjJqWeekQuery.setJqNo(jlJq.getJqNo());
		List<JlHjJqWeekVO> jlHjJqWeekList = jlHjJqWeekSQL.findList(jlHjJqWeekQuery);
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date());
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if(week==1){
			week=7;
		}else{
			week = week-1;
		}
		boolean is_week = false;
		for(JlHjJqWeekVO t : jlHjJqWeekList){
			if(t.getJqWeek()==week){
				is_week=true;
			}
		}
		if(is_week == false){
			// 判断当天是不是节假会见日
			JlHjJqHolidayVO jlHjJqHolidayQuery = new JlHjJqHolidayVO();
			jlHjJqHolidayQuery.setJqNo(jlJq.getJqNo());
			int jqCount = jlHjJqHolidaySQL.count(jlHjJqHolidayQuery);
			
			String dateFormat = DateUtil.getDefaultNow("yyyy-MM-dd");
			JlHjHolidayVO jlHjHolidayQuery = new JlHjHolidayVO();
			jlHjHolidayQuery.setHoliday(dateFormat);
			int hoCount = jlHjHolidaySQL.count(jlHjHolidayQuery);
			if(jqCount>0 && hoCount>0){ //大于0 当天是此监区的节假日会见，无需审批
			}else{
				// 服刑人员当前监区不是会见日
				JlHjSpSetVO jlHjSpSetQuery = new JlHjSpSetVO();
				jlHjSpSetQuery.setUsable(1);
				jlHjSpSetQuery.setSpNo("4");
				List<JlHjSpSetVO> jlHjSpSetList = jlHjSpSetSQL.findList(jlHjSpSetQuery);
				if(jlHjSpSetList.size()>0){ // 开启了《罪犯本月会见次数已用完》的审批
					jlHjSpSet=jlHjSpSetList.get(0);
					if(StringUtils.isNotBlank(explain)){
						explain+=";"+jlHjSpSet.getSpName();
					}else{
						explain=jlHjSpSet.getSpName();
					}
					is_sp=true;
				}else{
					result.error(Result.error_103, "服刑人员当前监区不是会见日");
					return result.toResult();
				}
			}
			
		}
		/** 查看当前罪犯是否符合会见登记条件 结束 */
		
		
		
		
		
		//亲属关系为其他
		JlHjSpSetVO jlHjSpSetQuery = new JlHjSpSetVO();
		jlHjSpSetQuery.setUsable(1);
		jlHjSpSetQuery.setSpNo("5");
		List<JlHjSpSetVO> jlHjSpSetList = jlHjSpSetSQL.findList(jlHjSpSetQuery);
		if(jlHjSpSetList.size()>0){ // 开启了《罪犯本月会见次数已用完》的审批
			jlHjSpSet=jlHjSpSetList.get(0);
			List<String> spGx = Arrays.asList(jlHjSpSet.getSpValue().split(","));
			for(String gx :qsGxList){
				if(spGx.contains(gx)){ //有亲属关系需要审批
					if(StringUtils.isNotBlank(explain)){
						explain+=";"+jlHjSpSet.getSpName();
					}else{
						explain=jlHjSpSet.getSpName();
					}
					is_sp=true;
					break;
				}
			}
		}
		
		
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
		addJlHjDj.setHjMode(hjMode);
		addJlHjDj.setDjTime(new Date());
		if(hjsc == null){
			addJlHjDj.setHjTime(jlJb.getHjTime()*60); // 单位：秒  , 30分钟
		}else{
			addJlHjDj.setHjTime(hjsc*60); // 单位：秒
		}
		addJlHjDj.setHjInfo(hjInfo); // 会见说明
		addJlHjDj.setDjUser(TokenUser.getUser().getUserNo()); // 登记人
		addJlHjDj.setFpFlag(0);
		addJlHjDj.setFpTzfrFlag(0);
		addJlHjDj.setFpTzqsFlag(0);
		addJlHjDj.setHjOrder(callNo);
		addJlHjDj.setMonitorFlag("");
		addJlHjDj.setPageTzState(0);
		addJlHjDj.setDjType(0); // 会见登记是否需要审批 0 否 1是
		if(is_sp){ // 需要审批
			addJlHjDj.setState(3);
		}else{
			addJlHjDj.setState(0);
		}
		
//		if(addJlHjDj.getHjType() == 1){
//			addJlHjDj.setState(0); // 登记状态，0 未完成会见，1已完成会见，2 已取消会见 默认值：0
//		}else{
//			addJlHjDj.setState(1);
//		}
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
		
		
		
		
		try {
			addJlHjDj = this.add(addJlHjDj);
			for(JlHjDjQsVO t : jlHjDjQsList){
				t.setHjId(addJlHjDj.getHjid());
				jlHjDjQsSQL.add(t);
			}
			if(is_sp){
				JlHjSpVO jlHjSp = new JlHjSpVO();
				jlHjSp.setHjid(addJlHjDj.getHjid());
				jlHjSp.setType(1);
				jlHjSp.setSetNo(jlHjSpSet.getSpNo());
				jlHjSp.setSetName(jlHjSpSet.getSpName());
				jlHjSp.setExplain(explain);
				jlHjSp.setMaxNum(jlHjSpSet.getMaxNum());
				jlHjSp.setSpeedProgress(1);
				jlHjSp.setState(0);
				jlHjSp.setFrNo(addJlHjDj.getFrNo());
				jlHjSp.setFrName(addJlHjDj.getFrName());
				jlHjSp.setJqNo(addJlHjDj.getJqNo());
				jlHjSp.setJqName(addJlHjDj.getJqName());
				jlHjSp.setQsInfo(qsInfo);
				jlHjSp.setTjTime(new Date());
				jlHjSpSQL.add(jlHjSp);
				result.msg("提交登记成功，但此次会见需要审批，审批通过后才能参与会见");
			}else{
				result.msg("提交登记成功");
			}
		} catch (Exception e) {
			result.error(Result.error_103, "添加会见登记失败");
			return result.toResult();
		}
		
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
			
			// 查看当前会见登记是否有审批，有的话就删除
			JlHjSpVO jlHjSpQuery = new JlHjSpVO();
			jlHjSpQuery.setHjid(jlHjDj.getHjid());
			List<JlHjSpVO> jlHjSpList = jlHjSpSQL.findList(jlHjSpQuery);
			for(JlHjSpVO t : jlHjSpList){
				t.setState(3);
				t.setRemark("会见登记被人工取消");
				jlHjSpSQL.edit(t);
			}
			result.putJson(0);
			return result.toResult();
		}else if(jlHjDj.getFpFlag()==1){
			String sql="update SYS_HJ_LINE set hjid=null where hjid="+jlHjDj.getHjid();
			this.jdbcTemplate.update(sql);
			
			// 查看当前会见登记是否有审批，有的话就删除
			JlHjSpVO jlHjSpQuery = new JlHjSpVO();
			jlHjSpQuery.setHjid(jlHjDj.getHjid());
			List<JlHjSpVO> jlHjSpList = jlHjSpSQL.findList(jlHjSpQuery);
			for(JlHjSpVO t : jlHjSpList){
				t.setState(3);
				t.setRemark("会见登记被人工取消");
				jlHjSpSQL.edit(t);
			}
			
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
		if(StringUtils.isNotBlank(model.getFrNo())){
			String str = model.getFrNo();
			leftJoinWhere.append(" AND a.FR_No LIKE '%"+str+"%'");
			model.setFrNo(null);
		}
		if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		leftJoinWhere.append(" AND (a.FR_Name LIKE '%"+str+"%' OR dbo.f_get_fryp(a.FR_Name,'"+str+"') =1 )");
    		model.setFrName(null);
    	}
		model.setLeftJoinField(leftJoinField.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		List<JlHjDjVO> list = (List<JlHjDjVO>) map.get("list");
		String qsInfo="";
		for(JlHjDjVO t :list){
			if(StringUtils.isNotBlank(t.getQsInfo1())){
				qsInfo+=t.getQsInfo1()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo2())){
				qsInfo+=t.getQsInfo2()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo3())){
				qsInfo+=t.getQsInfo3()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo4())){
				qsInfo+=t.getQsInfo4()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo5())){
				qsInfo+=t.getQsInfo5()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo6())){
				qsInfo+=t.getQsInfo6()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo7())){
				qsInfo+=t.getQsInfo7()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo8())){
				qsInfo+=t.getQsInfo8()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo9())){
				qsInfo+=t.getQsInfo9()+";";
			}
			t.setQsInfo(qsInfo);
		}
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
	    		WhereJlQs.append(" AND (a.QS_Name LIKE '%"+qsName+"%' OR dbo.f_get_fryp(a.QS_Name,'"+qsName+"') =1 )");
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
				return new HashMap();
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
    		Where.append(" AND (a.FR_Name LIKE '%"+str+"%' OR dbo.f_get_fryp(a.FR_Name,'"+str+"') =1 )");
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
			String sql="update SYS_HJ_LINE set hjid=null where hjid="+jlHjDj.getHjid();
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
		if(jlHjDj.getHjMode()==1){
			sysHjLine.setLineType(0);
		}else{
			sysHjLine.setLineType(1);
		}
		sysHjLine.setLeftJoinWhere(" AND hjid is null");
		List<SysHjLineVO> sysHjLineList = sysHjLineSQL.findList(sysHjLine, null, null, "ASC");
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
		result.msg("授权成功");
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
		result.msg("已取消授权");
		return result.toResult();
	}
	
	
	public Map<String, Object> findPojoByLog(JlHjDjVO model, Integer pageSize, Integer pageNum){
		StringBuffer where = new StringBuffer();
		if(StringUtils.isNotBlank(model.getFrNo())){
			String str = model.getFrNo();
			where.append(" AND a.FR_No LIKE '%"+str+"%' ");
			model.setFrNo(null);
		}
		if(StringUtils.isNotBlank(model.getFrName())){
			String str = model.getFrName();
			where.append(" AND a.FR_Name LIKE '%"+str+"%' ");
			model.setFrName(null);
		}
		if(StringUtils.isNotBlank(model.getQsName())){
			String str = model.getQsName();
			where.append(" AND ("
					+ "a.QS_Info1 LIKE '%"+str+"%' "
					+ "OR a.QS_Info2  LIKE '%"+str+"%' "
					+ "OR a.QS_Info3  LIKE '%"+str+"%' "
					+ "OR a.QS_Info4  LIKE '%"+str+"%' "
					+ "OR a.QS_Info5  LIKE '%"+str+"%' "
					+ "OR a.QS_Info6  LIKE '%"+str+"%' "
					+ "OR a.QS_Info7  LIKE '%"+str+"%' "
					+ "OR a.QS_Info8  LIKE '%"+str+"%' "
					+ "OR a.QS_Info9  LIKE '%"+str+"%' "
					+ ") ");
			model.setQsName(null);
		}
		model.setLeftJoinWhere(where.toString());
		Map<String, Object> map =this.findPojo(model, pageSize, pageNum);
		List<JlHjDjVO> list = (List<JlHjDjVO>) map.get("list");
		for(JlHjDjVO t : list){
			String qsInfo = "";
			if(StringUtils.isNotBlank(t.getQsInfo1())){
				qsInfo+=t.getQsInfo1()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo2())){
				qsInfo+=t.getQsInfo2()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo3())){
				qsInfo+=t.getQsInfo3()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo4())){
				qsInfo+=t.getQsInfo4()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo5())){
				qsInfo+=t.getQsInfo5()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo6())){
				qsInfo+=t.getQsInfo6()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo7())){
				qsInfo+=t.getQsInfo7()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo8())){
				qsInfo+=t.getQsInfo8()+";";
			}
			if(StringUtils.isNotBlank(t.getQsInfo9())){
				qsInfo+=t.getQsInfo9()+";";
			}
			t.setQsInfo(qsInfo);
		}
		return map;
	}
	
	public void exportExcelByLog(JlHjDjVO model, 
			HttpServletRequest request, HttpServletResponse response){

		StringBuffer where = new StringBuffer();
		if(StringUtils.isNotBlank(model.getFrNo())){
			String str = model.getFrNo();
			where.append(" AND a.FR_No LIKE '%"+str+"%' ");
			model.setFrNo(null);
		}
		if(StringUtils.isNotBlank(model.getFrName())){
			String str = model.getFrName();
			where.append(" AND (a.FR_Name LIKE '%"+str+"%' OR dbo.f_get_fryp(a.FR_Name,'"+str+"') =1 )");
			model.setFrName(null);
		}
		if(StringUtils.isNotBlank(model.getQsName())){
			String str = model.getQsName();
			where.append(" AND ("
					+ "a.QS_Info1 LIKE '%"+str+"%' "
					+ "OR a.QS_Info2  LIKE '%"+str+"%' "
					+ "OR a.QS_Info3  LIKE '%"+str+"%' "
					+ "OR a.QS_Info4  LIKE '%"+str+"%' "
					+ "OR a.QS_Info5  LIKE '%"+str+"%' "
					+ "OR a.QS_Info6  LIKE '%"+str+"%' "
					+ "OR a.QS_Info7  LIKE '%"+str+"%' "
					+ "OR a.QS_Info8  LIKE '%"+str+"%' "
					+ "OR a.QS_Info9  LIKE '%"+str+"%' "
					+ ") ");
			model.setQsName(null);
		}
		model.setLeftJoinWhere(where.toString());
		List<JlHjDjVO> list = this.findList(model);
		
		String fileName =  "登记记录.xls";
		
		OutputStream out = null;
		
		try {
			
			// EXCEL START
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet();
			CellStyle cellStyle = book.createCellStyle();
			cellStyle.setDataFormat(book.createDataFormat().getFormat("yyyy-MM-dd"));
			// 设置标题
			List<String> title = new ArrayList<String>();
			title.add("会见编号");
			title.add("监区");
			title.add("罪犯编号");
			title.add("罪犯姓名");
			title.add("亲属信息");
			title.add("会见时长(分钟)");
			title.add("会见类型");
			title.add("会见说明");
			title.add("登记时间");
			title.add("登记人");
			title.add("取消原因");
			title.add("会见方式");
			// 标题 start
			HSSFRow row1 = sheet.createRow(0);
			for(int i=0; i<title.size(); i++){
				String t = title.get(i);
				HSSFCell cell = row1.createCell(i);
				cell.setCellValue(t);
			}
			// 标题 end
			
			// 记录 start
			for(int i=0; i<list.size(); i++){
				JlHjDjVO jlHjDj = list.get(i);
				HSSFRow row2 = sheet.createRow(i+1);
				
				HSSFCell cell0 = row2.createCell(0);
				cell0.setCellValue(jlHjDj.getHjIndex()+"");
					
				HSSFCell cell1 = row2.createCell(1);
				cell1.setCellValue(jlHjDj.getJqName()!=null?jlHjDj.getJqName():"");
				
				HSSFCell cell2 = row2.createCell(2);
				cell2.setCellValue(jlHjDj.getFrNo());
				
				HSSFCell cell3 = row2.createCell(3);
				cell3.setCellValue(jlHjDj.getFrName()!=null?jlHjDj.getFrName():"");
				
				String qsInfo = "";
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo1())){
					qsInfo+=jlHjDj.getQsInfo1()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo2())){
					qsInfo+=jlHjDj.getQsInfo2()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo3())){
					qsInfo+=jlHjDj.getQsInfo3()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo4())){
					qsInfo+=jlHjDj.getQsInfo4()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo5())){
					qsInfo+=jlHjDj.getQsInfo5()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo6())){
					qsInfo+=jlHjDj.getQsInfo6()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo7())){
					qsInfo+=jlHjDj.getQsInfo7()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo8())){
					qsInfo+=jlHjDj.getQsInfo8()+";";
				}
				if(StringUtils.isNotBlank(jlHjDj.getQsInfo9())){
					qsInfo+=jlHjDj.getQsInfo9()+";";
				}
				HSSFCell cell4 = row2.createCell(4);
				cell4.setCellValue(qsInfo);
				
				HSSFCell cell5 = row2.createCell(5);
				cell5.setCellValue(jlHjDj.getHjTime()!=null?jlHjDj.getHjTime()/60:0);
				
				String hjType="";
				if(jlHjDj.getHjType()==1){
					hjType="亲属会见";
				}else if(jlHjDj.getHjType()==2){
					hjType="监护人会见";
				}else if(jlHjDj.getHjType()==3){
					hjType="律师会见";
				}else if(jlHjDj.getHjType()==4){
					hjType="使领馆探视";
				}else if(jlHjDj.getHjType()==5){
					hjType="提审会见";
				}else if(jlHjDj.getHjType()==6){
					hjType="公务会见";
				}else if(jlHjDj.getHjType()==9){
					hjType="特批会见";
				}else if(jlHjDj.getHjType()==99){
					hjType="其他会见";
				}
				HSSFCell cell6 = row2.createCell(6);
				cell6.setCellValue(hjType);
				
				HSSFCell cell7 = row2.createCell(7);
				cell7.setCellValue(jlHjDj.getHjInfo()!=null?jlHjDj.getHjInfo():"");
				
				HSSFCell cell8 = row2.createCell(8);
				cell8.setCellValue(jlHjDj.getDjTime()!=null?DateUtil.getDefault(jlHjDj.getDjTime()):"");
				
				HSSFCell cell9 = row2.createCell(9);
				cell9.setCellValue(jlHjDj.getDjUser()!=null?jlHjDj.getDjUser():"");
				
				HSSFCell cell10 = row2.createCell(10);
				cell10.setCellValue(jlHjDj.getCancelInfo()!=null?jlHjDj.getCancelInfo():"");
				
				String hjMode="";
				if(jlHjDj.getHjMode()!=null && jlHjDj.getHjMode()==1){
					hjMode = "隔离会见";
				}else if(jlHjDj.getHjMode()!=null && jlHjDj.getHjMode()==2){
					hjMode = "非隔离会见";
				}else if(jlHjDj.getHjMode()!=null && jlHjDj.getHjMode()==3){
					hjMode = "远程视频会见";
				}else if(jlHjDj.getHjMode()!=null && jlHjDj.getHjMode()==4){
					hjMode = "其他方式";
				}
				HSSFCell cell11 = row2.createCell(11);
				cell11.setCellValue(hjMode);
			}
			
			// 处理不同浏览器中文名称编码
			String userAgent=request.getHeader("USER-AGENT");
			if(userAgent.indexOf("Chrome")!=-1 || userAgent.indexOf("Safari")!=-1 || userAgent.indexOf("Firefox")!=-1){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}else{
				//fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				fileName = URLEncoder.encode(fileName,"UTF8");
			}
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setHeader("Cache-Control","no-cache");//设置头
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			out = response.getOutputStream();
			book.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e2) {
				}
			}
		}
		System.out.println("导出完成");
	
	}
	
	public String getQsListByHjid(Long hjid){
		Result result = new Result();
		
		JlHjDjVO jlHjDj = this.findOne(hjid);
		if(StringUtils.isNotBlank(jlHjDj.getFrNo())){
			JlQsVO jlQs = new JlQsVO();
			jlQs.setFrNo(jlHjDj.getFrNo());
			List<JlQsVO> qsList = jlQsSQL.findList(jlQs);
			List<JlQsVO> checkedQsList = new ArrayList<>();
			for(JlQsVO t : qsList){
				if(jlHjDj.getQsInfo1() != null && t.getQsName().equals(jlHjDj.getQsInfo1().substring(jlHjDj.getQsInfo1().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo2() != null && t.getQsName().equals(jlHjDj.getQsInfo2().substring(jlHjDj.getQsInfo2().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo3() != null && t.getQsName().equals(jlHjDj.getQsInfo3().substring(jlHjDj.getQsInfo3().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo4() != null && t.getQsName().equals(jlHjDj.getQsInfo4().substring(jlHjDj.getQsInfo4().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo5() != null && t.getQsName().equals(jlHjDj.getQsInfo5().substring(jlHjDj.getQsInfo5().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo6() != null && t.getQsName().equals(jlHjDj.getQsInfo6().substring(jlHjDj.getQsInfo6().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo7() != null && t.getQsName().equals(jlHjDj.getQsInfo7().substring(jlHjDj.getQsInfo7().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo8() != null && t.getQsName().equals(jlHjDj.getQsInfo8().substring(jlHjDj.getQsInfo8().indexOf("]")+1))){
					checkedQsList.add(t);
				}
				else if(jlHjDj.getQsInfo9() != null && t.getQsName().equals(jlHjDj.getQsInfo9().substring(jlHjDj.getQsInfo9().indexOf("]")+1))){
					checkedQsList.add(t);
				}
			}
			result.putData(checkedQsList);
		}
		return result.toResult();
	}
	
	public String editDj(Long hjid, Integer hjTime, Integer hjType, String hjInfo, String qsIds){
		Result result = new Result();
		JlHjDjVO model = new JlHjDjVO();
		if(hjid==null){
			result.error(Result.error_102);
			return result.toResult();
		}
		model.setHjid(hjid);
		if(hjTime!=null){
			model.setHjTime(hjTime*60);
		}
		model.setHjType(hjType);
		model.setHjInfo(hjInfo);
		
		model.setQsInfo1("");
		model.setQsCard1("");
		model.setQsInfo2("");
		model.setQsCard2("");
		model.setQsInfo3("");
		model.setQsCard3("");
		model.setQsInfo4("");
		model.setQsCard4("");
		model.setQsInfo5("");
		model.setQsCard5("");
		model.setQsInfo6("");
		model.setQsCard6("");
		model.setQsInfo7("");
		model.setQsCard7("");
		model.setQsInfo8("");
		model.setQsCard8("");
		model.setQsInfo9("");
		model.setQsCard9("");
		
		String[] qsIdss = qsIds.split(",");
		for(int i=0; i<qsIdss.length;i++){ // 亲属
			JlQsVO jlQs = jlQsSQL.findOne(qsIdss[i]);
			String gx = StringUtils.isNotBlank(jlQs.getGx())?"["+jlQs.getGx()+"]":"";
			String name = StringUtils.isNotBlank(jlQs.getQsName())?jlQs.getQsName():"";
			if(i==0){
				model.setQsInfo1(gx+name);
				model.setQsCard1(jlQs.getQsCard());
			}else if(i==1){
				model.setQsInfo2(gx+name);
				model.setQsCard2(jlQs.getQsCard());
			}else if(i==2){
				model.setQsInfo3(gx+name);
				model.setQsCard3(jlQs.getQsCard());
			}else if(i==3){
				model.setQsInfo4(gx+name);
				model.setQsCard4(jlQs.getQsCard());
			}else if(i==4){
				model.setQsInfo5(gx+name);
				model.setQsCard5(jlQs.getQsCard());
			}else if(i==5){
				model.setQsInfo6(gx+name);
				model.setQsCard6(jlQs.getQsCard());
			}else if(i==6){
				model.setQsInfo7(gx+name);
				model.setQsCard7(jlQs.getQsCard());
			}else if(i==7){
				model.setQsInfo8(gx+name);
				model.setQsCard8(jlQs.getQsCard());
			}else if(i==8){
				model.setQsInfo9(gx+name);
				model.setQsCard9(jlQs.getQsCard());
			}
		}
		this.edit(model);
		return result.toResult();
	}
	
	@Override
	public Map<String, Object> findPojoByNotice(JlHjDjVO model, Integer pageSize, Integer pageNum) {
		StringBuffer leftJoinField = new StringBuffer(); // 字段 
		leftJoinField.append(",dbo.get_ck(a.FP_Line_No,a.JY) as zw");
		
		model.setLeftJoinField(leftJoinField.toString());
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
			t.setQsInfo(qsName);
			t.setHjTime(t.getHjTime()/60);
		}
		return map;
	}
	
	public String editTz(JlHjDjVO model){
		String overTime = Config.getPropertiesValue("noticeOvertime"); //超时分钟
		Result result = new Result();
		SysUserVO user = TokenUser.getUser();
		model.setPageTzUserNo(user.getUserNo());
		model.setPageTzUserName(user.getUserName());
		model.setPageTzTime(new Date());
		if(StringUtils.isNotBlank(overTime)){
			Date overDate = DateUtil.addMin(model.getDjTime(), Integer.parseInt(overTime));//超时时间
			if(new Date().before(overDate)){ //未超时
				model.setIsOverTime(0);
			}else{//超时
				model.setIsOverTime(1);
			}
		}
		this.edit(model);
		return result.toResult();
	}

	
}
