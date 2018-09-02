﻿package com.slxt.rs.action;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.slxt.rs.form.HjRecordForm;
import com.slxt.rs.model.JlHjDjQs;
import com.slxt.rs.model.JlHjInfo;
import com.slxt.rs.model.JlHjMon;
import com.slxt.rs.model.JlHjRec;
import com.slxt.rs.model.JlHjRecAssessmentInfo;
import com.slxt.rs.model.JlHjRecRatingInfo;
import com.slxt.rs.model.JlJq;
import com.slxt.rs.model.JlQs;
import com.slxt.rs.model.SysHjServer;
import com.slxt.rs.model.SysLog;
import com.slxt.rs.model.SysUser;
import com.slxt.rs.svc.HjRecordService;
import com.slxt.rs.util.Constant;
import com.slxt.rs.vo.FrQs;
import com.slxt.rs.vo.JlHjInfoVO;
import com.slxt.rs.vo.JlHjMonVO;
import com.slxt.rs.vo.Page;
import com.slxt.rs.vo.RecordOtherInfoVO;
import com.slxt.rs.vo.RecordSearch;
import com.slxt.rs.vo.ZhuShiVo;
public class HjRecordAction extends DispatchAction{
	private HjRecordService hrs;
	public void setHrs(HjRecordService hrs) {
		this.hrs = hrs;
	}
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			HjRecordForm rscf = (HjRecordForm) form;
			Page page = new Page();
			page.setPageNo(1);
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = df1.format(date);
			String todayBegin = "";
			String todayEnd = "";
			int jqIsNodisable = 0;
			
			String frNo3 = request.getParameter("frNo3");
			if(frNo3!=null && !frNo3.trim().equals("")){
				rscf.setFrNo(frNo3);
			}
			String jq2 = request.getParameter("jq2");
			if(jq2!=null && !jq2.trim().equals("null")){
				rscf.setJqNo(jq2);
			}
			String jy2 = request.getParameter("jy2");
			if(jy2!=null && !jy2.trim().equals("null")){
				rscf.setJy(jy2);
			}
			String callTimeStart2 = request.getParameter("callTimeStart2");
			if(callTimeStart2!=null && !callTimeStart2.trim().equals("")){
				rscf.setCallTimeStart(callTimeStart2);
			}
			String callTimeEnd2 = request.getParameter("callTimeEnd2");
			if(callTimeEnd2!=null && !callTimeEnd2.trim().equals("")){
				rscf.setCallTimeEnd(callTimeEnd2);
			}
			String frName2 = request.getParameter("frName2");
			if(frName2!=null && !frName2.trim().equals("")){
				rscf.setFrName(frName2);
			}
			String zw2 = request.getParameter("zw2");
			if(zw2!=null && !zw2.trim().equals("")){
				rscf.setZw(zw2);
			}
			String qsName2 = request.getParameter("qsName2");
			if(qsName2!=null && !qsName2.trim().equals("")){
				rscf.setQsName(qsName2);
			}
			
			String pageNo2=request.getParameter("pageNo2");
			if(pageNo2!=null && !pageNo2.equals("-1")){
				page.setPageNo(Integer.parseInt(pageNo2));
			}
			String type2=request.getParameter("type2");
			if(type2!=null && !type2.trim().equals("null")){
				rscf.setType(type2);
			}
			String recRatingState2=request.getParameter("recRatingState2");
			if(recRatingState2!=null && !recRatingState2.trim().equals("null")){
				rscf.setRecRatingState(recRatingState2);
			}
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			if(recAssessmentState2!=null && !recAssessmentState2.trim().equals("null")){
				rscf.setRecAssessmentState(recAssessmentState2);
			}
			String recordOverTime2=request.getParameter("recordOverTime2");
			if(recordOverTime2!=null && !recordOverTime2.trim().equals("null")){
				rscf.setRecordOverTime(recordOverTime2);
			}
			if ((rscf.getRecordOverTime() != null && !rscf.getRecordOverTime().equals("null")) || (rscf.getRecAssessmentState() != null && !rscf.getRecAssessmentState().equals("null")) || (rscf.getRecRatingState() != null && !rscf.getRecRatingState().equals("null")) || rscf.getCallTimeEnd() != null || rscf.getCallTimeStart() != null  || (rscf.getType() != null && !rscf.getType().equals("null")) || (rscf.getJqNo() != null && !rscf.getJqNo().equals("null")) || rscf.getQsName() != null || (rscf.getJy() != null && !rscf.getJy().equals("null")) || rscf.getFrNo() != null || rscf.getFrName() != null) {
				StringBuffer str1 = new StringBuffer();
				if (rscf.getCallTimeStart() != null && !rscf.getCallTimeStart().trim().equals("")) {
					str1.append(" and bb.Call_Time_Start>='"+ rscf.getCallTimeStart() + "'");
					todayBegin = rscf.getCallTimeStart();
				}
				if (rscf.getCallTimeEnd() != null && !rscf.getCallTimeEnd().trim().equals("")) {
					str1.append(" and bb.Call_Time_Start<='"+ rscf.getCallTimeEnd() + "'");
					todayEnd = rscf.getCallTimeEnd();
				}
				if (rscf.getFrNo() != null && !rscf.getFrNo().equals("")) {
					str1.append(" and bb.FR_No='" + rscf.getFrNo() + "'");
				}
				if (rscf.getFrName() != null && !rscf.getFrName().trim().equals("")) {
					//str1.append(" and bb.FR_Name like '%" + rscf.getFrName() + "%'");
					str1.append("and (dbo.f_get_fryp(bb.FR_Name,'"+rscf.getFrName().trim()+"') =1 or bb.FR_Name like '%"+rscf.getFrName()+"%')");
				}
				if (rscf.getType() != null && !rscf.getType().equals("null")) {
					str1.append(" and bb.HJ_Type=" + Integer.parseInt(rscf.getType()));
				}
				if (rscf.getRecRatingState() != null && !rscf.getRecRatingState().equals("null")) {
					str1.append(" and bb.Rec_Rating_State=" + Integer.parseInt(rscf.getRecRatingState()));
				}
				if (rscf.getRecAssessmentState() != null && !rscf.getRecAssessmentState().equals("null")) {
					str1.append(" and bb.Rec_Assessment_State=" + Integer.parseInt(rscf.getRecAssessmentState()));
				}
				if (rscf.getRecordOverTime() != null && !rscf.getRecordOverTime().equals("null")) {
					str1.append(" and bb.recordOverTime=" + Integer.parseInt(rscf.getRecordOverTime()));
				}
				if (rscf.getJqNo() != null && !rscf.getJqNo().equals("null")) {
					str1.append(" and bb.JQ_No='" + rscf.getJqNo() + "'");
				}
				if (rscf.getJy() != null && !rscf.getJy().equals("null")) {
					str1.append(" and bb.JY='" + rscf.getJy() + "'");
					jqIsNodisable = 1;
					if (user.getIsSuper() == 1) {
						String hql1 = "from JlJq where jy='" + rscf.getJy() + "'";
						List<JlJq> list1 = hrs.searchAll(hql1);
						request.setAttribute("jlJqList", list1);
					}else if(user.getGroupNo().trim().equals("Admin")) {
						String hql1 = "from JlJq where jy='" + rscf.getJy() + "' and isTs!=1";
						List<JlJq> list1 = hrs.searchAll(hql1);
						request.setAttribute("jlJqList", list1);
					}else {
						String hql1 = "select jq from JlJq jq,SysUserJq sys where jq.jy='"+ rscf.getJy()+ "' and jq.jqNo=sys.jqNo and sys.groupNo='"+ user.getGroupNo() + "'";
						List<JlJq> list1 = hrs.searchAll(hql1);
						request.setAttribute("jlJqList", list1);
					}
				}
				if(rscf.getZw()!=null && !rscf.getZw().equals("")){
					str1.append(" and bb.zw='"+rscf.getZw()+"'");
				}
				if (rscf.getQsName() != null && !rscf.getQsName().equals("")) {
					str1.append(" and (bb.QS_Info1 like '%" + rscf.getQsName() + "%' or bb.QS_Info2 like '%"+ rscf.getQsName() + "%' or bb.QS_Info3 like '%"+ rscf.getQsName() + "%' or bb.QS_Info4 like '%"+ rscf.getQsName() + "%' or bb.QS_Info5 like '%"+ rscf.getQsName() + "%' or bb.QS_Info6 like '%"+ rscf.getQsName() + "%' or bb.QS_Info7 like '%"+ rscf.getQsName() + "%' or bb.QS_Info8 like '%"+ rscf.getQsName() + "%' or bb.QS_Info9 like '%"+ rscf.getQsName() + "%')");
				}
				StringBuffer str2 = new StringBuffer();
				StringBuffer str3 = new StringBuffer();
				if (user.getIsSuper() == 1) {
					str2.append("select rec.WebID,cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.JY,rec.JQ_No,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,rec.Call_RecFile,jl.JQ_Name,jlserver.IP,jlserver.RecUrl,rec.Call_ID as callID,rec.zw,rec.Call_VideoFile1,rec.Call_VideoFile2,dbo.get_VideoURL(line.VideoChan1_Server) as url1,dbo.get_VideoURL(line.VideoChan2_Server) as url2,rec.YJ_Name,rec.YJ_No,rec.Rec_Rating_State,rec.Rec_Assessment_State,dbo.get_Record_OverTime(rec.FR_No,rec.call_Time_End,rec.Rec_Assessment_State) as recordOverTime from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,SYS_HJ_LINE line where rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and rec.jy=line.jy and rec.line_no=line.line_no");
					str3.append(" select bb.WebID,bb.Call_Time_Start,bb.Call_Time_End,bb.HJ_Type,bb.Call_Time_Len,bb.JY,bb.JQ_No,bb.FR_No,bb.FR_Name,bb.QS_Info1,bb.QS_Info2,bb.QS_Info3,bb.QS_Info4,bb.QS_Info5,bb.QS_Info6,bb.QS_Info7,bb.QS_Info8,bb.QS_Info9,bb.Call_RecFile,bb.JQ_Name,bb.IP,bb.RecUrl,cast(bb.callID AS varchar(26)) AS callID,bb.zw,bb.Call_VideoFile1,bb.Call_VideoFile2,bb.url1,bb.url2,bb.YJ_Name,bb.YJ_No,bb.Rec_Rating_State,bb.Rec_Assessment_State,bb.recordOverTime from (").append(str2.toString()).append(") as bb");
					str3.append(" where 1=1" + str1.toString());
				} else if (user.getGroupNo().trim().equals("Admin")) {
					str2.append("select rec.WebID,cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.JY,rec.JQ_No,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,rec.Call_RecFile,jl.JQ_Name,jlserver.IP,jlserver.RecUrl,rec.Call_ID as callID,rec.zw,rec.Call_VideoFile1,rec.Call_VideoFile2,dbo.get_VideoURL(line.VideoChan1_Server) as url1,dbo.get_VideoURL(line.VideoChan2_Server) as url2,rec.YJ_Name,rec.YJ_No,rec.Rec_Rating_State,rec.Rec_Assessment_State,dbo.get_Record_OverTime(rec.FR_No,rec.call_Time_End,rec.Rec_Assessment_State) as recordOverTime,rec.HJID from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,SYS_HJ_LINE line where rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and rec.jy=line.jy and rec.line_no=line.line_no and jl.Is_Ts!=1");
					str3.append(" select bb.WebID,bb.Call_Time_Start,bb.Call_Time_End,bb.HJ_Type,bb.Call_Time_Len,bb.JY,bb.JQ_No,bb.FR_No,bb.FR_Name,bb.QS_Info1,bb.QS_Info2,bb.QS_Info3,bb.QS_Info4,bb.QS_Info5,bb.QS_Info6,bb.QS_Info7,bb.QS_Info8,bb.QS_Info9,bb.Call_RecFile,bb.JQ_Name,bb.IP,bb.RecUrl,cast(bb.callID AS varchar(26)) AS callID,bb.zw,bb.Call_VideoFile1,bb.Call_VideoFile2,bb.url1,bb.url2,bb.YJ_Name,bb.YJ_No,bb.Rec_Rating_State,bb.Rec_Assessment_State,bb.recordOverTime,bb.HJID from (").append(str2.toString()).append(") as bb");
					if(user.getIsBj()==1){
						str3.append(" where 1=1" + str1.toString());
					}else{
						str3.append(" where 1=1" + str1.toString() + " and bb.HJID not in (select HJID from JL_HJ_SP sp where sp.HJID=bb.HJID)");
					}
					
				} else {
					str2.append("select rec.WebID,cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.JY,rec.JQ_No,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,rec.Call_RecFile,jl.JQ_Name,jlserver.IP,jlserver.RecUrl,rec.Call_ID as callID,rec.zw,rec.Call_VideoFile1,rec.Call_VideoFile2,dbo.get_VideoURL(line.VideoChan1_Server) as url1,dbo.get_VideoURL(line.VideoChan2_Server) as url2,rec.YJ_Name,rec.YJ_No,rec.Rec_Rating_State,rec.Rec_Assessment_State,dbo.get_Record_OverTime(rec.FR_No,rec.call_Time_End,rec.Rec_Assessment_State) as recordOverTime,rec.HJID from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,SYS_HJ_LINE line,sys_user_jq suj where rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and rec.jy=line.jy and rec.line_no=line.line_no and jl.jq_No=suj.jq_No");
					str2.append(" and suj.group_No='" + user.getGroupNo() + "'");
					str3.append(" select bb.WebID,bb.Call_Time_Start,bb.Call_Time_End,bb.HJ_Type,bb.Call_Time_Len,bb.JY,bb.JQ_No,bb.FR_No,bb.FR_Name,bb.QS_Info1,bb.QS_Info2,bb.QS_Info3,bb.QS_Info4,bb.QS_Info5,bb.QS_Info6,bb.QS_Info7,bb.QS_Info8,bb.QS_Info9,bb.Call_RecFile,bb.JQ_Name,bb.IP,bb.RecUrl,cast(bb.callID AS varchar(26)) AS callID,bb.zw,bb.Call_VideoFile1,bb.Call_VideoFile2,bb.url1,bb.url2,bb.YJ_Name,bb.YJ_No,bb.Rec_Rating_State,bb.Rec_Assessment_State,bb.recordOverTime,bb.HJID from (").append(str2.toString()).append(") as bb");
					if(user.getIsBj()==1){
						str3.append(" where 1=1" + str1.toString());
					}else{
						str3.append(" where 1=1" + str1.toString() + " and bb.HJID not in (select HJID from JL_HJ_SP sp where sp.HJID=bb.HJID)");
					}
				}
				str3.append(" order by bb.Call_Time_Start desc");
				Object[] obj = {};
				Map map = hrs.searchToPageBySql(str3.toString(), page.getPageNo(),20, obj);
				page.setRecordCount((Integer) map.get(Constant.ALLFILEDCOUNT));
				List list2 = (List) map.get(Constant.RESULTLIST);
				Iterator it = list2.iterator();
				while (it.hasNext()) {
					Object[] obj1 = (Object[]) it.next();
					RecordSearch recordSearch = new RecordSearch();
					recordSearch.setWebId(Long.parseLong(obj1[0].toString()));
					if (obj1[1] != null && !obj1[1].toString().trim().equals("")) {
						recordSearch.setCallTimeStart(obj1[1].toString());
					}
					if (obj1[2] != null && !obj1[2].toString().trim().equals("")) {
						recordSearch.setCallTimeEnd(obj1[2].toString());
					}	
					if (obj1[3] != null && !obj1[3].toString().trim().equals("")) {
						recordSearch.setType(obj1[3].toString());
					}
					if (obj1[4] != null && !obj1[4].toString().trim().equals("")) {
						recordSearch.setCallTimeLen(obj1[4].toString());
					}
					if (obj1[5] != null && !obj1[5].toString().trim().equals("")) {
						recordSearch.setJy(obj1[5].toString());
					}
					if (obj1[6] != null && !obj1[6].toString().trim().equals("")) {
						recordSearch.setJqNo(obj1[6].toString());
					}
					if (obj1[7] != null && !obj1[7].toString().trim().equals("")) {
						recordSearch.setFrNo(obj1[7].toString());
					}
					if (obj1[8] != null && !obj1[8].toString().trim().equals("")) {
						recordSearch.setFrName(obj1[8].toString());
					}
					String info="";
					int index=0;
					if (obj1[9] != null && !obj1[9].toString().trim().equals("")) {
						info+=obj1[9].toString();
						index++;
					}
					if (obj1[10] != null &&  !obj1[10].toString().trim().equals("")) {
						info+=obj1[10].toString();
						index++;
					}
					if (obj1[11] != null &&  !obj1[11].toString().trim().equals("")) {
						info+=obj1[11].toString();
						index++;
					}
					if (obj1[12] != null &&  !obj1[12].toString().trim().equals("")) {
						info+=obj1[12].toString();
						index++;
					}
					if (obj1[13] != null &&  !obj1[13].toString().trim().equals("")) {
						info+=obj1[13].toString();
						index++;
					}
					if (obj1[14] != null &&  !obj1[14].toString().trim().equals("")) {
						info+=obj1[14].toString();
						index++;
					}
					if (obj1[15] != null &&  !obj1[15].toString().trim().equals("")) {
						info+=obj1[15].toString();
						index++;
					}
					if (obj1[16] != null &&  !obj1[16].toString().trim().equals("")) {
						info+=obj1[16].toString();
						index++;
					}
					if (obj1[17] != null &&  !obj1[17].toString().trim().equals("")) {
						info+=obj1[17].toString();
						index++;
					}
					recordSearch.setQsInfo(info);
					recordSearch.setQsIndex(""+index);
					if (obj1[18] != null && !obj1[18].toString().trim().equals("")) {
						recordSearch.setDownCallRecFile(obj1[18].toString());
						obj1[18] = obj1[18].toString().replace(":", "");
						obj1[18] = obj1[18].toString().replace("\\", "/");
						recordSearch.setCallRecFile(obj1[21].toString() + "/"+ obj1[18].toString());
					}
					if (obj1[19] != null && !obj1[19].toString().trim().equals("")) {
						recordSearch.setJqName(obj1[19].toString());
					}
					if (obj1[20] != null && !obj1[20].toString().trim().equals("")) {
						recordSearch.setIp(obj1[20].toString());
					}
					if (obj1[21] != null && !obj1[21].toString().trim().equals("")) {
						recordSearch.setRecUrl(obj1[21].toString());
					}
					if (obj1[22] != null && !obj1[22].toString().trim().equals("")) {
						recordSearch.setCall_ID(obj1[22].toString());
					}
//					if (obj1[23] != null && !obj1[23].toString().trim().equals("")) {
//						recordSearch.setUserNo(obj1[23].toString());
//					}
//					if (obj1[24] != null && !obj1[24].toString().trim().equals("")) {
//						recordSearch.setWeb_Id(obj1[24].toString());
//					}
					if (obj1[23] != null && !obj1[23].toString().trim().equals("")) {
						recordSearch.setZw(obj1[23].toString());
					}
					if (obj1[24] != null && !obj1[24].toString().equals("")) {
						if(obj1[26] != null && obj1[26].toString() != ""){
							recordSearch.setDownCallVideoFile1(obj1[26].toString());
							obj1[24] = obj1[24].toString().replace(":", "");
							obj1[24] = obj1[24].toString().replace("\\", "/");
							recordSearch.setCallVideoFile1(obj1[26].toString() + "/"+obj1[24].toString());
//							System.out.println(recordSearch.getDownCallVideoFile1().toString());
							
//							if(new File(obj1[26].toString().replace("\\", "/")).exists()){
//								if(new File(obj1[26].toString().replace("\\", "/")).length()<1048576){
//									recordSearch.setCallVideoFile1("");
//								}else{
//									obj1[26] = obj1[26].toString().replace(":", "");
//									obj1[26] = obj1[26].toString().replace("\\", "/");
//									recordSearch.setCallVideoFile1(obj1[28].toString() + "/"+obj1[26].toString());
//								}
//							}							
						}else{
							recordSearch.setDownCallVideoFile1("");
							recordSearch.setCallVideoFile1("");
						}
					}
					if (obj1[25] != null && !obj1[25].toString().equals("")) {
						if(obj1[26] != null && obj1[26].toString() != ""){
							recordSearch.setDownCallVideoFile2(obj1[27].toString());
							obj1[25] = obj1[25].toString().replace(":", "");
							obj1[25] = obj1[25].toString().replace("\\", "/");
							recordSearch.setCallVideoFile2(obj1[27].toString() + "/"+obj1[25].toString());
//							if(new File(obj1[27].toString().replace("\\", "/")).exists()){
//								if(new File(obj1[27].toString().replace("\\", "/")).length()<1048576){
//									recordSearch.setCallVideoFile2("");
//								}else{
//									obj1[27] = obj1[27].toString().replace(":", "");
//									obj1[27] = obj1[27].toString().replace("\\", "/");
//									recordSearch.setCallVideoFile2(obj1[29].toString() + "/"+obj1[27].toString());
//								}
//							}
						}else{
							recordSearch.setDownCallVideoFile2("");
							recordSearch.setCallVideoFile2("");
						}
					}
					if (obj1[28] != null && !obj1[28].toString().equals("")) {
						recordSearch.setYjName(obj1[28].toString());
					}
					if (obj1[29] != null && !obj1[29].toString().equals("")) {
						recordSearch.setYjNo(obj1[29].toString());
					}
					recordSearch.setRecRatingState(obj1[30].toString());
					recordSearch.setRecAssessmentState(obj1[31].toString());
					recordSearch.setRecordOverTime(obj1[32].toString());

					page.getList().add(recordSearch);
				}
			} else {
				StringBuffer str = new StringBuffer(now.substring(0, 10));
				str.append(" 00:00:00");
				todayBegin = str.toString();
				StringBuffer str1 = new StringBuffer(now.substring(0, 10));
				str1.append(" 23:59:59");
				todayEnd = str1.toString();
			}
			if (user.getIsSuper() == 1) {
				String hql = "from SysHjServer";
				List<SysHjServer> list = hrs.searchAll(hql);
				request.setAttribute("sysQqServerList", list);
			} else if (user.getGroupNo().trim().equals("Admin")) {
				String hql = "from SysHjServer";
				List<SysHjServer> list = hrs.searchAll(hql);
				request.setAttribute("sysQqServerList", list);
			} else {
				String sql6 = "select distinct JY from SYS_USER_JQ where Group_No='"+ user.getGroupNo() + "'";
				List list6 = hrs.searchAllBySql(sql6);
				if (list6.size() > 0) {
					StringBuffer hql = new StringBuffer("from SysHjServer ");
					Iterator it = list6.iterator();
					int i = 0;
					while (it.hasNext()) {
						if (i == 0) {
							hql.append(" where serverName='" + it.next() + "' ");
							i++;
						} else {
							hql.append("or serverName='" + it.next() + "' ");
							i++;
						}
					}
					List<SysHjServer> list = hrs.searchAll(hql.toString());
					request.setAttribute("sysQqServerList", list);
				}
			}
			page.setPageSize(20);
	//		if(Constant.fileExist()){
	//			request.setAttribute("BatchDownLoadSwitch", Constant.BatchDownLoadSwitch);
	//		}
			request.setAttribute("todayBegin", todayBegin);
			request.setAttribute("todayEnd", todayEnd);
			request.setAttribute("page", page);
			request.setAttribute("jqIsNodisable", jqIsNodisable);
			return mapping.findForward("hjReocrdMain");
	}
	public ActionForward search1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			HjRecordForm rscf = (HjRecordForm) form;
			Page page = new Page();
			page.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = df1.format(date);
			String todayBegin = "";
			String todayEnd = "";
			int jqIsNodisable = 0;
			if ((rscf.getRecAssessmentState() != null && !rscf.getRecAssessmentState().equals("null")) || (rscf.getRecRatingState() != null && !rscf.getRecRatingState().equals("null")) || rscf.getCallTimeEnd() != null || rscf.getCallTimeStart() != null  || (rscf.getType() != null && !rscf.getType().equals("null")) || (rscf.getJqNo() != null && !rscf.getJqNo().equals("null")) || rscf.getQsName() != null || (rscf.getJy() != null && !rscf.getJy().equals("null")) || rscf.getFrNo() != null || rscf.getFrName() != null) {
				StringBuffer str1 = new StringBuffer();
				if (rscf.getCallTimeStart() != null && !rscf.getCallTimeStart().trim().equals("")) {
					str1.append(" and bb.Call_Time_Start>='"+ rscf.getCallTimeStart() + "'");
					todayBegin = rscf.getCallTimeStart();
				}
				if (rscf.getCallTimeEnd() != null && !rscf.getCallTimeEnd().trim().equals("")) {
					str1.append(" and bb.Call_Time_Start<='"+ rscf.getCallTimeEnd() + "'");
					todayEnd = rscf.getCallTimeEnd();
				}
				if (rscf.getFrNo() != null && !rscf.getFrNo().equals("")) {
					str1.append(" and bb.FR_No='" + rscf.getFrNo() + "'");
				}
				if (rscf.getFrName() != null && !rscf.getFrName().trim().equals("")) {
					str1.append(" and bb.FR_Name like '%" + rscf.getFrName() + "%'");
				}
				if (rscf.getType() != null && !rscf.getType().equals("null")) {
					str1.append(" and bb.HJ_Type=" + Integer.parseInt(rscf.getType()));
				}
				if (rscf.getRecRatingState() != null && !rscf.getRecRatingState().equals("null")) {
					str1.append(" and bb.Rec_Rating_State=" + Integer.parseInt(rscf.getRecRatingState()));
				}
				if (rscf.getRecAssessmentState() != null && !rscf.getRecAssessmentState().equals("null")) {
					str1.append(" and bb.Rec_Assessment_State=" + Integer.parseInt(rscf.getRecAssessmentState()));
				}
				if (rscf.getRecordOverTime() != null && !rscf.getRecordOverTime().equals("null")) {
					str1.append(" and bb.recordOverTime=" + Integer.parseInt(rscf.getRecordOverTime()));
				}
				if (rscf.getJqNo() != null && !rscf.getJqNo().equals("null")) {
					str1.append(" and bb.JQ_No='" + rscf.getJqNo() + "'");
				}
				if (rscf.getJy() != null && !rscf.getJy().equals("null")) {
					str1.append(" and bb.JY='" + rscf.getJy() + "'");
					jqIsNodisable = 1;
					if (user.getIsSuper() == 1) {
						String hql1 = "from JlJq where jy='" + rscf.getJy() + "'";
						List<JlJq> list1 = hrs.searchAll(hql1);
						request.setAttribute("jlJqList", list1);
					}else if(user.getGroupNo().trim().equals("Admin")) {
						String hql1 = "from JlJq where jy='" + rscf.getJy() + "' and isTs!=1";
						List<JlJq> list1 = hrs.searchAll(hql1);
						request.setAttribute("jlJqList", list1);
					}else {
						String hql1 = "select jq from JlJq jq,SysUserJq sys where jq.jy='"+ rscf.getJy()+ "' and jq.jqNo=sys.jqNo and sys.groupNo='"+ user.getGroupNo() + "'";
						List<JlJq> list1 = hrs.searchAll(hql1);
						request.setAttribute("jlJqList", list1);
					}
				}
				if(rscf.getZw()!=null && !rscf.getZw().equals("")){
					str1.append(" and bb.zw='"+rscf.getZw()+"'");
				}
				if (rscf.getQsName() != null && !rscf.getQsName().equals("")) {
					str1.append(" and (bb.QS_Info1 like '%" + rscf.getQsName() + "%' or bb.QS_Info2 like '%"+ rscf.getQsName() + "%' or bb.QS_Info3 like '%"+ rscf.getQsName() + "%' or bb.QS_Info4 like '%"+ rscf.getQsName() + "%' or bb.QS_Info5 like '%"+ rscf.getQsName() + "%' or bb.QS_Info6 like '%"+ rscf.getQsName() + "%' or bb.QS_Info7 like '%"+ rscf.getQsName() + "%' or bb.QS_Info8 like '%"+ rscf.getQsName() + "%' or bb.QS_Info9 like '%"+ rscf.getQsName() + "%')");
				}
				StringBuffer str2 = new StringBuffer();
				StringBuffer str3 = new StringBuffer();
				if (user.getIsSuper() == 1) {
					str2.append("select rec.WebID,cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.JY,rec.JQ_No,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,rec.Call_RecFile,jl.JQ_Name,jlserver.IP,jlserver.RecUrl,rec.Call_ID as callID,rec.zw,rec.Call_VideoFile1,rec.Call_VideoFile2,dbo.get_VideoURL(line.VideoChan1_Server) as url1,dbo.get_VideoURL(line.VideoChan2_Server) as url2,rec.YJ_Name,rec.YJ_No,rec.Rec_Rating_State,rec.Rec_Assessment_State,dbo.get_Record_OverTime(rec.FR_No,rec.call_Time_End,rec.Rec_Assessment_State) as recordOverTime from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,SYS_HJ_LINE line where rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and rec.jy=line.jy and rec.line_no=line.line_no");
					str3.append(" select bb.WebID,bb.Call_Time_Start,bb.Call_Time_End,bb.HJ_Type,bb.Call_Time_Len,bb.JY,bb.JQ_No,bb.FR_No,bb.FR_Name,bb.QS_Info1,bb.QS_Info2,bb.QS_Info3,bb.QS_Info4,bb.QS_Info5,bb.QS_Info6,bb.QS_Info7,bb.QS_Info8,bb.QS_Info9,bb.Call_RecFile,bb.JQ_Name,bb.IP,bb.RecUrl,cast(bb.callID AS varchar(26)) AS callID,bb.zw,bb.Call_VideoFile1,bb.Call_VideoFile2,bb.url1,bb.url2,bb.YJ_Name,bb.YJ_No,bb.Rec_Rating_State,bb.Rec_Assessment_State,bb.recordOverTime from (").append(str2.toString()).append(") as bb");
					str3.append(" where 1=1" + str1.toString());
				} else if (user.getGroupNo().trim().equals("Admin")) {
					str2.append("select rec.WebID,cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.JY,rec.JQ_No,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,rec.Call_RecFile,jl.JQ_Name,jlserver.IP,jlserver.RecUrl,rec.Call_ID as callID,rec.zw,rec.Call_VideoFile1,rec.Call_VideoFile2,dbo.get_VideoURL(line.VideoChan1_Server) as url1,dbo.get_VideoURL(line.VideoChan2_Server) as url2,rec.YJ_Name,rec.YJ_No,rec.Rec_Rating_State,rec.Rec_Assessment_State,dbo.get_Record_OverTime(rec.FR_No,rec.call_Time_End,rec.Rec_Assessment_State) as recordOverTime,rec.HJID from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,SYS_HJ_LINE line where rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and rec.jy=line.jy and rec.line_no=line.line_no and jl.Is_Ts!=1");
					str3.append(" select bb.WebID,bb.Call_Time_Start,bb.Call_Time_End,bb.HJ_Type,bb.Call_Time_Len,bb.JY,bb.JQ_No,bb.FR_No,bb.FR_Name,bb.QS_Info1,bb.QS_Info2,bb.QS_Info3,bb.QS_Info4,bb.QS_Info5,bb.QS_Info6,bb.QS_Info7,bb.QS_Info8,bb.QS_Info9,bb.Call_RecFile,bb.JQ_Name,bb.IP,bb.RecUrl,cast(bb.callID AS varchar(26)) AS callID,bb.zw,bb.Call_VideoFile1,bb.Call_VideoFile2,bb.url1,bb.url2,bb.YJ_Name,bb.YJ_No,bb.Rec_Rating_State,bb.Rec_Assessment_State,bb.recordOverTime,bb.HJID from (").append(str2.toString()).append(") as bb");
					if(user.getIsBj()==1){
						str3.append(" where 1=1" + str1.toString());
					}else{
						str3.append(" where 1=1" + str1.toString() + " and bb.HJID not in (select HJID from JL_HJ_SP sp where sp.HJID=bb.HJID)");
					}
				} else {
					str2.append("select rec.WebID,cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.JY,rec.JQ_No,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,rec.Call_RecFile,jl.JQ_Name,jlserver.IP,jlserver.RecUrl,rec.Call_ID as callID,rec.zw,rec.Call_VideoFile1,rec.Call_VideoFile2,dbo.get_VideoURL(line.VideoChan1_Server) as url1,dbo.get_VideoURL(line.VideoChan2_Server) as url2,rec.YJ_Name,rec.YJ_No,rec.Rec_Rating_State,rec.Rec_Assessment_State,dbo.get_Record_OverTime(rec.FR_No,rec.call_Time_End,rec.Rec_Assessment_State) as recordOverTime,rec.HJID from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,SYS_HJ_LINE line,sys_user_jq suj where rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and rec.jy=line.jy and rec.line_no=line.line_no and jl.jq_No=suj.jq_No");
					str2.append(" and suj.group_No='" + user.getGroupNo() + "'");
					str3.append(" select bb.WebID,bb.Call_Time_Start,bb.Call_Time_End,bb.HJ_Type,bb.Call_Time_Len,bb.JY,bb.JQ_No,bb.FR_No,bb.FR_Name,bb.QS_Info1,bb.QS_Info2,bb.QS_Info3,bb.QS_Info4,bb.QS_Info5,bb.QS_Info6,bb.QS_Info7,bb.QS_Info8,bb.QS_Info9,bb.Call_RecFile,bb.JQ_Name,bb.IP,bb.RecUrl,cast(bb.callID AS varchar(26)) AS callID,bb.zw,bb.Call_VideoFile1,bb.Call_VideoFile2,bb.url1,bb.url2,bb.YJ_Name,bb.YJ_No,bb.Rec_Rating_State,bb.Rec_Assessment_State,bb.recordOverTime,bb.HJID from (").append(str2.toString()).append(") as bb");
					if(user.getIsBj()==1){
						str3.append(" where 1=1" + str1.toString());
					}else{
						str3.append(" where 1=1" + str1.toString() + " and bb.HJID not in (select HJID from JL_HJ_SP sp where sp.HJID=bb.HJID)");
					}
				}
				str3.append(" order by bb.Call_Time_Start desc");
				Object[] obj = {};
				Map map = hrs.searchToPageBySql(str3.toString(), page.getPageNo(),20, obj);
				page.setRecordCount((Integer) map.get(Constant.ALLFILEDCOUNT));
				List list2 = (List) map.get(Constant.RESULTLIST);
				Iterator it = list2.iterator();
				while (it.hasNext()) {
					Object[] obj1 = (Object[]) it.next();
					RecordSearch recordSearch = new RecordSearch();
					recordSearch.setWebId(Long.parseLong(obj1[0].toString()));
					if (obj1[1] != null && !obj1[1].toString().trim().equals("")) {
						recordSearch.setCallTimeStart(obj1[1].toString());
					}
					if (obj1[2] != null && !obj1[2].toString().trim().equals("")) {
						recordSearch.setCallTimeEnd(obj1[2].toString());
					}	
					if (obj1[3] != null && !obj1[3].toString().trim().equals("")) {
						recordSearch.setType(obj1[3].toString());
					}
					if (obj1[4] != null && !obj1[4].toString().trim().equals("")) {
						recordSearch.setCallTimeLen(obj1[4].toString());
					}
					if (obj1[5] != null && !obj1[5].toString().trim().equals("")) {
						recordSearch.setJy(obj1[5].toString());
					}
					if (obj1[6] != null && !obj1[6].toString().trim().equals("")) {
						recordSearch.setJqNo(obj1[6].toString());
					}
					if (obj1[7] != null && !obj1[7].toString().trim().equals("")) {
						recordSearch.setFrNo(obj1[7].toString());
					}
					if (obj1[8] != null && !obj1[8].toString().trim().equals("")) {
						recordSearch.setFrName(obj1[8].toString());
					}
					String info="";
					int index=0;
					if (obj1[9] != null && !obj1[9].toString().trim().equals("")) {
						info+=obj1[9].toString();
						index++;
					}
					if (obj1[10] != null &&  !obj1[10].toString().trim().equals("")) {
						info+=obj1[10].toString();
						index++;
					}
					if (obj1[11] != null &&  !obj1[11].toString().trim().equals("")) {
						info+=obj1[11].toString();
						index++;
					}
					if (obj1[12] != null &&  !obj1[12].toString().trim().equals("")) {
						info+=obj1[12].toString();
						index++;
					}
					if (obj1[13] != null &&  !obj1[13].toString().trim().equals("")) {
						info+=obj1[13].toString();
						index++;
					}
					if (obj1[14] != null &&  !obj1[14].toString().trim().equals("")) {
						info+=obj1[14].toString();
						index++;
					}
					if (obj1[15] != null &&  !obj1[15].toString().trim().equals("")) {
						info+=obj1[15].toString();
						index++;
					}
					if (obj1[16] != null &&  !obj1[16].toString().trim().equals("")) {
						info+=obj1[16].toString();
						index++;
					}
					if (obj1[17] != null &&  !obj1[17].toString().trim().equals("")) {
						info+=obj1[17].toString();
						index++;
					}
					recordSearch.setQsInfo(info);
					recordSearch.setQsIndex(""+index);
					if (obj1[18] != null && !obj1[18].toString().trim().equals("")) {
						recordSearch.setDownCallRecFile(obj1[18].toString());
						obj1[18] = obj1[18].toString().replace(":", "");
						obj1[18] = obj1[18].toString().replace("\\", "/");
						recordSearch.setCallRecFile(obj1[21].toString() + "/"+ obj1[18].toString());
					}
					if (obj1[19] != null && !obj1[19].toString().equals("")) {
						recordSearch.setJqName(obj1[19].toString());
					}
					if (obj1[20] != null && !obj1[20].toString().equals("")) {
						recordSearch.setIp(obj1[20].toString());
					}
					if (obj1[21] != null && !obj1[21].toString().equals("")) {
						recordSearch.setRecUrl(obj1[21].toString());
					}
					if (obj1[22] != null && !obj1[22].toString().equals("")) {
						recordSearch.setCall_ID(obj1[22].toString());
					}
//					if (obj1[23] != null && !obj1[23].toString().equals("")) {
//						recordSearch.setUserNo(obj1[23].toString());
//					}
//					if (obj1[24] != null && !obj1[24].toString().equals("")) {
//						recordSearch.setWeb_Id(obj1[24].toString());
//					}
					if (obj1[23] != null && !obj1[23].toString().equals("")) {
						recordSearch.setZw(obj1[23].toString());
					}

					if (obj1[24] != null && !obj1[24].toString().equals("")) {
						if(obj1[26] != null && obj1[26].toString() != ""){
							recordSearch.setDownCallVideoFile1(obj1[24].toString());
							obj1[24] = obj1[24].toString().replace(":", "");
							obj1[24] = obj1[24].toString().replace("\\", "/");
							recordSearch.setCallVideoFile1(obj1[26].toString() + "/"+obj1[24].toString());
//							System.out.println(recordSearch.getDownCallVideoFile1().toString());
							
//							if(new File(obj1[26].toString().replace("\\", "/")).exists()){
//								if(new File(obj1[26].toString().replace("\\", "/")).length()<1048576){
//									recordSearch.setCallVideoFile1("");
//								}else{
//									obj1[26] = obj1[26].toString().replace(":", "");
//									obj1[26] = obj1[26].toString().replace("\\", "/");
//									recordSearch.setCallVideoFile1(obj1[28].toString() + "/"+obj1[26].toString());
//								}
//							}							
						}else{
							recordSearch.setDownCallVideoFile1("");
							recordSearch.setCallVideoFile1("");
						}
					}
					if (obj1[25] != null && !obj1[25].toString().equals("")) {
						if(obj1[26] != null && obj1[26].toString() != ""){
							recordSearch.setDownCallVideoFile2(obj1[25].toString());
							obj1[25] = obj1[25].toString().replace(":", "");
							obj1[25] = obj1[25].toString().replace("\\", "/");
							recordSearch.setCallVideoFile2(obj1[27].toString() + "/"+obj1[25].toString());
//							if(new File(obj1[27].toString().replace("\\", "/")).exists()){
//								if(new File(obj1[27].toString().replace("\\", "/")).length()<1048576){
//									recordSearch.setCallVideoFile2("");
//								}else{
//									obj1[27] = obj1[27].toString().replace(":", "");
//									obj1[27] = obj1[27].toString().replace("\\", "/");
//									recordSearch.setCallVideoFile2(obj1[29].toString() + "/"+obj1[27].toString());
//								}
//							}
						}else{
							recordSearch.setDownCallVideoFile2("");
							recordSearch.setCallVideoFile2("");
						}
					}
					if (obj1[28] != null && !obj1[28].toString().equals("")) {
						recordSearch.setYjName(obj1[28].toString());
					}
					if (obj1[29] != null && !obj1[29].toString().equals("")) {
						recordSearch.setYjNo(obj1[29].toString());
					}
					recordSearch.setRecRatingState(obj1[30].toString());
					recordSearch.setRecAssessmentState(obj1[31].toString());
					recordSearch.setRecordOverTime(obj1[32].toString());
					page.getList().add(recordSearch);
				}
			} else {
				StringBuffer str = new StringBuffer(now.substring(0, 10));
				str.append(" 00:00:00");
				todayBegin = str.toString();
				StringBuffer str1 = new StringBuffer(now.substring(0, 10));
				str1.append(" 23:59:59");
				todayEnd = str1.toString();
			}
			if (user.getIsSuper() == 1) {
				String hql = "from SysHjServer";
				List<SysHjServer> list = hrs.searchAll(hql);
				request.setAttribute("sysQqServerList", list);
			} else if (user.getGroupNo().trim().equals("Admin")) {
				String hql = "from SysHjServer";
				List<SysHjServer> list = hrs.searchAll(hql);
				request.setAttribute("sysQqServerList", list);
			} else {
				String sql6 = "select distinct JY from SYS_USER_JQ where Group_No='"+ user.getGroupNo() + "'";
				List list6 = hrs.searchAllBySql(sql6);
				if (list6.size() > 0) {
					StringBuffer hql = new StringBuffer("from SysHjServer ");
					Iterator it = list6.iterator();
					int i = 0;
					while (it.hasNext()) {
						if (i == 0) {
							hql.append(" where serverName='" + it.next() + "' ");
							i++;
						} else {
							hql.append("or serverName='" + it.next() + "' ");
							i++;
						}
					}
					List<SysHjServer> list = hrs.searchAll(hql.toString());
					request.setAttribute("sysQqServerList", list);
				}
			}
			page.setPageSize(20);
	//		if(Constant.fileExist()){
	//			request.setAttribute("BatchDownLoadSwitch", Constant.BatchDownLoadSwitch);
	//		}
			request.setAttribute("todayBegin", todayBegin);
			request.setAttribute("todayEnd", todayEnd);
			request.setAttribute("page", page);
			request.setAttribute("jqIsNodisable", jqIsNodisable);
			return mapping.findForward("hjReocrdMain");
	}
	// 通过服务器名称查询监区
	public ActionForward checkJq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String jy1 = java.net.URLDecoder.decode((String) request.getParameter("jy1"), "UTF-8");
			if (user.getIsSuper() == 1) {
				String hql = "from JlJq where jy='" + jy1 + "'";
				List<JlJq> list = hrs.searchAll(hql);
				JSONArray jsonArray = JSONArray.fromObject(list);
				response.setContentType("text/json; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(jsonArray.toString());
			} else if (user.getGroupNo().equals("Admin")) {
				String hql = "from JlJq where jy='" + jy1 + "' and isTs!=1";
				List<JlJq> list = hrs.searchAll(hql);
				JSONArray jsonArray = JSONArray.fromObject(list);
				response.setContentType("text/json; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(jsonArray.toString());
			} else {
				String sql = "select jl.JQ_No,jl.JQ_Name from JL_JQ jl,SYS_USER_JQ sys where jl.JQ_No=sys.JQ_No and sys.jy='"+ jy1 + "' and sys.Group_No='" + user.getGroupNo() + "'";
				List list = hrs.searchAllBySql(sql);
				List<JlJq> list1 = new ArrayList();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Object[] objects = (Object[]) it.next();
					JlJq jlJq = new JlJq();
					jlJq.setJqNo(objects[0].toString());
					jlJq.setJqName(objects[1].toString());
					list1.add(jlJq);
				}
				JSONArray jsonArray = JSONArray.fromObject(list1);
				response.setContentType("text/json; charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(jsonArray.toString());
			}
			return null;
	}
	public ActionForward downFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String path = java.net.URLDecoder.decode((String) request.getParameter("path"), "UTF-8");
			path = path.replaceAll(" ", "%20");
			Calendar c = Calendar.getInstance();
			SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			c = Calendar.getInstance(Locale.CHINESE);
			String loginTime = simpleDateTimeFormat.format(c.getTime());
			SysLog sl = new SysLog();
			sl.setType("严重");
			sl.setLogTime(loginTime);
			sl.setUserName(user.getUserName());
			sl.setUserNo(user.getUserNo());
			sl.setUserIp(request.getRemoteAddr());
			sl.setModel("通话录音");
			sl.setInfo("下载地址为" + path);
			sl.setOp("下载通话录音");
			hrs.save(sl);
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
			// 文件本地服务器上
			// String filePath = request.getRealPath("/")+path;//服务器下载绝对路径
			// String filePath =path;
			// File uploadFile = new File(filePath);
			// fis = new FileInputStream(uploadFile);
			// 文件别的服务器上
			// URL url=new
			// URL("http","192.168.1.121",9009,"/E/aa.mp3");//这个可以http://
			// 192.168.1.121:9009/E/TeleRec/Server1/201012/2010-12-03
			// 18/20101203184819968_MN_00_8000_601.wav
			// URL url=new URL("http","192.168.1.121",9009,
			// "/E/TeleRec/Server1/201012/2010-12-02%2018/aa.mp3");//这个可以
			try {
				URL url = new URL(path);// 这个可以
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				if (connection.getResponseCode() == 200) {
					fis = connection.getInputStream();
					bis = new BufferedInputStream(fis);
					fos = response.getOutputStream();
					bos = new BufferedOutputStream(fos);
					response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(path.substring(path.lastIndexOf("//")+1), "utf-8"));
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
						bos.write(buffer, 0, bytesRead);
					}
					bos.flush();
					fis.close();
					bis.close();
					fos.close();
					bos.close();
				} else {
					return mapping.findForward("fileIsNoExist");
				}
			} catch (Exception e) {
				return mapping.findForward("fileIsNoExist");
			}
			
			// String filePath =path;// 本地
			// File uploadFile = new File(filePath);
			// fis = new FileInputStream(uploadFile);
			// bis = new BufferedInputStream(fis);
			// fos = response.getOutputStream();
			// bos = new BufferedOutputStream(fos);
			// response.setHeader("Content-Disposition",
			// "attachment;filename=" +
			// URLEncoder.encode(path, "utf-8"));
			// int bytesRead = 0;
			// byte[] buffer = new byte[8192];
			// while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
			// bos.write(buffer, 0, bytesRead);
			// }
			// bos.flush();
			// fis.close();
			// bis.close();
			// fos.close();
			// bos.close();
			return null;
	}
	// 播放通话录音
	public ActionForward bofangId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String bofangId = java.net.URLDecoder.decode((String) request.getParameter("id"), "UTF-8");
			String bofangId1 = java.net.URLDecoder.decode((String) request.getParameter("id1"), "UTF-8");
			String bofangId2 = java.net.URLDecoder.decode((String) request.getParameter("id2"), "UTF-8");
			String webId=request.getParameter("webId");
			JlHjRec jlHjRec=(JlHjRec)hrs.findById(JlHjRec.class, Long.parseLong(webId));
			// String jy=request.getParameter("jy");
			// String hql="from SysQqServer where serverName='"+jy+"'";
			// List<SysQqServer> list=rs.searchAll(hql);
			// if(list.size()>0){
			// SysQqServer sysQqServer=(SysQqServer)list.get(0);
			// bofangId=sysQqServer.getRecUrl()+"/"+bofangId;
			// }
			String returnName="playRecord";
			if(bofangId2!="" && !bofangId2.equals("/") && bofangId1!="" && bofangId!=""){
				String localAdd=bofangId1.substring(bofangId1.lastIndexOf("/")+1,bofangId1.length());
				bofangId1="C:\\tempdown\\"+localAdd;
				String localAdd1=bofangId2.substring(bofangId2.lastIndexOf("/")+1,bofangId2.length());
				bofangId2="C:\\tempdown\\"+localAdd1;
				String localAdd2=bofangId.substring(bofangId.lastIndexOf("/")+1,bofangId.length());
				bofangId="C:\\tempdown\\"+localAdd2;
				returnName="playRecord2";
			}else if(bofangId1!="" && bofangId!=""){
				
				String localAdd=bofangId1.substring(bofangId1.lastIndexOf("/")+1,bofangId1.length());
				bofangId1="C:\\tempdown\\"+localAdd;
				String localAdd2=bofangId.substring(bofangId.lastIndexOf("/")+1,bofangId.length());
				bofangId="C:\\tempdown\\"+localAdd2;
				returnName="playRecord1";
			}else {
				returnName="playRecord";
			}
			request.setAttribute("bofangId", bofangId);
			request.setAttribute("bofangId1", bofangId1);
			request.setAttribute("bofangId2", bofangId2);
			request.setAttribute("webId", webId);
			request.setAttribute("jlHjRec", jlHjRec);
			return mapping.findForward(returnName);
	}
	// 添加通话录音注释
	public ActionForward addRecordFlag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			request.setAttribute("callId", callId);
			request.setAttribute("userNo", user.getUserNo());
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);
			return mapping.findForward("addRecordFlag");
	}

	// 添加保存通话录音注释
	public ActionForward addSaveRecordFlag(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) 
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			String userNo = java.net.URLDecoder.decode((String) request.getParameter("userNo"), "UTF-8");
			String writeTxt = java.net.URLDecoder.decode((String) request.getParameter("writeTxt"), "UTF-8");
			JlHjInfo jlQqInfo = new JlHjInfo();
			jlQqInfo.setCallId(callId);
			jlQqInfo.setUserNo(userNo);
			jlQqInfo.setUserName(user.getUserName());
			jlQqInfo.setWriteTxt(writeTxt);
			hrs.save(jlQqInfo);
			JSONArray json = JSONArray.fromObject(null);
			response.getWriter().println(json.toString());
			return null;
	}
	//修改录音评级
	public ActionForward updateRatingState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			String webId = request.getParameter("webId");
			JlHjRec jlHjRec=(JlHjRec)hrs.findById(JlHjRec.class, Long.parseLong(webId));
			request.setAttribute("callId", callId);
			request.setAttribute("userNo", user.getUserNo());
			request.setAttribute("userName", user.getUserName());
			
			
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);			
		    request.setAttribute("jlHjRec", jlHjRec);
		    
			return mapping.findForward("updateRatingState");
	}

	// 添加保存评级信息
	public ActionForward updateSaveRatingState(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) 
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			String webId = request.getParameter("webId");
			String state = request.getParameter("state");

			String userNo = java.net.URLDecoder.decode((String) request.getParameter("userNo"), "UTF-8");
			String userName = java.net.URLDecoder.decode((String) request.getParameter("userName"), "UTF-8");
			String writeTxt = java.net.URLDecoder.decode((String) request.getParameter("writeTxt"), "UTF-8");

			JlHjRecRatingInfo jlHjRecRatingInfo = new JlHjRecRatingInfo();
			jlHjRecRatingInfo.setCallId(callId);
			jlHjRecRatingInfo.setUserNo(userNo);
			jlHjRecRatingInfo.setUserName(userName);
			jlHjRecRatingInfo.setWriteTxt(writeTxt);
			Timestamp now1=new Timestamp(System.currentTimeMillis());
			jlHjRecRatingInfo.setCreateTime(now1);
			hrs.save(jlHjRecRatingInfo);
			StringBuffer str=new StringBuffer("update JlHjRec sql set ");
			
			str.append("sql.recRatingState="+Integer.parseInt(state)+"");
			
			str.append(" where sql.webId=?");
			Object[] obj={Long.parseLong(webId)};
			hrs.updates(str.toString(), obj);
			JSONArray json = JSONArray.fromObject(null);
			response.getWriter().println(json.toString());
			return null;
	}
	// 查询所有评级信息
	public ActionForward checkAllRatingInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			List<JlHjRecRatingInfo> jlHjRecRatingInfoList=new ArrayList();
			StringBuffer str=new StringBuffer("select jl.webId,jl.callId,jl.userNo,jl.userName,jl.writeTxt,jl.createTime from JlHjRec jlHjRec,JlHjRecRatingInfo jl where jlHjRec.callId=jl.callId and jlHjRec.callId='");
			str.append(callId+"'");
			List<JlHjRecRatingInfo> list=hrs.searchAll(str.toString());
			Iterator it=list.iterator();
			while(it.hasNext()){
				Object[] obj=(Object[])it.next();
				JlHjRecRatingInfo jlHjRecRatingInfo=new JlHjRecRatingInfo();
				jlHjRecRatingInfo.setWebId(Long.parseLong(obj[0].toString()));
				jlHjRecRatingInfo.setCallId(obj[1].toString());
				jlHjRecRatingInfo.setUserNo(obj[2].toString());
				jlHjRecRatingInfo.setUserName(obj[3].toString());
				if(obj[4]!=null && obj[4].toString().trim()!=""){
					jlHjRecRatingInfo.setWriteTxt(obj[4].toString());
				}
				if(obj[5]!=null && obj[5].toString().trim()!=""){
					jlHjRecRatingInfo.setCreateTime(Timestamp.valueOf(obj[5].toString()));
				}
				
				jlHjRecRatingInfoList.add(jlHjRecRatingInfo);
			}
			request.setAttribute("jlHjRecRatingInfoList",jlHjRecRatingInfoList);
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);
			return mapping.findForward("allRatingInfo");
	}
	// 查询所有复听信息
	public ActionForward checkAllAssessmentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			List<JlHjRecAssessmentInfo> jlHjRecAssessmentInfoList=new ArrayList();
			StringBuffer str=new StringBuffer("select jl.webId,jl.callId,jl.userNo,jl.userName,jl.createTime from JlHjRec jlHjRec,JlHjRecAssessmentInfo jl where jlHjRec.callId=jl.callId and jlHjRec.callId='");
			str.append(callId+"'");
			List<JlHjRecAssessmentInfo> list=hrs.searchAll(str.toString());
			Iterator it=list.iterator();
			while(it.hasNext()){
				Object[] obj=(Object[])it.next();
				JlHjRecAssessmentInfo jlHjRecAssessmentInfo=new JlHjRecAssessmentInfo();
				jlHjRecAssessmentInfo.setWebId(Long.parseLong(obj[0].toString()));
				jlHjRecAssessmentInfo.setCallId(obj[1].toString());
				jlHjRecAssessmentInfo.setUserNo(obj[2].toString());
				jlHjRecAssessmentInfo.setUserName(obj[3].toString());

				if(obj[4]!=null && obj[4].toString().trim()!=""){
					jlHjRecAssessmentInfo.setCreateTime(Timestamp.valueOf(obj[4].toString()));
				}
				
				jlHjRecAssessmentInfoList.add(jlHjRecAssessmentInfo);
			}
			request.setAttribute("jlHjRecAssessmentInfoList",jlHjRecAssessmentInfoList);
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);
			return mapping.findForward("allAssessmentInfo");
	}
	// 查询通话记录其它信息
	public ActionForward checkRecordInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			String hql = "from JlHjRec where callId='" + callId + "'";
			List<JlHjRec> list1 = hrs.searchAll(hql);
			
			String hql1 = "from JlHjDjQs where hjid='" + list1.get(0).getHjid() + "'";
			List<JlHjDjQs> jlHjDjQslist = hrs.searchAll(hql1);
					
			List<RecordOtherInfoVO> recordOtherInfoVOList=new ArrayList();
			StringBuffer str=new StringBuffer("select jl.djUser,jl.djTime,jl.frInUser,jl.frInTime,jl.hjInfo from JlHjRec jlHjRec,JlHjDj jl where jlHjRec.hjid=jl.hjid and jlHjRec.hjid='");
			str.append(list1.get(0).getHjid()+"'");
			List<JlHjRecAssessmentInfo> list=hrs.searchAll(str.toString());
			Iterator it=list.iterator();
			while(it.hasNext()){
				Object[] obj=(Object[])it.next();
				RecordOtherInfoVO recordOtherInfo=new RecordOtherInfoVO();
//				recordOtherInfo.setWebId(Long.parseLong(obj[0].toString()));
				if(obj[0]!=null && obj[0].toString().trim()!=""){
					recordOtherInfo.setDjUser(obj[0].toString());
				}
				if(obj[1]!=null && obj[1].toString().trim()!=""){
					recordOtherInfo.setDjTime(obj[1].toString());
				}
				if(obj[2]!=null && obj[2].toString().trim()!=""){
					recordOtherInfo.setFrInUser(obj[2].toString());
				}
				if(obj[3]!=null && obj[3].toString().trim()!=""){
					recordOtherInfo.setFrInTime(obj[3].toString());
				}
				if(obj[4]!=null && obj[4].toString().trim()!=""){
					recordOtherInfo.setHjInfo(obj[4].toString());
				}

				recordOtherInfoVOList.add(recordOtherInfo);
			}
			request.setAttribute("recordOtherInfoVOList",recordOtherInfoVOList);
			request.setAttribute("jlHjDjQslist",jlHjDjQslist);
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);
			return mapping.findForward("recordOtherInfo");
	}
	// 修改通话录音注释
	public ActionForward updateRecordFlag(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String zhuShiWebID = request.getParameter("zhuShiWebID");
			String hql = "from JlHjInfo jl where jl.webId="+ Long.parseLong(zhuShiWebID);
			List<JlHjInfo> list = hrs.searchAll(hql);
			JlHjInfo jlQqInfo = (JlHjInfo) list.get(0);
			String hql1 = "from JlHjMon jl where jl.callId='"+ jlQqInfo.getCallId() + "' and jl.userNo='" + user.getUserNo()+ "'";
			List<JlHjMon> list1 = hrs.searchAll(hql1);
			String jtwriteTxt = "";
			if (list1.size() > 0) {
				JlHjMon jlHjMon = (JlHjMon) list1.get(0);
				jtwriteTxt = jlHjMon.getWriteTxt();
			}
			request.setAttribute("jlQqInfo", jlQqInfo);
			request.setAttribute("jtwriteTxt", jtwriteTxt);
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);
			return mapping.findForward("updateRecordFlag");
	}

	// 修改保存通话录音注释
	public ActionForward updateSaveRecordFlag(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String webId = request.getParameter("webId");
			String writeTxt = java.net.URLDecoder.decode((String) request.getParameter("writeTxt"), "UTF-8");
			JlHjInfo jlQqInfo = (JlHjInfo) hrs.findByIdLong(JlHjInfo.class, Long.parseLong(webId));
			StringBuffer str = new StringBuffer("update JlHjInfo jq set jq.writeTxt='");
			if (writeTxt != "") {
				str.append(writeTxt + "'");
			} else {
				str.append("'");
			}
			str.append(" where jq.webId=?");
			Object[] obj = { Long.parseLong(webId) };
			hrs.updates(str.toString(), obj);
			JSONArray json = JSONArray.fromObject(null);
			response.getWriter().println(json.toString());
			return null;
	}

	// 查询所有注释（包括监听通话录音注释）
	public ActionForward checkAllZhuShi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			List<JlHjInfoVO> jlHjInfoList=new ArrayList();
			String str=("select jl.webId,jl.call_Id,jl.user_No,jl.user_Name,jl.Write_Txt,jl.create_Time from Jl_Hj_Rec jlHjRec,Jl_Hj_Info jl where jlHjRec.call_Id=jl.call_Id and jlHjRec.call_Id='"+callId+"'");
			List<JlHjInfoVO> list=hrs.searchAllBySql(str.toString());
			Iterator it=list.iterator();
			while(it.hasNext()){
				Object[] obj=(Object[])it.next();
				JlHjInfoVO jlHjInfo=new JlHjInfoVO();
				jlHjInfo.setWebId(Long.parseLong(obj[0].toString()));
				jlHjInfo.setCallId(obj[1].toString());
				jlHjInfo.setUserNo(obj[2].toString());
				jlHjInfo.setUserName(obj[3].toString());
				jlHjInfo.setWriteTxt(obj[4].toString());
				
		        String tsStr = "";
		        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        tsStr = sdf.format(obj[5]);
		        
				jlHjInfo.setCreateTime(tsStr);
				
				jlHjInfoList.add(jlHjInfo);
			}
			request.setAttribute("jlHjInfoList",jlHjInfoList);
			
			List<JlHjMonVO> jlHjMonList=new ArrayList();
			String str1=("select jl.webId,jl.call_Id,jl.user_No,jl.user_Name,jl.Write_Txt,jl.Write_Txt_Lx,jl.create_Time from Jl_Hj_Rec jlHjRec,Jl_Hj_Mon jl where jlHjRec.call_Id=jl.call_Id and jlHjRec.call_Id='"+callId+"'");
			List<JlHjInfoVO> list1=hrs.searchAllBySql(str1.toString());
			Iterator it1=list1.iterator();
			while(it1.hasNext()){
				Object[] obj=(Object[])it1.next();
				JlHjMonVO jlHjMon=new JlHjMonVO();
				jlHjMon.setWebId(Long.parseLong(obj[0].toString()));
				jlHjMon.setCallId(obj[1].toString());
				jlHjMon.setUserNo(obj[2].toString());
				jlHjMon.setUserName(obj[3].toString());
				if(obj[4]!=null && !obj[4].toString().equals("")){
					jlHjMon.setWriteTxt(obj[4].toString());
				}
				
				jlHjMon.setWriteTxtLx(obj[5].toString());
				
		        String tsStr = "";
		        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		        tsStr = sdf.format(obj[6]);
		        
		        jlHjMon.setCreateTime(tsStr);
				
		        jlHjMonList.add(jlHjMon);
			}
			request.setAttribute("jlHjInfoList",jlHjInfoList);
			request.setAttribute("jlHjMonList",jlHjMonList);
			
			String frNo3 = java.net.URLDecoder.decode((String)request.getParameter("frNo3"),"UTF-8");
			String jq2 = java.net.URLDecoder.decode((String)request.getParameter("jq2"),"UTF-8");
			String jy2 = java.net.URLDecoder.decode((String)request.getParameter("jy2"),"UTF-8");
			String callTimeStart2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeStart2"),"UTF-8");
			String callTimeEnd2 = java.net.URLDecoder.decode((String)request.getParameter("callTimeEnd2"),"UTF-8");
			String frName2 = java.net.URLDecoder.decode((String)request.getParameter("frName2"),"UTF-8");
			String zw2 = java.net.URLDecoder.decode((String)request.getParameter("zw2"),"UTF-8");
			String qsName2 = java.net.URLDecoder.decode((String)request.getParameter("qsName2"),"UTF-8");
			String pageNo2=request.getParameter("pageNo2");
			String type2=request.getParameter("type2");
			String recRatingState2=request.getParameter("recRatingState2");
			String recAssessmentState2=request.getParameter("recAssessmentState2");
			String recordOverTime2=request.getParameter("recordOverTime2");
			request.setAttribute("frNo3", frNo3);
			request.setAttribute("pageNo2", pageNo2);
			request.setAttribute("jq2", jq2);
			request.setAttribute("jy2", jy2);
			request.setAttribute("callTimeStart2", callTimeStart2);
			request.setAttribute("callTimeEnd2", callTimeEnd2);
			request.setAttribute("frName2", frName2);
			request.setAttribute("zw2", zw2);
			request.setAttribute("qsName2", qsName2);
			request.setAttribute("type2", type2);
			request.setAttribute("recRatingState2", recRatingState2);
			request.setAttribute("recAssessmentState2", recAssessmentState2);
			request.setAttribute("recordOverTime2", recordOverTime2);
			return mapping.findForward("allZhushi");
	}
	public ActionForward dcHjRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			Date date=new Date(System.currentTimeMillis());
		    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss"); 
		    String now=df1.format(date);
		    String filenameTemp = "D:\\\\"+now.substring(0,4)+now.substring(5,7)+"会见记录.xls"; 
		    File filename = new File(filenameTemp); 
		    if (!filename.exists()){
		    	filename.createNewFile(); 
		    } 
			WritableWorkbook wwb6 = null;
			wwb6 = Workbook.createWorkbook(filename);
			WritableSheet ws6 = wwb6.createSheet("会见记录", 0);
			ws6.addCell(new Label(0, 0, "开始时间"));
			ws6.addCell(new Label(1, 0, "结束时间"));
			ws6.addCell(new Label(2, 0, "会见时长（秒）"));
			ws6.addCell(new Label(3, 0, "座位"));
			ws6.addCell(new Label(4, 0, "会见类型"));
			ws6.addCell(new Label(5, 0, "监区名称"));
			ws6.addCell(new Label(6, 0, "罪犯编号"));
			ws6.addCell(new Label(7, 0, "罪犯姓名"));
			ws6.addCell(new Label(8, 0, "亲属个数"));
			ws6.addCell(new Label(9, 0, "亲属信息"));
			ws6.addCell(new Label(10, 0, "警察编号"));
			ws6.addCell(new Label(11, 0, "警察姓名"));
			ws6.addCell(new Label(12, 0, "登记人"));
			ws6.addCell(new Label(13, 0, "登记时间"));
			ws6.addCell(new Label(14, 0, "罪犯返回时间"));
			ws6.addCell(new Label(15, 0, "返回签到警察"));
			ws6.addCell(new Label(16, 0, "评级状态（0:未评；1:异常；2:正常；）"));
			ws6.addCell(new Label(17, 0, "复听状态（0:未听；1:已听；）"));
			ws6.addCell(new Label(18, 0, "亲属详细信息"));
			HjRecordForm rscf = (HjRecordForm) form;
			if ((rscf.getRecAssessmentState() != null && !rscf.getRecAssessmentState().equals("null")) || (rscf.getRecRatingState() != null && !rscf.getRecRatingState().equals("null")) || rscf.getCallTimeEnd() != null || rscf.getCallTimeStart() != null  || (rscf.getType() != null && !rscf.getType().equals("null")) || (rscf.getJqNo() != null && !rscf.getJqNo().equals("null")) || rscf.getQsName() != null || (rscf.getJy() != null && !rscf.getJy().equals("null")) || rscf.getFrNo() != null || rscf.getFrName() != null) {
				StringBuffer str1 = new StringBuffer();
				if (rscf.getCallTimeStart() != null && !rscf.getCallTimeStart().trim().equals("")) {
					str1.append(" and rec.Call_Time_Start>='"+ rscf.getCallTimeStart() + "'");
				}
				if (rscf.getCallTimeEnd() != null && !rscf.getCallTimeEnd().trim().equals("")) {
					str1.append(" and rec.Call_Time_Start<='"+ rscf.getCallTimeEnd() + "'");
				}
				if (rscf.getFrNo() != null && !rscf.getFrNo().equals("")) {
					str1.append(" and rec.FR_No='" + rscf.getFrNo() + "'");
				}
				if (rscf.getFrName() != null && !rscf.getFrName().trim().equals("")) {
					str1.append(" and rec.FR_Name like '%" + rscf.getFrName() + "%'");
				}
				if (rscf.getType() != null && !rscf.getType().equals("null")) {
					str1.append(" and rec.HJ_Type=" + Integer.parseInt(rscf.getType()));
				}
				if (rscf.getRecRatingState() != null && !rscf.getRecRatingState().equals("null")) {
					str1.append(" and bb.Rec_Rating_State=" + Integer.parseInt(rscf.getRecRatingState()));
				}
				if (rscf.getRecAssessmentState() != null && !rscf.getRecAssessmentState().equals("null")) {
					str1.append(" and bb.Rec_Assessment_State=" + Integer.parseInt(rscf.getRecAssessmentState()));
				}
				if (rscf.getJqNo() != null && !rscf.getJqNo().equals("null")) {
					str1.append(" and rec.JQ_No='" + rscf.getJqNo() + "'");
				}
				if (rscf.getJy() != null && !rscf.getJy().equals("null")) {
					str1.append(" and rec.JY='" + rscf.getJy() + "'");
				}
				if(rscf.getZw()!=null && !rscf.getZw().equals("")){
					str1.append(" and rec.zw like '%"+rscf.getZw()+"%'");
				}
				if (rscf.getQsName() != null && !rscf.getQsName().equals("")) {
					str1.append(" and (rec.QS_Info1 like '%" + rscf.getQsName() + "%' or rec.QS_Info2 like '%"+ rscf.getQsName() + "%' or rec.QS_Info3 like '%"+ rscf.getQsName() + "%' or rec.QS_Info4 like '%"+ rscf.getQsName() + "%' or rec.QS_Info5 like '%"+ rscf.getQsName() + "%' or rec.QS_Info6 like '%"+ rscf.getQsName() + "%' or rec.QS_Info7 like '%"+ rscf.getQsName() + "%' or rec.QS_Info8 like '%"+ rscf.getQsName() + "%' or rec.QS_Info9 like '%"+ rscf.getQsName() + "%')");
				}
				StringBuffer str2 = new StringBuffer();
				if (user.getIsSuper() == 1) {
					str2.append("select cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,jl.JQ_Name,rec.zw,rec.YJ_No,rec.YJ_Name,rec.DJ_User,rec.DJ_Time,rec.FR_Out_Time,rec.FR_Out_User,rec.Rec_Rating_State,rec.Rec_Assessment_State,rec.HJID,dbo.f_get_hj_qs_info(rec.HJID) as allqsinfo from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver where  rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name");
				} else if (user.getGroupNo().trim().equals("Admin")) {
					str2.append("select cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,jl.JQ_Name,rec.zw,rec.YJ_No,rec.YJ_Name,rec.DJ_User,rec.DJ_Time,rec.FR_Out_Time,rec.FR_Out_User,rec.Rec_Rating_State,rec.Rec_Assessment_State,rec.HJID,dbo.f_get_hj_qs_info(rec.HJID) as allqsinfo from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver where  rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and jl.Is_Ts!=1");
//					str2.append(" and rec.QS_Info1 not like '%其他%' and rec.QS_Info2 not like '%其他%' and rec.QS_Info3 not like '%其他%' and rec.QS_Info4 not like '%其他%' and rec.QS_Info5 not like '%其他%' and rec.QS_Info6 not like '%其他%' and rec.QS_Info7 not like '%其他%' and rec.QS_Info8 not like '%其他%' and rec.QS_Info9 not like '%其他%'");
					if(user.getIsBj()==0){
						str2.append(" and rec.HJID not in (select HJID from JL_HJ_SP sp where sp.HJID=rec.HJID)");
					}
				} else {
					str2.append("select cast(rec.call_Time_Start AS varchar(19)) AS Call_Time_Start,cast(rec.call_Time_End AS varchar(19)) AS Call_Time_End,rec.HJ_Type,rec.Call_Time_Len,rec.FR_No,rec.FR_Name,rec.QS_Info1,rec.QS_Info2,rec.QS_Info3,rec.QS_Info4,rec.QS_Info5,rec.QS_Info6,rec.QS_Info7,rec.QS_Info8,rec.QS_Info9,jl.JQ_Name,rec.zw,rec.YJ_No,rec.YJ_Name,rec.DJ_User,rec.DJ_Time,rec.FR_Out_Time,rec.FR_Out_User,rec.Rec_Rating_State,rec.Rec_Assessment_State,rec.HJID,dbo.f_get_hj_qs_info(rec.HJID) as allqsinfo from jl_hj_rec rec,jl_jq jl,sys_hj_server jlserver,sys_user_jq suj where  rec.jq_No=jl.jq_No and rec.jy=jlserver.server_Name and jl.jq_No=suj.jq_No");
					str2.append(" and suj.group_No='" + user.getGroupNo() + "'");
//					str2.append(" and rec.QS_Info1 not like '%其他%' and rec.QS_Info2 not like '%其他%' and rec.QS_Info3 not like '%其他%' and rec.QS_Info4 not like '%其他%' and rec.QS_Info5 not like '%其他%' and rec.QS_Info6 not like '%其他%' and rec.QS_Info7 not like '%其他%' and rec.QS_Info8 not like '%其他%' and rec.QS_Info9 not like '%其他%'");
					if(user.getIsBj()==0){
						str2.append(" and rec.HJID not in (select HJID from JL_HJ_SP sp where sp.HJID=rec.HJID)");
					}
				}
				str2.append(str1.toString());
				str2.append(" order by rec.Call_Time_Start desc");
				List list2 = hrs.searchAllBySql(str2.toString());
				for(int i=0;i<list2.size();i++) {
					Object[] obj1 = (Object[]) list2.get(i);
					RecordSearch recordSearch = new RecordSearch();
					if (obj1[0] != null && obj1[0].toString() != "") {
						ws6.addCell(new Label(0, i+1, obj1[0].toString()));
					}
					if (obj1[1] != null && obj1[1].toString() != "") {
						ws6.addCell(new Label(1, i+1, obj1[1].toString()));
					}	
					if (obj1[2] != null && obj1[2].toString() != "") {
						if(obj1[2].toString().equals("1")){
							ws6.addCell(new Label(4, i+1, "电话会见"));
						}else if(obj1[2].toString().equals("2")){
							ws6.addCell(new Label(4, i+1, "宽管"));
						}else if(obj1[2].toString().equals("3")){
							ws6.addCell(new Label(4, i+1, "普管"));
						}else if(obj1[2].toString().equals("4")){
							ws6.addCell(new Label(4, i+1, "帮教"));
						}else if(obj1[2].toString().equals("5")){
							ws6.addCell(new Label(4, i+1, "提审"));
						}
					}
					if (obj1[3] != null && obj1[3].toString() != "") {
						ws6.addCell(new Label(2, i+1, Integer.parseInt(obj1[3].toString())+""));
					}
					if (obj1[4] != null && obj1[4].toString() != "") {
						ws6.addCell(new Label(6, i+1, obj1[4].toString()));
					}
					if (obj1[5] != null && obj1[5].toString() != "") {
						ws6.addCell(new Label(7, i+1, obj1[5].toString()));
					}
					String info="";
					int index=0;
					if (obj1[6] != null && !obj1[6].toString().trim().equals("")) {
						info+=obj1[6].toString();
						index++;
					}
					if (obj1[7] != null && !obj1[7].toString().trim().equals("")) {
						info+=obj1[7].toString();
						index++;
					}
					if (obj1[8] != null && !obj1[8].toString().trim().equals("")) {
						info+=obj1[8].toString();
						index++;
					}
					if (obj1[9] != null && !obj1[9].toString().trim().equals("")) {
						info+=obj1[9].toString();
						index++;
					}
					if (obj1[10] != null && !obj1[10].toString().trim().equals("")) {
						info+=obj1[10].toString();
						index++;
					}
					if (obj1[11] != null && !obj1[11].toString().trim().equals("")) {
						info+=obj1[11].toString();
						index++;
					}
					if (obj1[12] != null && !obj1[12].toString().trim().equals("")) {
						info+=obj1[12].toString();
						index++;
					}
					if (obj1[13] != null && !obj1[13].toString().trim().equals("")) {
						info+=obj1[13].toString();
						index++;
					}
					if (obj1[14] != null && !obj1[14].toString().trim().equals("")) {
						info+=obj1[14].toString();
						index++;
					}
					ws6.addCell(new Label(8, i+1, ""+index));
					ws6.addCell(new Label(9, i+1, info));
					if (obj1[15] != null && obj1[15].toString() != "") {
						ws6.addCell(new Label(5, i+1, obj1[15].toString()));
					}
					if (obj1[16] != null && obj1[16].toString() != "") {
						ws6.addCell(new Label(3, i+1, obj1[16].toString()));
					}
					if (obj1[17] != null && obj1[17].toString() != "") {
						ws6.addCell(new Label(10, i+1, obj1[17].toString()));
					}
					if (obj1[18] != null && obj1[18].toString() != "") {
						ws6.addCell(new Label(11, i+1, obj1[18].toString()));
					}
					if (obj1[19] != null && obj1[19].toString() != "") {
						ws6.addCell(new Label(12, i+1, obj1[19].toString()));
					}
					if (obj1[20] != null && obj1[20].toString() != "") {
						ws6.addCell(new Label(13, i+1, obj1[20].toString().substring(0, 19)));
					}
					if (obj1[21] != null && obj1[21].toString() != "") {
						ws6.addCell(new Label(14, i+1, obj1[21].toString().substring(0, 19)));
					}
					if (obj1[22] != null && obj1[22].toString() != "") {
						ws6.addCell(new Label(15, i+1, obj1[22].toString()));
					}
					ws6.addCell(new Label(16, i+1, obj1[23].toString()));
					ws6.addCell(new Label(17, i+1, obj1[24].toString()));
					if (obj1[26] != null && obj1[26].toString() != "") {
						ws6.addCell(new Label(18, i+1, obj1[26].toString()));
					}
					
				}
			} 
			wwb6.write();
			wwb6.close(); 
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
			fis = new FileInputStream(filename);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			response.addHeader("Content-Disposition","attachment; filename="+ URLEncoder.encode(filenameTemp, "utf-8"));
			response.setContentType("application/octet-stream");
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
			return null;
	}
	public ActionForward pldownFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String path = java.net.URLDecoder.decode((String) request.getParameter("path"), "UTF-8");
			String path1 = java.net.URLDecoder.decode((String) request.getParameter("path1"), "UTF-8");
			String path2 = java.net.URLDecoder.decode((String) request.getParameter("path2"), "UTF-8");
			String filepath="D:\\Demo.zip";
			File file =new File(filepath);
			ZipOutputStream out =null;
			byte[] buffer = new byte[1024];
			if(file.exists()){
				file.delete();
				file.createNewFile();
				out= new ZipOutputStream(new FileOutputStream(filepath));
			}else{
				file.createNewFile();
				out= new ZipOutputStream(new FileOutputStream(filepath));
			}
			File[] file1 = {new File(path),new File(path1),new File(path2)};
			for(int i=0;i<file1.length;i++) {
				FileInputStream fis = new FileInputStream(file1[i]);
				out.putNextEntry(new ZipEntry(file1[i].getName()));
				  int len;
				  //读入需要下载的文件的内容，打包到zip文件
		          while((len = fis.read(buffer))>0) {
		        	  out.write(buffer,0,len); 

		          }
		          out.closeEntry();
		          fis.close();
			}
			out.close();
	        System.out.println("生成Demo.zip");
	        
	       // System.out.println("下载Demo.zip");
	        
	        BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
	        
	        String filePath =filepath;// 本地
	        File uploadFile = new File(filePath);
	        fis = new FileInputStream(uploadFile);
	        bis = new BufferedInputStream(fis);
	        fos = response.getOutputStream();
	        bos = new BufferedOutputStream(fos);
	        response.setHeader("Content-Disposition",
	        		"attachment;filename=" +
	        		URLEncoder.encode(uploadFile.getName(), "utf-8"));
	        int bytesRead = 0;
	        byte[] buffer1 = new byte[8192];
	        while ((bytesRead = bis.read(buffer1, 0, 8192)) != -1) {
	        	bos.write(buffer1, 0, bytesRead);
	        }
	        bos.flush();
	        fis.close();
	        bis.close();
	        fos.close();
	        bos.close();
	        return null;
	}
	// 修改复听状态
	public ActionForward updateSaveAssessmentState(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) 
			throws Exception {
			SysUser user = (SysUser) request.getSession().getAttribute("users");
			if (user == null) {
				return mapping.findForward("sessionFailure");
			}
			String callId = request.getParameter("callId");
			String webId = request.getParameter("webId");
			
//			String userNo = java.net.URLDecoder.decode((String) request.getParameter("userNo"), "UTF-8");
//			String userName = java.net.URLDecoder.decode((String) request.getParameter("userName"), "UTF-8");
//			String writeTxt = java.net.URLDecoder.decode((String) request.getParameter("writeTxt"), "UTF-8");

			JlHjRecAssessmentInfo jlHjRecAssessmentInfo = new JlHjRecAssessmentInfo();
			jlHjRecAssessmentInfo.setCallId(callId);
			jlHjRecAssessmentInfo.setUserNo(user.getUserNo());
			jlHjRecAssessmentInfo.setUserName(user.getUserName());
			Timestamp now1=new Timestamp(System.currentTimeMillis());
			jlHjRecAssessmentInfo.setCreateTime(now1);
			hrs.save(jlHjRecAssessmentInfo);
			StringBuffer str=new StringBuffer("update JlHjRec sql set ");
			
			str.append("sql.recAssessmentState=1");
			
			str.append(" where sql.webId=?");
			Object[] obj={Long.parseLong(webId)};
			hrs.updates(str.toString(), obj);
			JSONArray json = JSONArray.fromObject(null);
			response.getWriter().println(json.toString());
			return null;
	}
	public ActionForward showPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    String webId=request.getParameter("qsid");
			String sql="from JlHjDjQs where webId="+webId;
			List<JlHjDjQs> list=hrs.searchAll(sql);
			if(list.size()>0){
				JlHjDjQs jlQs=(JlHjDjQs)list.get(0);
				if(jlQs.getZp()!=null){
					response.setContentType( "image/jpg");
					response.getOutputStream().write(jlQs.getZp()); 
				}
			}
			return null;
	}
}
