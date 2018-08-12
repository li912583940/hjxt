package com.sl.ue.hj.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.sl.ue.hj.StringUtil;

/**
 * 说明 [ web]
 * @author lxt
 */
public class WebBean {

	private String path ="G:\\声联项目\\hjxt\\HjSystem\\src\\main\\java\\com\\sl\\ue\\web";
	private String hj = "\\hj";
	private String jl = "\\jl";
	private String sys = "\\sys";
	
	public void hj(String fileName){
		String[] fileNames = fileName.split("\\.");
		String url = path+hj+"\\"+fileNames[0]+"Web.java";
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFile(url, fileName, ".hj");
		}
	}
	
	public void jl(String fileName){
		String[] fileNames = fileName.split("\\.");
		String url = path+jl+"\\"+fileNames[0]+"Web.java";
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFile(url, fileName, ".jl");
		}
	}
	
	public void sys(String fileName){
		String[] fileNames = fileName.split("\\.");
		String url = path+sys+"\\"+fileNames[0]+"Web.java";
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFile(url, fileName, ".sys");
		}
	}
	
	private void writeFile(String filePath, String fileName, String _pack){
		String[] fileNames = fileName.split("\\.");
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filePath);
			StringBuffer sb = new StringBuffer();
			sb.append("package com.sl.ue.web"+_pack+";").append("\r\n");
			sb.append("\r\n");
			sb.append("import java.util.List;").append("\r\n");
			sb.append("\r\n");
			sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
			sb.append("import org.springframework.web.bind.annotation.RestController;").append("\r\n");
			sb.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");
			sb.append("\r\n");
			sb.append("import com.sl.ue.entity"+_pack+"."+fileNames[0]+";").append("\r\n");
			sb.append("import com.sl.ue.service"+_pack+"."+fileNames[0]+"Service;").append("\r\n");
			sb.append("import com.sl.ue.util.http.Result;").append("\r\n");
			sb.append("\r\n");
			sb.append("@RestController").append("\r\n");
			sb.append("@RequestMapping(\"/"+StringUtil.toLower(fileNames[0])+"\")").append("\r\n");
			sb.append("public class "+fileNames[0]+"Web extends Result{").append("\r\n");
			sb.append("\r\n");
			sb.append("    @Autowired").append("\r\n");
			sb.append("    private "+fileNames[0]+"Service "+fileNames[0]+"SQL;").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/findList\")").append("\r\n");
			sb.append("    public String findList(Integer pageSize, Integer pageNum){").append("\r\n");
			sb.append("        "+fileNames[0]+" model = new "+fileNames[0]+"();").append("\r\n");
			sb.append("        List<"+fileNames[0]+"> list = "+fileNames[0]+"SQL.baseFindList(model, pageSize, pageNum);").append("\r\n");
			sb.append("        this.putData(list);").append("\r\n");
			sb.append("        return this.toString();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/findOne\")").append("\r\n");
			sb.append("    public String findOne(Integer id){").append("\r\n");
			sb.append("        "+fileNames[0]+" model = "+fileNames[0]+"SQL.baseFindOne(id);").append("\r\n");
			sb.append("        this.putJson(model);").append("\r\n");
			sb.append("        return this.toString();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/add\")").append("\r\n");
			sb.append("    public String add("+fileNames[0]+" model){").append("\r\n");
			sb.append("        "+fileNames[0]+"SQL.baseAdd(model);").append("\r\n");
			sb.append("        succes();").append("\r\n");
			sb.append("        return this.toString();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/edit\")").append("\r\n");
			sb.append("    public String edit("+fileNames[0]+" model){").append("\r\n");
			sb.append("        "+fileNames[0]+"SQL.baseEdit(model);").append("\r\n");
			sb.append("        succes();").append("\r\n");
			sb.append("        return this.toString();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/delete\")").append("\r\n");
			sb.append("    public String del(Integer id){").append("\r\n");
			sb.append("        "+fileNames[0]+"SQL.baseDeleteKey(id);").append("\r\n");
			sb.append("        succes();").append("\r\n");
			sb.append("        return this.toString();").append("\r\n");
			sb.append("    }").append("\r\n");
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
