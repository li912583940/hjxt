package com.sl.ue.web.hj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.WebContextUtil;
import com.sl.ue.util.http.token.JqRoleManager;

@RestController
@RequestMapping("/hjReport")
public class HjReportWeb extends Result{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JlHjDjService  jlHjDjSQL;
	
	@Autowired
    private JlHjRecService jlHjRecSQL;
	
	@RequestMapping("/findReport")
    public String findReport(String years, String frNo, String frName, String jq, Integer hjType, Integer hjMode){
		
		String where = "";
		String recWhere = "";
		String djWhere = "";
    	String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
		JqRoleManager jqRoleManager = new JqRoleManager();
		String jqs = jqRoleManager.getJqs(token);
		if("admin".equals(jqs)){
		}else if(StringUtils.isBlank(jqs)){
			where+=" AND 1<>1 ";
		}else if(StringUtils.isNotBlank(jqs)){
			where+=" AND JQ_No in ("+jqs+") ";
		}
		
		if(StringUtils.isNotBlank(frNo)){
			where+=" AND FR_No='"+frNo+"'";
		}
		if(StringUtils.isNotBlank(jq)){
			where+=" AND JQ_No='"+jq+"'";
		}
		if(StringUtils.isNotBlank(frName)){
			where+=" AND FR_Name='"+frName+"'";
		}
		if(hjType!=null){
			where+=" AND HJ_Type="+hjType;
		}
		String recTimedate= "";
		String djTimedate = "";
        if(StringUtils.isNotBlank(years)){ // 选了年份的查询条件
        	String dateStart = "";
    		String dateEnd = "";
        	String[] year = years.split(",");
        	if(year.length==1){ // 按某年的每月统计
        		dateStart = year[0] + "-01-01 00:00:00";
        		dateEnd = year[0] + "-12-31 23:59:59";
        		recTimedate = " (SELECT  CONVERT(VARCHAR(7),Call_Time_Start,120) as timedate";
        		djTimedate = " (SELECT  CONVERT(VARCHAR(7),DJ_Time,120) as timedate";
        	}else if(year.length==2){ // 按某月的每天统计
        		Calendar c= Calendar.getInstance();
        		c.set(Calendar.YEAR, Integer.parseInt(year[0]));
        		c.set(Calendar.MONTH, Integer.parseInt(year[1])-1);
        		int lastDay = c.getActualMaximum(Calendar.DATE);
        		
        		dateStart = year[0] + "-" + (year[1].length()==1?"0"+year[1]:year[1]) + "-01 00:00:00";
        		dateEnd = year[0] + "-" + (year[1].length()==1?"0"+year[1]:year[1]) + "-"+lastDay+" 23:59:59";
        		recTimedate = " (SELECT  CONVERT(VARCHAR(10),Call_Time_Start,120) as timedate";
        		djTimedate = " (SELECT  CONVERT(VARCHAR(10),DJ_Time,120) as timedate";
        	}
        	recWhere= where + " AND Call_Time_Start>='"+dateStart+"'";
        	recWhere+= " AND Call_Time_Start<='"+dateEnd+"'";
        	
        	djWhere= where + " AND DJ_Time>='"+dateStart+"'";
        	djWhere+= " AND DJ_Time<='"+dateEnd+"'";
        	
        }else{// 没选年份的查询条件  直接每年的总数
        	recTimedate = " (SELECT  CONVERT(VARCHAR(4),Call_Time_Start,120) as timedate";
        	djTimedate = " (SELECT  CONVERT(VARCHAR(4),DJ_Time,120) as timedate";
        	recWhere= where;
        	djWhere= where;
        }
        String sql = "SELECT ISNULL(count(*),0) AS count,timedate from"
    			+   recTimedate
    			+ 	" FROM JL_HJ_REC"
    			+ 	" where 1=1"
    			+   recWhere
    			+ 	" ) as aa"
    			+ " group by aa.timedate";
    	List<Map<String, Object>> hjRecList = jdbcTemplate.queryForList(sql);
    	
    	
    	
    	//查询登记记录
    	String djSql = "SELECT ISNULL(count(*),0) AS count,timedate from"
    			+   djTimedate
    			+ 	" FROM JL_HJ_DJ"
    			+ 	" where 1=1"
    			+   djWhere
    			+ 	" ) as aa"
    			+ " group by aa.timedate";
    	List<Map<String, Object>> hjdjList = jdbcTemplate.queryForList(djSql);
    	
    	Set<String> dateList = new TreeSet<>(); //X轴坐标 日期
    	for(Map<String, Object> map : hjRecList){
    		dateList.add(map.get("timedate")+"");
    	}
    	for(Map<String, Object> map : hjdjList){
    		dateList.add(map.get("timedate")+"");
    	}
    	this.putData("dateList", dateList);
    	
    	Map<String, Integer> hjRecM = new LinkedHashMap<>();
    	Map<String, Integer> hjdjM = new LinkedHashMap<>();
    	for(String s : dateList){
    		hjRecM.put(s, 0);
    		hjdjM.put(s, 0);
    	}
    	for(Map<String, Object> map : hjRecList){
    		hjRecM.put(map.get("timedate")+"", (int)map.get("count"));
    	}
    	for(Map<String, Object> map : hjdjList){
    		hjdjM.put(map.get("timedate")+"", (int)map.get("count"));
    	} 
    	List<Integer> hjRecL = new ArrayList<>();  // Y轴坐标 会见记录
    	for(Integer i : hjRecM.values()){
    		hjRecL.add(i);
    	}
    	this.putData("hjRecList", hjRecL);
    	
    	List<Integer> hjdjL = new ArrayList<>(); // Y轴坐标 会见登记
    	for(Integer i : hjdjM.values()){
    		hjdjL.add(i);
    	}
    	this.putData("hjdjList", hjdjL);
		return this.toResult();
    }
	
	@RequestMapping("/findYearList")
	public String findYearList(){
		List<JlHjDjVO> list = jlHjDjSQL.findList(new JlHjDjVO(), 1, 1, "ASC");
		if(list.size()>0){
			JlHjDjVO jlHjDj = list.get(0);
			Date djTime = jlHjDj.getDjTime();
			Calendar c = Calendar.getInstance();
			c.setTime(djTime);
			int startYear = c.get(Calendar.YEAR);
			int startMonth = c.get(Calendar.MONTH)+1;
			
			c.setTime(new Date());
			int nowYear = c.get(Calendar.YEAR);
			int nowMonth = c.get(Calendar.MONTH)+1;
			List<Map<String, Object>> yearList= new ArrayList<>();
			for(int i=startYear;i<=nowYear;i++){
				Map<String, Object> yearMap = new HashMap<>();
				if(i==startYear){
					yearMap.put("value", i);
					yearMap.put("label", i+"年");
					List<Map<String, Object>> monthList = new ArrayList<>();
					for(int j=startMonth;j<=12;j++){
						Map<String, Object> monthMap = new HashMap<>();
						monthMap.put("value", j);
						monthMap.put("label", j+"月");
						monthList.add(monthMap);
					}
					yearMap.put("children", monthList);
					yearList.add(yearMap);
				}else if(i==nowYear){
					yearMap.put("value", i);
					yearMap.put("label", i+"年");
					List<Map<String, Object>> monthList = new ArrayList<>();
					for(int j=1;j<=nowMonth;j++){
						Map<String, Object> monthMap = new HashMap<>();
						monthMap.put("value", j);
						monthMap.put("label", j+"月");
						monthList.add(monthMap);
					}
					yearMap.put("children", monthList);
					yearList.add(yearMap);
				}else{
					yearMap.put("value", i);
					yearMap.put("label", i+"年");
					List<Map<String, Object>> monthList = new ArrayList<>();
					for(int j=1;j<=12;j++){
						Map<String, Object> monthMap = new HashMap<>();
						monthMap.put("value", j);
						monthMap.put("label", j+"月");
						monthList.add(monthMap);
					}
					yearMap.put("children", monthList);
					yearList.add(yearMap);
				}
			}
			this.putData(yearList);
		}
		return this.toResult();
	}
}
