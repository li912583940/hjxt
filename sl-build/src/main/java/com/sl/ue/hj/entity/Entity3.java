package com.sl.ue.hj.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class Entity3 {

	private String path ="G:\\声联项目\\hjxt\\HjSystem\\src\\main\\java\\com\\sl\\ue\\entity";
	
	/** 目前直接从实体类读取的*/
	
	
	
	public void executeVO(String fileName,  String pick){
		String startPath = path+"\\"+pick+"\\vo";
		File folder = new File(startPath);
		if(!folder.exists()){
			folder.mkdirs();
		}
		String url = startPath+"\\"+fileName+"VO.java";
		File file = new File(url); 
		if(file.exists()){
			System.out.println("文件已存在： "+url);
		}else{
			writeFileVO(url, fileName, "."+pick);
		}
	}
	
	private void writeFileVO(String filePath, String fileName, String _pack){
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filePath);
			StringBuffer sb = new StringBuffer();
			sb.append("package com.sl.ue.entity"+_pack+".vo;").append("\r\n");
			sb.append("\r\n");
			sb.append("import com.sl.ue.entity"+_pack+"."+fileName+";").append("\r\n");
			sb.append("\r\n");
			sb.append("public class "+fileName+"VO extends "+fileName+"{").append("\r\n");
			sb.append("\r\n");
			sb.append("    /** 序列化 */").append("\r\n");
			sb.append("    private static final long serialVersionUID = 1L;").append("\r\n");
			sb.append("\r\n");
			sb.append("\r\n");
			sb.append("\r\n");
			sb.append("    /*---------------------------  处理关联表  -----------------------------*/").append("\r\n");
			sb.append("\r\n");
			sb.append("    private String leftJoinField; // 关联表字段").append("\r\n");
			sb.append("\r\n");
			sb.append("    private String leftJoinTable; // 关联表").append("\r\n");
			sb.append("\r\n");
			sb.append("    private String leftJoinWhere; // 关联表条件").append("\r\n");
			sb.append("\r\n");
			sb.append("    public String getLeftJoinField() {").append("\r\n");
			sb.append("        return leftJoinField;").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    public void setLeftJoinField(String leftJoinField) {").append("\r\n");
			sb.append("        this.leftJoinField = leftJoinField;").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    public String getLeftJoinTable() {").append("\r\n");
			sb.append("        return leftJoinTable;").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    public void setLeftJoinTable(String leftJoinTable) {").append("\r\n");
			sb.append("        this.leftJoinTable = leftJoinTable;").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    public String getLeftJoinWhere() {").append("\r\n");
			sb.append("        return leftJoinWhere;").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
			sb.append("    public void setLeftJoinWhere(String leftJoinWhere) {").append("\r\n");
			sb.append("        this.leftJoinWhere = leftJoinWhere;").append("\r\n");
			sb.append("    }").append("\r\n");
			sb.append("\r\n");
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
