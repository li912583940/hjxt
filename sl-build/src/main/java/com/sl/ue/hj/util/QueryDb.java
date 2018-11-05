package com.sl.ue.hj.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * 说明 [查询数据库,得到所有的表,以及字段]
 * @注意 这个类不用动
 * L_晓天  @2018年9月2日
 */
public class QueryDb {

	public static void main(String[] args){
		System.out.println(Entity.enMap);
		
	}
	String tableName = ""; // 表名
	String tableExplain = ""; // 表说明
	String entityName = ""; // 实体类名
	List<Map<String, String>> fieldList = null; // 所有字段信息
	Set<String> importSet = new HashSet<String>();
	
	
	public void execute(){
		Connection conn = Db.getConnection();
		Map<String, String> sqlMap = Entity.enMap;
		for(String tableNameOld : sqlMap.keySet()){
			// 查询表说明
			String tableSql = "select "+
								  "isnull(cast(g.[value] as varchar(200)),'') AS table_explain "+
								"from "+
								  "sys.tables a left join sys.extended_properties g "+
								  "on (a.object_id = g.major_id AND g.minor_id = 0) "+
								"where a.name='"+tableNameOld+"'";
			// 查询所有字段信息
			String fieldSql = "SELECT "+
				         "col.colorder AS serial_number,"+
				         "col.name AS field ,"+
				         "ISNULL(cast(ep.[value] as varchar(200)), '') AS field_explain,"+
				         "t.name AS field_type,"+
				         "CASE WHEN EXISTS ( SELECT   1 "+
                            "FROM     dbo.sysindexes si "+
                                    "INNER JOIN dbo.sysindexkeys sik ON si.id = sik.id "+
                                                              "AND si.indid = sik.indid "+
                                    "INNER JOIN dbo.syscolumns sc ON sc.id = sik.id "+
                                                              "AND sc.colid = sik.colid "+
                                    "INNER JOIN dbo.sysobjects so ON so.name = si.name "+
                                                              "AND so.xtype = 'PK' "+
                            "WHERE    sc.id = col.id "+
                                    "AND sc.colid = col.colid ) THEN 'yes' "+
							             "ELSE '' "+
						"END AS pkid  "+
							"FROM    dbo.syscolumns col "+
							       " LEFT  JOIN dbo.systypes t ON col.xtype = t.xusertype "+
							        "inner JOIN dbo.sysobjects obj ON col.id = obj.id "+
							                                         "AND obj.xtype = 'U' "+
							                                         "AND obj.status >= 0 "+
							        "LEFT  JOIN sys.extended_properties ep ON col.id = ep.major_id "+
							                                                      "AND col.colid = ep.minor_id "+
							                                                      "AND ep.name = 'MS_Description' "+
							"WHERE   obj.name = '"+tableNameOld+"'";
			Statement stmt = null;
			try {
				tableName = tableNameOld;
				entityName = sqlMap.get(tableNameOld);
				stmt = conn.createStatement();
				ResultSet tableRs = stmt.executeQuery(tableSql);
				while(tableRs.next()){
					tableExplain = tableRs.getString("table_explain");
				}
				
				ResultSet fieldRs = stmt.executeQuery(fieldSql);
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				Set<String> set = new HashSet<String>();
				while(fieldRs.next()){
					Map<String, String> map = new HashMap<String, String>();
					map.put("field", fieldRs.getString("field")); //字段
					map.put("field_explain", fieldRs.getString("field_explain")); // 字段说明
					map.put("field_type", fieldRs.getString("field_type")); // 字段类型
					map.put("pkid", fieldRs.getString("pkid")); //是否为主键  yes
					list.add(map);
				}
				if(list.size()>0){
					fieldList = list;
					write();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					if(stmt!=null)stmt.close();
					if(conn!=null)conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	/**
	 * 说明 [生成实体类]
	 * L_晓天  @2018年9月3日
	 */
	private void write(){
		String packageName = StringUtil.getPackage(tableName);
		String url =Entity.url;
		url += "\\src\\main\\java\\com\\sl\\ue\\entity"; // 实体类路径
		url += "\\"+packageName; // 最终实体类路径
		checkFolder(url);
		String entityUrl = url+"\\"+entityName+".java";
		boolean check = checkFile(entityUrl);
		if(check){
			return;
		}
		
		initImport();
		
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			String package_ = "com.sl.ue.entity"+"."+packageName;
			File file = new File(entityUrl);
			StringBuffer sb = new StringBuffer();
			sb.append("package "+package_+";").append("\r\n");
			sb.append("\r\n");
			sb.append("import com.sl.ue.util.anno.DbField;").append("\r\n");
			sb.append("import com.sl.ue.util.anno.Id;").append("\r\n");
			sb.append("import com.sl.ue.util.anno.Table;").append("\r\n");
			for(String im : importSet){
				sb.append("import "+im+";").append("\r\n");
			}
			sb.append("\r\n");
			sb.append("@Table(\""+tableName+"\")").append("\r\n");
			sb.append("public class "+entityName+" implements java.io.Serializable{").append("\r\n");
			sb.append("\r\n");
			sb.append("    /** 序列化 */").append("\r\n");
			sb.append("    private static final long serialVersionUID = 1L;").append("\r\n");
			sb.append("\r\n");
			// 属性
			for(int i =0; i<fieldList.size(); i++){
				Map<String, String> map = (Map<String, String>) fieldList.get(i);
				sb.append("    /** "+map.get("field_explain")+" */").append("\r\n");
				if(map.get("pkid").equals("yes")){
					sb.append("    @Id").append("\r\n");
				}
				sb.append("    @DbField(\""+map.get("field")+"\")").append("\r\n");
				String field_type = SqlTypeTo.typeMap.get(map.get("field_type")); // 类型
				String field = StringUtil.toFieldName(map.get("field")); // 属性名
				sb.append("    private "+field_type+" "+field+";").append("\r\n");
				sb.append("\r\n");
			}
			
			// 注入get set 方法
			for(int i =0; i<fieldList.size(); i++){
				Map<String, String> map = (Map<String, String>) fieldList.get(i);
				String field_type = SqlTypeTo.typeMap.get(map.get("field_type"));// 类型
				String field = StringUtil.toFieldName(map.get("field"));// 属性名
				String _field =  StringUtil.toUpper(field); // 属性名首字母大写
				String get_field = "get"+_field;
				String set_field = "set"+_field;
				// get 
				sb.append("    public "+field_type+" "+get_field+"() {").append("\r\n");
				sb.append("        return this."+field+";").append("\r\n");
				sb.append("    }").append("\r\n");
				sb.append("\r\n");
				// set
				sb.append("    public void "+set_field+"("+field_type+" "+field+") {").append("\r\n");
				sb.append("        this."+field+" = "+field+";").append("\r\n");
				sb.append("    }").append("\r\n");
			}
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
	
	/**
	 * 说明 [检查文件夹是否存在,不存在就创建一个文件夹]
	 * L_晓天  @2018年9月3日
	 */
	private void checkFolder(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 说明 [检查文件是否存在]
	 * L_晓天  @2018年9月3日
	 */
	private boolean checkFile(String path){
		File file = new File(path);
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	} 
	
	/**
	 * 说明 [初始化importSet ]
	 * L_晓天  @2018年9月3日
	 */
	private void initImport(){
		importSet.clear();
		for(int i=0;i<fieldList.size();i++){
			Map<String, String> map = (Map<String, String>) fieldList.get(i);
			if(SqlTypeTo.typeMap.containsKey(map.get("field_type"))){
				String type = SqlTypeTo.typeMap.get(map.get("field_type"));
				String v = SqlTypeTo.importMap.get(type);
				if(!"".equals(v)){
					importSet.add(v);
				}
			}
		}
	}
}
