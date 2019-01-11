package com.sl.ue.hj.util;

import java.util.*;

/**
 * 说明 [sqlserver 数据库字段类型对应实体字段类型]
 * L_晓天  @2018年9月3日
 */
public class SqlTypeTo {

	static final Map<String, String> typeMap = new HashMap<String, String>();
	
	// 实体类 类型
	static{
		typeMap.put("int", "Integer");
		typeMap.put("varchar", "String");
		typeMap.put("char", "String");
		typeMap.put("nchar", "String");
		typeMap.put("nvarchar", "String");
		typeMap.put("text", "String");
		typeMap.put("ntext", "String");
		typeMap.put("tinyint", "Integer");
		typeMap.put("smallint", "Integer");
		typeMap.put("bit", "Boolean");
		typeMap.put("bigint", "Long");
		typeMap.put("float", "Double");
		typeMap.put("decimal", "BigDecimal");
		typeMap.put("money", "BigDecimal");
		typeMap.put("smallmoney", "BigDecimal");
		typeMap.put("numeric", "BigDecimal");
		typeMap.put("real", "Float");
		typeMap.put("uniqueidentifier", "String");
		typeMap.put("smalldatetime", "Date");
		typeMap.put("datetime", "Date");
		typeMap.put("timestamp", "byte[]");
		typeMap.put("binary", "byte[]");
		typeMap.put("varbinary", "byte[]");
		typeMap.put("image", "byte[]");
		typeMap.put("sql_variant", "String");
	}
	
	static final Map<String, String> importMap = new HashMap<String, String>();
	
	static{
		//基本类型无须导入
		importMap.put("Integer", ""); 
		importMap.put("String", "");
		importMap.put("byte[]", "");
		importMap.put("Long", "");
		
		importMap.put("BigDecimal", "java.math.BigDecimal");
		importMap.put("Date", "java.util.Date");
		
	}
}
