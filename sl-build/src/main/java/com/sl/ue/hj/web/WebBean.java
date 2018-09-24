package com.sl.ue.hj.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.sl.ue.hj.util.StringUtil;

/**
 * 说明 [ web]
 * @author lxt
 */
public class WebBean {

	private String path ="D:\\声联项目\\hjxt\\HjSystem\\src\\main\\java\\com\\sl\\ue\\web";
	
	public void execute(String fileName,  String pick){
		String startPath = path+"\\"+pick;
		File folder = new File(startPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		String url = startPath+"\\"+fileName+"Web.java";
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFile(url, fileName, "."+pick);
		}
	}
	
	
	private void writeFile(String filePath, String fileName, String _pack){
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filePath);
			StringBuffer sb = new StringBuffer();
			sb.append("package com.sl.ue.web"+_pack+";").append("\r\n");
			sb.append("\r\n");
			sb.append("import java.util.List;").append("\r\n");
			sb.append("import java.util.Map;").append("\r\n");
			sb.append("\r\n");
			sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
			sb.append("import org.springframework.web.bind.annotation.RestController;").append("\r\n");
			sb.append("import org.springframework.web.bind.annotation.RequestMapping;").append("\r\n");
			sb.append("\r\n");
			sb.append("import com.sl.ue.entity"+_pack+".vo."+fileName+"VO;").append("\r\n");
			sb.append("import com.sl.ue.service"+_pack+"."+fileName+"Service;").append("\r\n");
			sb.append("import com.sl.ue.util.http.Result;").append("\r\n");
			sb.append("\r\n");
			sb.append("@RestController").append("\r\n");
			sb.append("@RequestMapping(\"/"+StringUtil.toLower(fileName)+"\")").append("\r\n");
			sb.append("public class "+fileName+"Web extends Result{").append("\r\n");
			sb.append("\r\n");
			sb.append("    @Autowired").append("\r\n");
			sb.append("    private "+fileName+"Service "+StringUtil.toLower(fileName)+"SQL;").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/findList\")").append("\r\n");
			sb.append("    public String findList("+fileName+"VO model,Integer pageSize, Integer pageNum){").append("\r\n");
			sb.append("        List<"+fileName+"VO> list = "+StringUtil.toLower(fileName)+"SQL.findList(model, pageSize, pageNum);").append("\r\n");
			sb.append("        this.putData(list);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			
			sb.append("    @RequestMapping(\"/findPojo\")").append("\r\n");
			sb.append("    public String findPojo("+fileName+"VO model, Integer pageSize, Integer pageNum){").append("\r\n");
			sb.append("        Map<String, Object> map = "+StringUtil.toLower(fileName)+"SQL.findPojo(model, pageSize, pageNum);").append("\r\n");
			sb.append("        this.putPojo(map);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			
			sb.append("    @RequestMapping(\"/findCount\")").append("\r\n");
			sb.append("    public String findCount("+fileName+"VO model){").append("\r\n");
			sb.append("        Integer count = "+StringUtil.toLower(fileName)+"SQL.count(model);").append("\r\n");
			sb.append("        this.putJson(\"count\", count);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			
			sb.append("    @RequestMapping(\"/findOne\")").append("\r\n");
			sb.append("    public String findOne(Integer id){").append("\r\n");
			sb.append("        "+fileName+"VO model = "+StringUtil.toLower(fileName)+"SQL.findOne(id);").append("\r\n");
			sb.append("        this.putJson(model);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/add\")").append("\r\n");
			sb.append("    public String add("+fileName+"VO model){").append("\r\n");
			sb.append("        "+StringUtil.toLower(fileName)+"SQL.add(model);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/edit\")").append("\r\n");
			sb.append("    public String edit("+fileName+"VO model){").append("\r\n");
			sb.append("        "+StringUtil.toLower(fileName)+"SQL.edit(model);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    @RequestMapping(\"/delete\")").append("\r\n");
			sb.append("    public String del(Integer id){").append("\r\n");
			sb.append("        "+StringUtil.toLower(fileName)+"SQL.deleteKey(id);").append("\r\n");
			sb.append("        return this.toResult();").append("\r\n");
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
