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
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.Config;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.business.FrThreadXLS;
import com.sl.ue.util.business.QsThreadXLS;
import com.sl.ue.util.business.QsThreadXLSX;
import com.sl.ue.util.http.Result;

@Service("jlQsSQL")
public class JlQsServiceImpl extends BaseSqlImpl<JlQsVO> implements JlQsService{

	public Map<String, Object> findPojoJoin(JlQsVO model, Integer pageSize, Integer pageNum){
		StringBuffer field = new StringBuffer();
		field.append(",b.FR_Name");
		
		StringBuffer table = new StringBuffer();
		table.append(" left join JL_FR b ON a.FR_No=b.FR_No");
		
		
		StringBuffer where = new StringBuffer(); // sql条件
		if(StringUtils.isNotBlank(model.getFrNo())){
			String str = model.getFrNo();
			where.append(" AND a.FR_No LIKE '%"+str+"%' ");
			model.setFrNo(null);
		}
    	if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		where.append(" AND b.FR_Name LIKE '%"+str+"%' ");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(model.getQsName())){
    		String str = model.getQsName();
    		where.append(" AND a.QS_Name LIKE '%"+str+"%' ");
    		model.setQsName(null);
    	}
		model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
		model.setLeftJoinWhere(where.toString());
		
		return this.findPojo(model, pageSize, pageNum);
	}
	@Override
	public void exportExcel(JlQsVO model, HttpServletRequest request, HttpServletResponse response) {
		StringBuffer field = new StringBuffer();
		field.append(",b.FR_Name");
		
		StringBuffer table = new StringBuffer();
		table.append(" left join JL_FR b ON a.FR_No=b.FR_No");
		
		
		StringBuffer where = new StringBuffer(); // sql条件
		if(StringUtils.isNotBlank(model.getFrNo())){
			String str = model.getFrNo();
			where.append(" AND a.FR_No LIKE '%"+str+"%' ");
			model.setFrNo(null);
		}
    	if(StringUtils.isNotBlank(model.getFrName())){
    		String str = model.getFrName();
    		where.append(" AND b.FR_Name LIKE '%"+str+"%' ");
    		model.setFrName(null);
    	}
    	if(StringUtils.isNotBlank(model.getQsName())){
    		String str = model.getQsName();
    		where.append(" AND a.QS_Name LIKE '%"+str+"%' ");
    		model.setQsName(null);
    	}
		model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
		model.setLeftJoinWhere(where.toString());
		
		List<JlQsVO> qsList = this.findList(model);
		
		String fileName =  "亲属信息.xls";
		
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
			title.add("罪犯姓名");
			title.add("证件类型");
			title.add("证件号码");
			title.add("家属姓名");
			title.add("性别");
			title.add("家属称谓");
			title.add("电话");
			title.add("家庭住址");
			// 标题 start
			HSSFRow row1 = sheet.createRow(0);
			for(int i=0; i<title.size(); i++){
				String t = title.get(i);
				HSSFCell cell = row1.createCell(i);
				cell.setCellValue(t);
			}
			// 标题 end
			
			// 记录 start
			for(int i=0; i<qsList.size(); i++){
				JlQsVO jlQs = qsList.get(i);
				HSSFRow row2 = sheet.createRow(i+1);
				
				HSSFCell cell0 = row2.createCell(0);
				cell0.setCellValue(jlQs.getFrNo());
					
				HSSFCell cell1 = row2.createCell(1);
				cell1.setCellValue(jlQs.getFrName()!=null?jlQs.getFrName():"");
				
				HSSFCell cell2 = row2.createCell(2);
				if(jlQs.getQsZjlb()==2){
					cell2.setCellValue("警官证");
				}else if(jlQs.getQsZjlb()==3){
					cell2.setCellValue("工作证");
				}else if(jlQs.getQsZjlb()==4){
					cell2.setCellValue("其他");
				}else{
					cell2.setCellValue("身份证");
				}
				
				HSSFCell cell3 = row2.createCell(3);
				cell3.setCellValue(jlQs.getQsSfz()!=null?jlQs.getQsSfz():"");
				
				HSSFCell cell4 = row2.createCell(4);
				cell4.setCellValue(jlQs.getQsName()!=null?jlQs.getQsName():"");
				
				HSSFCell cell5 = row2.createCell(5);
				cell5.setCellValue(jlQs.getXb()!=null?jlQs.getXb():"");
				
				HSSFCell cell6 = row2.createCell(6);
				cell6.setCellValue(jlQs.getGx()!=null?jlQs.getGx():"");
				
				HSSFCell cell7 = row2.createCell(7);
				cell7.setCellValue(jlQs.getTele()!=null?jlQs.getTele():"");
				
				HSSFCell cell8 = row2.createCell(8);
				cell8.setCellValue(jlQs.getDz()!=null?jlQs.getDz():"");
			}
			
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
					QsThreadXLS qsThread = new QsThreadXLS(excelFilePath);
					qsThread.start();
				}else if("xlsx".equals(fileExt)){
					QsThreadXLSX qsThread = new QsThreadXLSX(excelFilePath);
					qsThread.start();
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
	
	public boolean qsExist(String frNo, String qsSfz){
		if(StringUtils.isNotBlank(qsSfz)){
			String sql = "SELECT ISNULL(count(*),0) as count FROM JL_QS WHERE FR_No='"+frNo+"' AND QS_SFZ='"+qsSfz+"'";
			Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class);
			if(count>0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	@Override
	public String uploadWord(HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		Map<String, String> map = new HashMap<>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile mFile = null;
		for (Iterator<?> i = fileMap.keySet().iterator(); i.hasNext();) {
			Object obj = i.next();
			mFile = (MultipartFile) fileMap.get(obj);
		}
		String startPath = Config.getPropertiesValue("file.path");
		String qswordfile = "/qswordfile";
	    String nowStr = DateUtil.getFormat(new Date(), "yyyyMMddHHmmss");
		BufferedInputStream in =null;
		BufferedOutputStream out = null;
		String absolutelyFilePath = null;
		String fileExt = null;
		try {
			// 得到上传的文件的文件名
			String filename = mFile.getOriginalFilename();
			// 获取文件后缀名
			if (filename == null || ("").equals(filename)) {
				result.error(Result.error_102, "上传文件不存在");
				return result.toResult();
			}
			File file = new File(startPath+qswordfile);
			if(!file.exists()){
				file.mkdirs();
			}
			//boolean isPattern = false;
			fileExt = filename.substring(filename.lastIndexOf(".")+1);
//			if("xls".equals(fileExt) || "xlsx".equals(fileExt)){
//				isPattern = true;
//			}
//			if(isPattern == false){
//				return "不支持此格式";
//			}
			absolutelyFilePath = startPath+qswordfile+"/"+nowStr+"."+fileExt;//excel文件临时存储路径
			map.put("absolutelyFilePath", absolutelyFilePath); //绝对路径
			map.put("relativeFilePath", qswordfile+"/"+nowStr+"."+fileExt); //相对路径
			File saveFile = new File(absolutelyFilePath);
			int l = 10*1024*1024;//1M 默认，可在配置文件中设置此值大小
			String typeBufferSize = Config.getPropertiesValue("file.buffer.size");
			if(!"".equals(typeBufferSize)){
				int tfz =Integer.parseInt(typeBufferSize.trim());
				l = tfz*1024;
			}
			in = new BufferedInputStream(mFile.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(saveFile));
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
		result.putJson(map);
		return result.toResult();
	
	}
	@Override
	public void wordDownload(JlQsVO model, HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isBlank(model.getEnclosureUrl())){
			return;
		}
		String startPath = Config.getPropertiesValue("file.path");
		String filePath = startPath + model.getEnclosureUrl();
		File file = new File(filePath);
		if(!file.exists()){
			return;
		}
		OutputStream out = null;
		BufferedInputStream in =null;
		try {
			String fileName = model.getEnclosureUrl().substring(model.getEnclosureUrl().lastIndexOf("/")+1);;
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
			int l = 1*1024*1024;//1M 默认，可在配置文件中设置此值大小
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
