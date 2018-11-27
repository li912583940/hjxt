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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjInfoVO;
import com.sl.ue.entity.jl.vo.JlHjMonVO;
import com.sl.ue.entity.jl.vo.JlHjRecRatingInfoVO;
import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjInfoService;
import com.sl.ue.service.jl.JlHjRecRatingInfoService;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.util.Config;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@Service("jlHjRecSQL")
public class JlHjRecServiceImpl extends BaseSqlImpl<JlHjRecVO> implements JlHjRecService{

	@Autowired
	private JlHjInfoService jlHjInfoSQL;
	@Autowired
	private SysHjServerService sysHjServerSQL;
	@Autowired
	private JlHjRecRatingInfoService jlHjRecRatingInfoSQL;
	
	@Override
	public Map<String, Object> findPojoLeft(JlHjRecVO model, Integer pageSize, Integer pageNum) {
		StringBuffer leftJoinWhere = new StringBuffer();
    	if(StringUtils.isNotBlank(model.getCallTimeStart())){ // 开始时间
    		leftJoinWhere.append(" AND a.Call_Time_Start>='"+ model.getCallTimeStart() + "' ");
    		model.setCallTimeStart(null);
    	}
    	if(StringUtils.isNotBlank(model.getCallTimeEnd())){ // 结束时间
    		leftJoinWhere.append(" AND a.Call_Time_Start<='"+ model.getCallTimeEnd() + "' ");
    		model.setCallTimeEnd(null);
    	}
    	if(StringUtils.isNotBlank(model.getFrName())){
    		leftJoinWhere.append(" AND a.FR_Name LIKE '%"+model.getFrName()+"%' ");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(model.getQsName())){
    		
    	}
    	model.setLeftJoinWhere(leftJoinWhere.toString());
    	Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
    	List<JlHjRecVO> list = (List<JlHjRecVO>) map.get("list");
    	List<SysHjServerVO> sysHjServerList = sysHjServerSQL.findList(new SysHjServerVO());
    	
    	String callRecPath = Config.getPropertiesValue("callRecfile");
    	String callVideoPath1 = Config.getPropertiesValue("callVideofile1");
    	String callVideoPath2 = Config.getPropertiesValue("callVideofile2");
    	for(JlHjRecVO hjRec : list){
    		for(SysHjServerVO hjServer: sysHjServerList){
    			if(hjRec.getJy().equals(hjServer.getServerName())){
    				if(StringUtils.isNotBlank(hjRec.getCallRecfile())){
    					String end =hjRec.getCallRecfile().replace("\\", "/");
        				end = end.substring(end.indexOf("/")+1);
        				end = end.substring(end.indexOf("/"));
        				String url = hjServer.getRecUrl()+callRecPath+end;
        				hjRec.setCallRecfileUrl(url);
    				}
    				if(StringUtils.isNotBlank(hjRec.getCallVideofile1())){
    					String end = hjRec.getCallVideofile1().replace("\\", "/");
    					end = end.substring(end.indexOf("/")+1);
        				end = end.substring(end.indexOf("/"));
        				String url = hjServer.getRecUrl()+callVideoPath1+end;
        				hjRec.setCallVideofile1Url(url);
    				}
    				if(StringUtils.isNotBlank(hjRec.getCallVideofile2())){
    					String end = hjRec.getCallVideofile2().replace("\\", "/");
    					end = end.substring(end.indexOf("/")+1);
        				end = end.substring(end.indexOf("/"));
        				String url = hjServer.getRecUrl()+callVideoPath2+end;
        				hjRec.setCallVideofile2Url(url);
    				}
    			}
    		}
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
	
	public String updateRatingState(Long webId, Integer recRatingState, String writeTxt){
		Result result = new Result();
		if(webId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjRecVO jlHjRec = this.findOne(webId);
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
		jlHjRecRatingInfo.setUserName(sysUser.getUserName());
		jlHjRecRatingInfo.setWriteTxt(writeTxt);
		jlHjRecRatingInfo.setCreateTime(new Date());
		jlHjRecRatingInfoSQL.add(jlHjRecRatingInfo);
		
		return result.toResult();
	}
}
