package com.sl.ue.service.jl.sqlImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjQsVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjInfoVO;
import com.sl.ue.entity.jl.vo.JlHjMonVO;
import com.sl.ue.entity.jl.vo.JlHjRecRatingInfoVO;
import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjDjQsService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjInfoService;
import com.sl.ue.service.jl.JlHjRecRatingInfoService;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.Config;
import com.sl.ue.util.Constants;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.WebContextUtil;
import com.sl.ue.util.http.token.JqRoleManager;
import com.sl.ue.util.http.token.TokenUser;

@Service("jlHjRecSQL")
public class JlHjRecServiceImpl extends BaseSqlImpl<JlHjRecVO> implements JlHjRecService{

	@Autowired
	private JlHjInfoService jlHjInfoSQL;
	@Autowired
	private SysHjServerService sysHjServerSQL;
	@Autowired
	private JlHjRecRatingInfoService jlHjRecRatingInfoSQL;
	@Autowired
	private JlHjDjQsService jlHjDjQsSQL;
	@Autowired
	private JlHjDjService jlHjDjSQL;
	@Autowired
	private SysLogService sysLogSQL;
	
	@Override
	public Map<String, Object> findPojoLeft(JlHjRecVO model, Integer pageSize, Integer pageNum) {
		StringBuffer leftJoinField = new StringBuffer();
		StringBuffer leftJoinTable = new StringBuffer();
		StringBuffer leftJoinWhere = new StringBuffer();
		
		leftJoinField.append(",b.FR_GJ as frGj");
		leftJoinField.append(",b.info_hkfl as infoHkfl");
		leftJoinField.append(",c.HJ_Type as hjType");
		
		leftJoinTable.append(" LEFT JOIN JL_FR b ON a.FR_No=b.FR_No ");
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ c ON a.HJID=c.HJID ");
		
    	if(StringUtils.isNotBlank(model.getCallTimeStart())){ // 开始时间
    		leftJoinWhere.append(" AND a.Call_Time_Start>='"+ model.getCallTimeStart() + "' ");
    		model.setCallTimeStart(null);
    	}
    	if(StringUtils.isNotBlank(model.getCallTimeEnd())){ // 结束时间
    		leftJoinWhere.append(" AND a.Call_Time_Start<='"+ model.getCallTimeEnd() + "' ");
    		model.setCallTimeEnd(null);
    	}
    	if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		leftJoinWhere.append(" AND (a.FR_Name LIKE '%"+str+"%'  OR dbo.f_get_fryp(a.FR_Name,'"+str+"') =1 )");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(model.getQsName())){
    		String str = model.getQsName();
    		leftJoinWhere.append(" AND (a.QS_Info1 LIKE '%"+str+"%' OR a.QS_Info2 LIKE '%"+str+"%' OR a.QS_Info3 LIKE '%"+str+"%')");
    		model.setQsName(null);
    	}
    	if(StringUtils.isNotBlank(model.getFrGj())){
    		String str = model.getFrGj();
    		leftJoinWhere.append(" AND b.FR_GJ LIKE '%"+str+"%'");
    		model.setFrGj(null);
    	}
    	if(StringUtils.isNotBlank(model.getInfoHkfl())){
    		String str = model.getInfoHkfl();
    		leftJoinWhere.append(" AND b.info_hkfl LIKE '%"+str+"%'");
    		model.setInfoHkfl(null);
    	}
    	if(model.getHjType()!=null){
    		leftJoinWhere.append(" AND c.HJ_Type="+model.getHjType());
    		model.setHjType(null);
    	}
    	model.setLeftJoinField(leftJoinField.toString());
    	model.setLeftJoinTable(leftJoinTable.toString());
    	model.setLeftJoinWhere(leftJoinWhere.toString());
    	Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
    	List<JlHjRecVO> list = (List<JlHjRecVO>) map.get("list");
    	List<SysHjServerVO> sysHjServerList = sysHjServerSQL.findList(new SysHjServerVO());
    	
    	String callRecPath = Config.getPropertiesValue("callRecfile");
    	String callVideoPath1 = Config.getPropertiesValue("callVideofile1");
    	String callVideoPath2 = Config.getPropertiesValue("callVideofile2");
    	for(JlHjRecVO hjRec : list){
    		hjRec.setCallRecfileUrl("");
    		hjRec.setCallVideofile1Url("");
    		hjRec.setCallVideofile2Url("");
    		
    		// 录音 录像文件路径处理
    		for(SysHjServerVO hjServer: sysHjServerList){
    			if(hjRec.getJy().equals(hjServer.getServerName())){
    				if(StringUtils.isNotBlank(hjRec.getCallRecfile())){
    					//先查看文件是否存在， 不存在就直接提示了
    					File file = new File(hjRec.getCallRecfile());
    					if(file.exists()){
    						String end =hjRec.getCallRecfile().replace("\\", "/");
            				end = end.substring(end.indexOf("/")+1);
            				end = end.substring(end.indexOf("/"));
            				String url = hjServer.getRecurl()+callRecPath+end;
            				hjRec.setCallRecfileUrl(url);
    					}
    				}
    				if(StringUtils.isNotBlank(hjRec.getCallVideofile1())){
    					File file = new File(hjRec.getCallVideofile1());
    					if(file.exists()){
    						String end = hjRec.getCallVideofile1().replace("\\", "/");
        					end = end.substring(end.indexOf("/")+1);
            				end = end.substring(end.indexOf("/"));
            				String url = hjServer.getRecurl()+callVideoPath1+end;
            				hjRec.setCallVideofile1Url(url);
    					}
    					
    				}
    				if(StringUtils.isNotBlank(hjRec.getCallVideofile2())){
    					File file = new File(hjRec.getCallVideofile2());
    					if(file.exists()){
    						String end = hjRec.getCallVideofile2().replace("\\", "/");
        					end = end.substring(end.indexOf("/")+1);
            				end = end.substring(end.indexOf("/"));
            				String url = hjServer.getRecurl()+callVideoPath2+end;
            				hjRec.setCallVideofile2Url(url);
    					}
    					
    				}
    			}
    		}
    		
    		// 亲属个数
    		int qsIndex = 0;
    		StringBuffer qsInfo = new StringBuffer();
    		if(StringUtils.isNotBlank(hjRec.getQsInfo1())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo1()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo2())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo2()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo3())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo3()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo4())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo4()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo5())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo5()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo6())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo6()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo7())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo7()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo8())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo8()+";");
    		}
    		if(StringUtils.isNotBlank(hjRec.getQsInfo9())){
    			qsIndex++;
    			qsInfo.append(hjRec.getQsInfo9()+";");
    		}
    		hjRec.setQsIndex(qsIndex);
    		hjRec.setQsInfo(StringUtil.lastComma(qsInfo.toString()));
    	}
		return map;
	}
	
	public String getZs(String callId){
		Result result = new Result();
		if(StringUtils.isBlank(callId)){
			result.error(Result.error_102);
			return result.toResult();
		}
		SysUserVO sysUser = TokenUser.getUser();
		JlHjInfoVO jlHjInfo = new JlHjInfoVO();
		jlHjInfo.setCallId(callId);
		jlHjInfo.setUserNo(sysUser.getUserNo());
		List<JlHjInfoVO> jlHjInfoList = jlHjInfoSQL.findList(jlHjInfo);
		if(jlHjInfoList.size()>0){
			jlHjInfo = jlHjInfoList.get(0);
		}
		result.putJson(jlHjInfo);
		return result.toResult();
	}

	public String addRecordFlag(String callId, String writeTxt){
		Result result = new Result();
		if(StringUtils.isBlank(callId)){
			result.error(Result.error_102);
			return result.toResult();
		}
		SysUserVO sysUser = TokenUser.getUser();
		JlHjInfoVO jlHjInfo = new JlHjInfoVO();
		jlHjInfo.setCallId(callId);
		jlHjInfo.setUserNo(sysUser.getUserNo());
		List<JlHjInfoVO> jlHjInfoList = jlHjInfoSQL.findList(jlHjInfo);
		if(jlHjInfoList.size()>0){
			jlHjInfo = jlHjInfoList.get(0);
			jlHjInfo.setWriteTxt(writeTxt);
			jlHjInfo.setCreateTime(new Date());
			jlHjInfoSQL.edit(jlHjInfo);
		}else{
			jlHjInfo.setUserName(sysUser.getUserName());
			jlHjInfo.setWriteTxt(writeTxt);
			jlHjInfoSQL.add(jlHjInfo);
		}
		return result.toResult();
	}
	
	public String getRatingState(String callId){
		Result result = new Result();
		if(StringUtils.isBlank(callId)){
			result.error(Result.error_102);
			return result.toResult();
		}
		SysUserVO sysUser = TokenUser.getUser();
		
		JlHjRecRatingInfoVO jlHjRecRatingInfo = new JlHjRecRatingInfoVO();
		jlHjRecRatingInfo.setCallId(callId);
		jlHjRecRatingInfo.setUserNo(sysUser.getUserNo());
		List<JlHjRecRatingInfoVO> list = jlHjRecRatingInfoSQL.findList(jlHjRecRatingInfo);
		if(list.size()>0){
			jlHjRecRatingInfo = list.get(0);
		}else{
			jlHjRecRatingInfo= new JlHjRecRatingInfoVO();
		}
		result.putJson("jlHjRecRatingInfo", jlHjRecRatingInfo);
		return result.toResult();
	}
	
	public String updateRatingState(Long webid, Integer recRatingState, String writeTxt){
		Result result = new Result();
		if(webid == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjRecVO jlHjRec = this.findOne(webid);
		if(jlHjRec == null){
			result.error(Result.error_103, "查询不到当前记录");
			return result.toResult();
		}
		jlHjRec.setRecRatingState(recRatingState);
		this.edit(jlHjRec);
		
		SysUserVO sysUser = TokenUser.getUser();
		
		JlHjRecRatingInfoVO jlHjRecRatingInfo = new JlHjRecRatingInfoVO();
		jlHjRecRatingInfo.setCallId(jlHjRec.getCallId());
		jlHjRecRatingInfo.setUserNo(sysUser.getUserNo());
		List<JlHjRecRatingInfoVO> list = jlHjRecRatingInfoSQL.findList(jlHjRecRatingInfo);
		if(list.size() >0){
			jlHjRecRatingInfo = list.get(0);
			jlHjRecRatingInfo.setCreateTime(new Date());
			jlHjRecRatingInfo.setWriteTxt(writeTxt);
			jlHjRecRatingInfoSQL.edit(jlHjRecRatingInfo);
		}else{
			jlHjRecRatingInfo.setUserName(sysUser.getUserName());
			jlHjRecRatingInfo.setWriteTxt(writeTxt);
			jlHjRecRatingInfo.setCreateTime(new Date());
			jlHjRecRatingInfoSQL.add(jlHjRecRatingInfo);
		}
		return result.toResult();
	}
	
    public String getOtherInfo(Long webid){
    	Result result = new Result();
		if(webid == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjRecVO jlHjRec = this.findOne(webid);
		
		if(jlHjRec.getHjid() != null){
			JlHjDjVO jlHjDj = new JlHjDjVO();
			jlHjDj.setHjid(jlHjRec.getHjid());
			List<JlHjDjVO> jlHjDjList = jlHjDjSQL.findList(jlHjDj);
			result.putJson("jlHjDjList", jlHjDjList);
			
			JlHjDjQsVO jlHjDjQs = new JlHjDjQsVO();
			jlHjDjQs.setHjId(jlHjRec.getHjid());
			List<JlHjDjQsVO> qsList = jlHjDjQsSQL.findList(jlHjDjQs);
			result.putData("jlHjDjQsList", qsList);
		}
		return result.toResult();
	}
    
    public void exportExcel(JlHjRecVO model, HttpServletRequest request, HttpServletResponse response){
    	StringBuffer leftJoinField = new StringBuffer();
		StringBuffer leftJoinTable = new StringBuffer();
		StringBuffer leftJoinWhere = new StringBuffer();
		
		leftJoinField.append(",b.FR_GJ as frGj");
		leftJoinField.append(",b.info_hkfl as infoHkfl");
		leftJoinField.append(",c.HJ_Type as hjType");
		
		leftJoinTable.append(" LEFT JOIN JL_FR b ON a.FR_No=b.FR_No ");
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ c ON a.HJID=c.HJID ");
		
    	if(StringUtils.isNotBlank(model.getCallTimeStart())){ // 开始时间
    		leftJoinWhere.append(" AND a.Call_Time_Start>='"+ model.getCallTimeStart() + "' ");
    		model.setCallTimeStart(null);
    	}
    	if(StringUtils.isNotBlank(model.getCallTimeEnd())){ // 结束时间
    		leftJoinWhere.append(" AND a.Call_Time_Start<='"+ model.getCallTimeEnd() + "' ");
    		model.setCallTimeEnd(null);
    	}
    	if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		leftJoinWhere.append(" AND (a.FR_Name LIKE '%"+str+"%'  OR dbo.f_get_fryp(a.FR_Name,'"+str+"') =1 )");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(model.getQsName())){
    		String str = model.getQsName();
    		leftJoinWhere.append(" AND (a.QS_Info1 LIKE '%"+str+"%' OR a.QS_Info2 LIKE '%"+str+"%' OR a.QS_Info3 LIKE '%"+str+"%')");
    		model.setQsName(null);
    	}
    	if(StringUtils.isNotBlank(model.getFrGj())){
    		String str = model.getFrGj();
    		leftJoinWhere.append(" AND b.FR_GJ LIKE '%"+str+"%'");
    		model.setFrGj(null);
    	}
    	if(StringUtils.isNotBlank(model.getInfoHkfl())){
    		String str = model.getInfoHkfl();
    		leftJoinWhere.append(" AND b.info_hkfl LIKE '%"+str+"%'");
    		model.setInfoHkfl(null);
    	}
    	if(model.getHjType()!=null){
    		leftJoinWhere.append(" AND c.HJ_Type="+model.getHjType());
    		model.setHjType(null);
    	}
    	model.setLeftJoinField(leftJoinField.toString());
    	model.setLeftJoinTable(leftJoinTable.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
		
		List<JlHjRecVO> jlHjRecList = this.findList(model);
		
		String fileName =  "会见记录.xls";
		
		OutputStream out = null;
		
		try {
			
			// EXCEL START
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet();
			CellStyle cellStyle = book.createCellStyle();
			cellStyle.setDataFormat(book.createDataFormat().getFormat("yyyy-MM-dd"));
			// 设置标题
			List<String> title = new ArrayList<String>();
			title.add("通话开始时间");
			title.add("通话结束时间");
			title.add("通话时长(秒)");
			title.add("座位");
			title.add("会见类型");
			title.add("监区");
			title.add("罪犯编号");
			title.add("罪犯姓名");
			title.add("亲属个数");
			title.add("亲属信息");
			title.add("警察信息");
			title.add("国籍");
			title.add("户口");
			// 标题 start
			HSSFRow row1 = sheet.createRow(0);
			for(int i=0; i<title.size(); i++){
				String t = title.get(i);
				HSSFCell cell = row1.createCell(i);
				cell.setCellValue(t);
			}
			// 标题 end
			
			// 记录 start
			for(int i=0; i<jlHjRecList.size(); i++){
				JlHjRecVO jlHjRec = jlHjRecList.get(i);
				HSSFRow row2 = sheet.createRow(i+1);
				
				HSSFCell cell0 = row2.createCell(0);
				cell0.setCellValue(jlHjRec.getCallTimeStart());
					
				HSSFCell cell1 = row2.createCell(1);
				cell1.setCellValue(jlHjRec.getCallTimeEnd());
				
				HSSFCell cell2 = row2.createCell(2);
				cell2.setCellValue(jlHjRec.getCallTimeLen());
				
				HSSFCell cell3 = row2.createCell(3);
				cell3.setCellValue(jlHjRec.getZw());
				
				HSSFCell cell4 = row2.createCell(4);
				cell4.setCellValue(jlHjRec.getHjType()==1?"严见":"宽见");
				
				HSSFCell cell5 = row2.createCell(5);
				cell5.setCellValue(jlHjRec.getJqName());
				
				HSSFCell cell6 = row2.createCell(6);
				cell6.setCellValue(jlHjRec.getFrNo());
				
				HSSFCell cell7 = row2.createCell(7);
				cell7.setCellValue(jlHjRec.getFrName());
				
				// 亲属个数
	    		int qsIndex = 0;
	    		StringBuffer qsInfo = new StringBuffer();
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo1())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo1()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo2())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo2()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo3())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo3()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo4())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo4()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo5())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo5()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo6())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo6()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo7())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo7()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo8())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo8()+";");
	    		}
	    		if(StringUtils.isNotBlank(jlHjRec.getQsInfo9())){
	    			qsIndex++;
	    			qsInfo.append(jlHjRec.getQsInfo9()+";");
	    		}
	    		
				HSSFCell cell8 = row2.createCell(8);
				cell8.setCellValue(qsIndex);
				
				HSSFCell cell9 = row2.createCell(9);
				cell9.setCellValue(qsInfo.toString());
				
				HSSFCell cell10 = row2.createCell(10);
				cell10.setCellValue(StringUtils.isNotBlank(jlHjRec.getYjNo())?jlHjRec.getYjNo()+"/"+jlHjRec.getYjName():"");
				
				HSSFCell cell11 = row2.createCell(11);
				cell11.setCellValue(StringUtils.isNotBlank(jlHjRec.getFrGj())?jlHjRec.getFrGj():"");
				
				HSSFCell cell12 = row2.createCell(12);
				cell12.setCellValue(StringUtils.isNotBlank(jlHjRec.getInfoHkfl())?jlHjRec.getInfoHkfl():"");
			}
			
			// 处理不同浏览器中文名称编码
			String userAgent=request.getHeader("USER-AGENT");
			if(userAgent.indexOf("Chrome")!=-1 || userAgent.indexOf("Safari")!=-1 || userAgent.indexOf("Firefox")!=-1){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}else{
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
		
	
    }
    
    
    public String getWeekCount(){
    	Result result = new Result();
    	String where = "";
    	String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
		JqRoleManager jqRoleManager = new JqRoleManager();
		String jqs = jqRoleManager.getJqs(token);
		if("admin".equals(jqs)){
		}else if(StringUtils.isBlank(jqs)){
			where+=" AND 1<>1 ";
		}else if(StringUtils.isNotBlank(jqs)){
			where+=" AND JQ_No in ("+jqs+") ";
		}
		
    	String sql = "SELECT ISNULL(count(*),0) AS count,timedate from"
    			+ " (SELECT  CONVERT(VARCHAR(10),Call_Time_Start,111) as timedate"
    			+ 	" FROM JL_HJ_REC"
    			+ 	" where Call_Time_Start>= convert(varchar(10),dateadd(day,-7,getdate()),120)"
    			+   where
    			+ 	" ) as aa"
    			+ " group by aa.timedate";
    	List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
    	result.putData(list);
    	return result.toResult();
    }
    
    public void downVideo(Long webid, HttpServletRequest request, HttpServletResponse response){
    	Result result = new Result();
    	JlHjRecVO jlHjRec = this.findOne(webid);
    	if(jlHjRec==null){
    		result.error(Result.error_103,"数据库查询不到此记录");
    		return;
    	}
    	List<File> fileList = new ArrayList();
    	if(StringUtils.isNotBlank(jlHjRec.getCallRecfile())){
    		File file = new File(jlHjRec.getCallRecfile());
    		if(file.exists()){
    			fileList.add(file);
    		}
    	}
    	if(StringUtils.isNotBlank(jlHjRec.getCallVideofile1())){
    		File file = new File(jlHjRec.getCallVideofile1());
    		if(file.exists()){
    			fileList.add(file);
    		}
    	}
    	if(StringUtils.isNotBlank(jlHjRec.getCallVideofile2())){
    		File file = new File(jlHjRec.getCallVideofile2());
    		if(file.exists()){
    			fileList.add(file);
    		}
    	}
    	if(fileList.size()==0){
    		return;
    	}
    	ZipOutputStream zos =null;
    	response.setHeader("Cache-Control","no-cache");//设置头
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream");
    	try {
    		zos = new ZipOutputStream(response.getOutputStream());
    		for (File srcFile : fileList) {
    			byte[] buf = new byte[1024*1024];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			 if(zos != null){
                 try {
                     zos.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
		}
    }
    
    public void downAudio(Long webid, HttpServletRequest request, HttpServletResponse response){
    	Result result = new Result();
    	JlHjRecVO jlHjRec = this.findOne(webid);
    	if(jlHjRec==null){
    		result.error(Result.error_103,"数据库查询不到此记录");
    		return;
    	}
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("下载录音");
		sysLog.setInfo("下载罪犯编号: "+jlHjRec.getFrNo()+"，罪犯姓名: "+jlHjRec.getFrName()+"；时间: "+jlHjRec.getCallTimeStart()+" 的录音");
		sysLog.setModel("会见记录");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
    	File file = null;
    	if(StringUtils.isNotBlank(jlHjRec.getCallRecfile())){
    		file = new File(jlHjRec.getCallRecfile());
    		if(!file.exists()){
    			return;
    		}
    	}
    	OutputStream out = null;
		BufferedInputStream in =null;
		try {
			String fileName ="录音.mp3";
			// 处理不同浏览器中文名称编码
			String userAgent=request.getHeader("USER-AGENT");
			if(userAgent.indexOf("Chrome")!=-1 || userAgent.indexOf("Safari")!=-1 || userAgent.indexOf("Firefox")!=-1){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}else{
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				//fileName = URLEncoder.encode(fileName,"UTF8");
			}
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setHeader("Cache-Control","no-cache");//设置头
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			out = response.getOutputStream();
			in = new BufferedInputStream(new FileInputStream(file));
			int l = 50*1024;//1M 默认，可在配置文件中设置此值大小
			//int byteCount = 0;
			byte[] buffer = new byte[l];
			int bytesRead = -1;//文件大小
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				//byteCount += bytesRead;
			}
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(in!=null){
					in.close();
				}
			}
			catch (IOException ex) {
			}
			try {
				if(out!=null){
					out.close();
				}
			}
			catch (IOException ex) {
			}
		}
    }
    
    public void downTest(Long webid, HttpServletRequest request, HttpServletResponse response){

    	Result result = new Result();
    	JlHjRecVO jlHjRec = this.findOne(webid);
    	if(jlHjRec==null){
    		result.error(Result.error_103,"数据库查询不到此记录");
    		return;
    	}
    	File file = null;
    	if(StringUtils.isNotBlank(jlHjRec.getCallVideofile1())){
    		file = new File(jlHjRec.getCallVideofile1());
    		if(!file.exists()){
    			return;
    		}
    	}
    	OutputStream out = null;
		BufferedInputStream in =null;
		try {
			String fileName ="录音.mp4";
			// 处理不同浏览器中文名称编码
			String userAgent=request.getHeader("USER-AGENT");
			if(userAgent.indexOf("Chrome")!=-1 || userAgent.indexOf("Safari")!=-1 || userAgent.indexOf("Firefox")!=-1){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}else{
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				//fileName = URLEncoder.encode(fileName,"UTF8");
			}
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setHeader("Cache-Control","no-cache");//设置头
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream");
			out = response.getOutputStream();
			in = new BufferedInputStream(new FileInputStream(file));
			int l = 2*1024*1024;//1M 默认，可在配置文件中设置此值大小
			//int byteCount = 0;
			byte[] buffer = new byte[l];
			int bytesRead = -1;//文件大小
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				//byteCount += bytesRead;
			}
			out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(in!=null){
					in.close();
				}
			}
			catch (IOException ex) {
			}
			try {
				if(out!=null){
					out.close();
				}
			}
			catch (IOException ex) {
			}
		}
    
    }
}
