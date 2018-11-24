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
import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjInfoService;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.util.Config;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@Service("jlHjRecSQL")
public class JlHjRecServiceImpl extends BaseSqlImpl<JlHjRecVO> implements JlHjRecService{

	@Autowired
	private JlHjInfoService jlHjInfoSQL;
	
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
	
	public void downFile(HttpServletRequest request, HttpServletResponse response) {
		try {
			String webId = request.getParameter("webId");
			
			if(StringUtils.isBlank(webId)){
				return;
			}
			JlHjRecVO jlHjRec = this.findOne(webId);
			
			List<String> fileList = new ArrayList<>();
			if(StringUtils.isNotBlank(jlHjRec.getCallVideofile1())){
				fileList.add(jlHjRec.getCallVideofile1());
			}
			if(StringUtils.isNotBlank(jlHjRec.getCallVideofile2())){
				fileList.add(jlHjRec.getCallVideofile2());
			}
			if(StringUtils.isNotBlank(jlHjRec.getCallRecfile())){
				fileList.add(jlHjRec.getCallRecfile());
			}
			OutputStream os = new BufferedOutputStream( new FileOutputStream( "jiankong.zip" ) );
	        ZipOutputStream zos = new ZipOutputStream( os );
	        byte[] buf = new byte[8192];
	        int len;
	        for (String filePath: fileList) {  
	            File file = new File( filePath );
	            if ( !file.isFile() ) continue;
	            ZipEntry ze = new ZipEntry( file.getName() );
	            zos.putNextEntry( ze );
	            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
	            while ( ( len = bis.read( buf ) ) > 0 ) {
	                zos.write( buf, 0, len );
	            }
	            zos.closeEntry();
	        }
//	        zos.setEncoding("GBK");
	        zos.closeEntry();
	        zos.close();
	        
//			for(int i=0;i<file1.length;i++) {
//				FileInputStream fis = new FileInputStream(file1[i]);
//				out.putNextEntry(new ZipEntry(file1[i].getName()));
//				  int len;
//				  //读入需要下载的文件的内容，打包到zip文件
//		          while((len = fis.read(buffer))>0) {
//		        	  out.write(buffer,0,len); 
	//
//		          }
//		          out.closeEntry();
//		          fis.close();
//			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}finally{

		}
		
        
	}
}
