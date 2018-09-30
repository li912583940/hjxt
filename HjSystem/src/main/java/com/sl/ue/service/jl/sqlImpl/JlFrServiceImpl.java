package com.sl.ue.service.jl.sqlImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlQsService;

@Service("jlFrSQL")
public class JlFrServiceImpl extends BaseSqlImpl<JlFrVO> implements JlFrService{
	
	@Autowired
	private JlQsService jlQsSQL;
	@Override
	public Map<String, Object> findPojoJoin(JlFrVO model, Integer pageSize, Integer pageNum) {
		StringBuffer field = new StringBuffer();
		field.append(",b.JQ_Name");
		field.append(",c.JB_Name");
		
		
		StringBuffer table = new StringBuffer();
		table.append(" left join JL_JQ b ON a.JQ=b.JQ_No");
		table.append(" left join JL_JB c ON a.JB_No=c.JB_No");
		
		model.setLeftJoinField(field.toString());
		model.setLeftJoinTable(table.toString());
		
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		if(map.containsKey("list")) {
			List<JlFrVO> list = (List<JlFrVO>) map.get("list");
			for(JlFrVO jlFr : list) {
				JlQsVO jlQs = new JlQsVO();
				jlQs.setFrNo(jlFr.getFrNo());
				Integer qsNum= jlQsSQL.count(jlQs);
				jlFr.setQsNum(qsNum);
			}
		}
		return map;
	}
	@Override
	public void exportExcel(JlFrVO model,Integer pageSize, Integer pageNum, HttpServletRequest request, HttpServletResponse response) {
		List<JlFrVO> frList = this.findList(model, pageSize, pageNum);
		
		String path = request.getParameter("path");
		InputStream in = null;
		OutputStream out = null;
		try {
			String fileName =  "服刑人员记录";
			String userAgent=request.getHeader("USER-AGENT");
			if(userAgent.indexOf("Chrome")!=-1 || userAgent.indexOf("Safari")!=-1 || userAgent.indexOf("Firefox")!=-1){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}else{
				fileName = URLEncoder.encode(fileName,"UTF8");
			}
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			response.setContentType("application/octet-stream");
//			String basePath = Config.getPropertiesValue("file.path");
//			in = new FileInputStream(basePath+path);
			in = new FileInputStream(path);
			int l = 10*1024*1024;//10M 默认，可在配置文件中设置此值大小
			out = response.getOutputStream();
			byte[] buffer = new byte[l];
			int bytesRead = -1;//文件大小
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				//bos.close();
			}
			catch (IOException ex) {
			}
			try {
				out.close();
			} catch (IOException e2) {
			}
		}
		
	}


}
