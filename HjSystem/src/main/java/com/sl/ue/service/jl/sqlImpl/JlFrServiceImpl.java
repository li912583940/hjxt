package com.sl.ue.service.jl.sqlImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.util.Config;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.business.FrThreadXLS;
import com.sl.ue.util.business.FrThreadXLSX;

@Service("jlFrSQL")
public class JlFrServiceImpl extends BaseSqlImpl<JlFrVO> implements JlFrService{
	
	@Override
	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum) {
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
    	model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
    	model.setLeftJoinWhere(Where.toString());
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
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
	
	@Override
	public void exportExcel(JlFrVO model, HttpServletRequest request, HttpServletResponse response) {
		StringBuffer field = new StringBuffer();
		field.append(",b.JQ_Name");
		field.append(",c.JB_Name");
		
		
		StringBuffer table = new StringBuffer();
		table.append(" left join JL_JQ b ON a.JQ=b.JQ_No");
		table.append(" left join JL_JB c ON a.JB_No=c.JB_No");
		
		StringBuffer Where = new StringBuffer(); // sql条件
    	if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		Where.append(" AND (a.FR_Name LIKE '%"+str+"%' OR dbo.f_get_fryp(a.FR_Name,'"+str+"') =1 )");
    		model.setFrName(null);
    	}
    	
		model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
		model.setLeftJoinWhere(Where.toString());
		
		List<JlFrVO> frList = this.findList(model);
//		if(frList == null || frList.size() == 0){
//			response.setContentType("application/octet-stream");
//			return ;
//		}
		
		String fileName =  "罪犯信息.xls";
		
		OutputStream out = null;
		
		try {
			
			// EXCEL START
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet();
			CellStyle cellStyle = book.createCellStyle();
			cellStyle.setDataFormat(book.createDataFormat().getFormat("yyyy-MM-dd"));
			// 设置标题
			List<String> title = new ArrayList<String>();
			title.add("罪犯编号");
			title.add("姓名");
			title.add("监区");
			title.add("级别");
			title.add("当月会见次数");
			title.add("当月剩余次数");
			title.add("入监时间");
			title.add("重点罪犯");
			title.add("会见级别");
			// 标题 start
			HSSFRow row1 = sheet.createRow(0);
			for(int i=0; i<title.size(); i++){
				String t = title.get(i);
				HSSFCell cell = row1.createCell(i);
				cell.setCellValue(t);
			}
			// 标题 end
			
			// 记录 start
			for(int i=0; i<frList.size(); i++){
				JlFrVO jlFr = frList.get(i);
				HSSFRow row2 = sheet.createRow(i+1);
				
				HSSFCell cell0 = row2.createCell(0);
				cell0.setCellValue(jlFr.getFrNo());
					
				HSSFCell cell1 = row2.createCell(1);
				cell1.setCellValue(jlFr.getFrName()!=null?jlFr.getFrName():"");
				
				HSSFCell cell2 = row2.createCell(2);
				cell2.setCellValue(jlFr.getJqName()!=null?jlFr.getJqName():"");
				
				HSSFCell cell3 = row2.createCell(3);
				cell3.setCellValue(jlFr.getJbName()!=null?jlFr.getJbName():"");
				
				HSSFCell cell4 = row2.createCell(4);
				cell4.setCellValue(jlFr.getHjUse()!=null?jlFr.getHjUse():0);
				
				HSSFCell cell5 = row2.createCell(5);
				cell5.setCellValue(jlFr.getHjLeft()!=null?jlFr.getHjLeft():0);
				
				HSSFCell cell6 = row2.createCell(6);
				cell6.setCellValue(jlFr.getInfoRjsj()!=null?jlFr.getInfoRjsj():"");
				
				HSSFCell cell7 = row2.createCell(7);
				cell7.setCellValue(jlFr.getInfoZdzf()!=null?jlFr.getInfoZdzf():"");
				
				HSSFCell cell8 = row2.createCell(8);
				if(jlFr.getHjJb()!=null && jlFr.getHjJb()==1){
					cell8.setCellValue("正常");
				}else if(jlFr.getHjJb()!=null && jlFr.getHjJb()==-1){
					cell8.setCellValue("禁止");
				}else{
					cell8.setCellValue("未定义");
				}
				
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

	
	public String importExcel(HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile mFile = null;
		for (Iterator<?> i = fileMap.keySet().iterator(); i.hasNext();) {
			Object obj = i.next();
			mFile = (MultipartFile) fileMap.get(obj);
		}
		String datePath = Config.getPropertiesValue("file.path")+"/excelfile";
	    String nowStr = DateUtil.getFormat(new Date(), "yyyyMMddHHmmss");
		BufferedInputStream in =null;
		BufferedOutputStream out = null;
		String excelFilePath = null;
		String fileExt = null;
		try {
			// 得到上传的文件的文件名
			String filename = mFile.getOriginalFilename();
			// 获取文件后缀名
			if (filename == null || ("").equals(filename)) {
				return "上传文件不存在";
			}
			File file = new File(datePath);
			if(!file.exists()){
				file.mkdirs();
			}
			boolean isPattern = false;
			fileExt = filename.substring(filename.lastIndexOf(".")+1);
			if("xls".equals(fileExt) || "xlsx".equals(fileExt)){
				isPattern = true;
			}
			if(isPattern == false){
				return "不支持此格式";
			}
			excelFilePath = datePath+"/"+nowStr+"."+fileExt;//excel文件临时存储路径
			System.out.println("路径： "+excelFilePath);
			File saveFile = new File(excelFilePath);
			int l = 10*1024*1024;//1M 默认，可在配置文件中设置此值大小
			String typeBufferSize = Config.getPropertiesValue("file.buffer.size");
			if(!"".equals(typeBufferSize)){
				int tfz =Integer.parseInt(typeBufferSize.trim());
				l = tfz*1024;
			}
			in = new BufferedInputStream(mFile.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(saveFile));
			int byteCount = 0;
			byte[] buffer = new byte[l];
			int bytesRead = -1;//文件大小
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}finally {
			// 启动线程 处理罪犯信息
			if(StringUtils.isNotBlank(excelFilePath) && StringUtils.isNotBlank(fileExt)){
				if("xls".equals(fileExt)){
					FrThreadXLS frThread = new FrThreadXLS(excelFilePath);
					frThread.start();
				}else if("xlsx".equals(fileExt)){
					FrThreadXLSX frThread = new FrThreadXLSX(excelFilePath);
					frThread.start();
				}
				
			}
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
		
		return "";
	
	}
	
	
	public boolean frExist(String frNo){
		String sql = "SELECT ISNULL(count(*),0) as count FROM JL_FR where FR_No='"+frNo+"'";
		Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
}
