package com.sl.ue.hj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.sl.ue.hj.util.StringUtil;


public class ServiceBean {

	private String path ="D:\\声联项目\\hjxt\\HjSystem\\src\\main\\java\\com\\sl\\ue\\service";
	
	public void execute(String fileName, String pick){
		String startPath = path+"\\"+pick; // 文件夹
		File folder = new File(startPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		String url = startPath+"\\"+fileName+"Service.java"; //Java文件
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFile(url, fileName, "."+pick);
		}
		executeImpl(fileName, pick);
	}
	
	private void executeImpl(String fileName, String pick){
		String startPath = path+"\\"+pick+"\\sqlImpl"; // 文件夹
		File folder = new File(startPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		String url = startPath+"\\"+fileName+"ServiceImpl.java"; //Java文件
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFileImpl(url, fileName, "."+pick);
		}
	}
	
	/**
	 * 说明 [写入文件]
	 * filePath  要写入的文件 路径
	 * fileName 实体类名 不带.java
	 * _pack 包名
	 * @author lxt
	 */
	private void writeFile(String filePath, String fileName, String _pack){
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filePath);
			StringBuffer sb = new StringBuffer();
			sb.append("package com.sl.ue.service"+_pack+";").append("\r\n");
			sb.append("\r\n");
			sb.append("import com.sl.ue.entity"+_pack+".vo."+fileName+"VO;").append("\r\n");
			sb.append("import com.sl.ue.service.base.BaseService;").append("\r\n");
			sb.append("\r\n");
			sb.append("public interface "+fileName+"Service extends BaseService<"+fileName+"VO>{").append("\r\n");
			sb.append("\r\n");
			sb.append("}");
			fos = new FileOutputStream(file);
			pw= new PrintWriter(fos);
			pw.write(sb.toString().toCharArray());
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pw != null){
				pw.close();
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 说明 [写入文件]
	 * filePath  要写入的文件 路径
	 * fileName 实体类名 不带.java
	 * _pack 包名
	 * @author lxt
	 */
	private void writeFileImpl(String filePath, String fileName, String _pack){
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filePath);
			StringBuffer sb = new StringBuffer();
			sb.append("package com.sl.ue.service"+_pack+".sqlImpl;").append("\r\n");
			sb.append("\r\n");
			sb.append("import org.springframework.stereotype.Service;").append("\r\n");
			sb.append("\r\n");
			sb.append("import com.sl.ue.entity"+_pack+".vo."+fileName+"VO;").append("\r\n");
			sb.append("import com.sl.ue.service.base.impl.BaseSqlImpl;").append("\r\n");
			sb.append("import com.sl.ue.service"+_pack+"."+fileName+"Service;").append("\r\n");
			sb.append("\r\n");
			sb.append("@Service(\""+StringUtil.toLower(fileName)+"SQL\")").append("\r\n");
			sb.append("public class "+fileName+"ServiceImpl extends BaseSqlImpl<"+fileName+"VO> implements "+fileName+"Service{").append("\r\n");
			sb.append("\r\n");
			sb.append("}").append("\r\n");
			fos = new FileOutputStream(file);
			pw= new PrintWriter(fos);
			pw.write(sb.toString().toCharArray());
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(pw != null){
				pw.close();
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
