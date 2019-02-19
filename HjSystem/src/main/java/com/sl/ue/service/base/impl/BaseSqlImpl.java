package com.sl.ue.service.base.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.sl.ue.service.base.BaseService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;
import com.sl.ue.util.http.WebContextUtil;
import com.sl.ue.util.http.token.JqRoleManager;

/**
 * 说明 [持久层 实现类 SQL Server]
 * @author lxt
 */
public abstract class BaseSqlImpl<T> implements BaseService<T>{

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/** 实际操作的实体类对象 */
	private Class<T> clazz; 
	/** 实体类vo */
	private Class<T> clazzVO;
	public BaseSqlImpl(){
		Type superClass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superClass;
		clazzVO = (Class<T>) type.getActualTypeArguments()[0];
		clazz = (Class<T>) clazzVO.getSuperclass();
	}
	
	
	
	@Override
	public List<T> findList(T model) {
		return findList(model, null, null);
	}
    
	/**
	 * 说明 [分页查询,不带记录总数(count) 自定义排序]
	 * @作者 LXT @2018年9月24日
	 */
	public List<T> findList(T model, Integer pageSize, Integer pageNum){
		return findList(model, pageSize, pageNum, "DESC");
	}
	
	@Override
	public List<T> findList(T model,  Integer pageSize, Integer pageNum, String sort) {
		Table table = clazz.getAnnotation(Table.class); // 自定义注解 表
		String tableName;
		if(table != null){
			tableName = table.value(); // 数据库表名
			Field[] fields = clazz.getDeclaredFields(); // 实体类的属性字段
			StringBuffer table_fileds = new StringBuffer(); // SQL字段
			StringBuffer where_fields = new StringBuffer(); // SQL的条件
			List<Object> params = new ArrayList<Object>(); // jdbc 需要的条件参数值
			String id_field = ""; // 主键
			String id_fields = ""; // 复合主键
			String d_id_field = ""; // 如果表没有主键就用这个
			String leftJoinField = ""; // 关联表字段
			String leftJoinTable = ""; // 关联表
			String leftJoinWhere = ""; // 关联表条件
			try {
				Field[] voFields = clazzVO.getDeclaredFields();
				for(Field field : voFields) {
					if(field.getName().equals("leftJoinField")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinField = field.get(model).toString();
						}
						continue;
					}
					if(field.getName().equals("leftJoinTable")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinTable = field.get(model).toString();
						}
						continue;
					}
					if(field.getName().equals("leftJoinWhere")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinWhere = field.get(model).toString();
						}
						continue;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				boolean jq_qx = false;
				String jq_name = "";
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					String table_field = field.getAnnotation(DbField.class).value();
					// 处理主键  区分单个主键 和 复合 主键
					if(field.isAnnotationPresent(Id.class)){
						id_field = table_field;
						if(StringUtils.isBlank(id_fields)){
							id_fields=table_field;
						}else{
							id_fields = id_fields+"+"+table_field;
						}
						
					}
					if(StringUtils.isBlank(d_id_field)){
						d_id_field = table_field;
					}
					// 拼接SQL字段
					table_fileds.append(table_field+",");
					// 处理SQL where条件
					field.setAccessible(true);
					if(field.get(model) != null  && !"".equals(field.get(model))){
						params.add(field.get(model));
						where_fields.append(" and a."+table_field+"=?");
					}
					
					/** 监区权限 开始 */
					String field_name = table_field.toLowerCase();
					if("jq".equals(field_name) || "jq_no".equals(field_name)){
						jq_qx= true;
						jq_name=field_name;
					}
					/** 监区权限 结束 */
				}
				
				/** 监区权限 开始 */
				if(jq_qx){
					String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
					JqRoleManager jqRoleManager = new JqRoleManager();
					String jqs = jqRoleManager.getJqs(token);
					if("admin".equals(jqs)){
						
					}else if(StringUtils.isBlank(jqs)){
						leftJoinWhere+=" AND 1<>1 ";
					}else if(StringUtils.isNotBlank(jqs)){
						leftJoinWhere+=" AND a."+jq_name+" in ("+jqs+") ";
					}
					
				}
				/** 监区权限 结束 */
			} catch (Exception e) {
			}
			// 如果是复合主键
			if(id_fields.length()>id_field.length()){
				// TODO 暂时不用
				id_fields = "("+id_fields+")";
			}
			// 最特殊情况  表中没有主键  --没有主键的表不能称之为表了
			if(StringUtils.isBlank(id_field)){
				//随便取一个字段
				id_field = d_id_field;
			}
			String table_fileds_str = StringUtil.lastComma(table_fileds.toString());
			//如果属性上没有字段注解，sql查询字段就设置为*
			if(StringUtils.isBlank(table_fileds_str)){
				table_fileds_str = "*";
			}
			String sql = "select a.*"+leftJoinField+" from " + tableName+" a "+leftJoinTable+" where 1=1 " + where_fields.toString()+" "+leftJoinWhere+" ORDER BY a."+id_field+" "+sort;
			if(pageSize != null && pageNum != null){
				int startNum = (pageNum-1)*pageSize;
				int endNum = pageNum*pageSize;
				sql = "select * from (select ROW_NUMBER() OVER(ORDER BY a."+id_field+" "+sort+") AS rowid,a.* "+leftJoinField+" from "+tableName+" a "+leftJoinTable+" where 1=1 "+where_fields.toString()+" "+leftJoinWhere+" ) t"
						+" where t.rowid>"+startNum+" AND t.rowid<="+endNum;
			}
			System.out.println("执行查询list语句: [ "+sql+" ]");
			System.out.println("参数："+params);
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazzVO);
			List<T> list = (List<T>)jdbcTemplate.query(sql, params.toArray(), rowMapper);
			return list;
		}
		return null;
	}

	/**
	 * 说明 [分页查询,带记录总数(count)]
	 * @作者 LXT @2018年9月24日
	 */
	public Map<String, Object> findPojo(T model){
		return findPojo(model, null, null);
	}
	
	public Map<String, Object> findPojo(T model, Integer pageSize, Integer pageNum){
		return findPojo(model, pageSize, pageNum, "DESC");
	}
	/**
	 * 说明 [分页查询,带记录总数(count)]
	 * @作者 LXT @2018年9月24日
	 */
	public Map<String, Object> findPojo(T model, Integer pageSize, Integer pageNum, String sort){
		Table table = clazz.getAnnotation(Table.class); // 自定义注解 表
		String tableName;
		if(table != null){
			Map<String, Object> resultMap = new HashMap<>(); // 封装结果集
			
			tableName = table.value(); // 数据库表名
			Field[] fields = clazz.getDeclaredFields(); // 实体类的属性字段
			StringBuffer table_fileds = new StringBuffer(); // SQL字段
			StringBuffer where_fields = new StringBuffer(); // SQL的条件
			List<Object> params = new ArrayList<Object>(); // jdbc 需要的条件参数值
			String id_field = ""; // 主键
			String id_fields = ""; // 复合主键
			String d_id_field = ""; // 如果表没有主键就用这个
			String leftJoinField = ""; // 关联表字段
			String leftJoinTable = ""; // 关联表
			String leftJoinWhere = ""; // 关联表条件
			try {
				Field[] voFields = clazzVO.getDeclaredFields();
				for(Field field : voFields) {
					if(field.getName().equals("leftJoinField")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinField = field.get(model).toString();
						}
						continue;
					}
					if(field.getName().equals("leftJoinTable")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinTable = field.get(model).toString();
						}
						continue;
					}
					if(field.getName().equals("leftJoinWhere")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinWhere = field.get(model).toString();
						}
						continue;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			try {
				boolean jq_qx = false;
				String jq_name = "";
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					String table_field = field.getAnnotation(DbField.class).value();
					// 处理主键  区分单个主键 和 复合 主键
					if(field.isAnnotationPresent(Id.class)){
						id_field = table_field;
						if(StringUtils.isBlank(id_fields)){
							id_fields=table_field;
						}else{
							id_fields = id_fields+"+"+table_field;
						}
						
					}
					if(StringUtils.isBlank(d_id_field)){
						d_id_field = table_field;
					}
					// 拼接SQL字段
					table_fileds.append(table_field+",");
					// 处理SQL where条件
					field.setAccessible(true);
					if(field.get(model) != null && !"".equals(field.get(model))){
						params.add(field.get(model));
						where_fields.append(" and a."+table_field+"=?");
					}
					
					/** 监区权限 开始 */
					String field_name = table_field.toLowerCase();
					if("jq".equals(field_name) || "jq_no".equals(field_name)){
						jq_qx= true;
						jq_name=field_name;
					}
					/** 监区权限 结束 */
				}
				
				/** 监区权限 开始 */
				if(jq_qx){
					String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
					JqRoleManager jqRoleManager = new JqRoleManager();
					String jqs = jqRoleManager.getJqs(token);
					if("admin".equals(jqs)){
					}else if(StringUtils.isBlank(jqs)){
						leftJoinWhere+=" AND 1<>1 ";
					}else if(StringUtils.isNotBlank(jqs)){
						leftJoinWhere+=" AND a."+jq_name+" in ("+jqs+") ";
					}
				}
				/** 监区权限 结束 */
				
			} catch (Exception e) {
			}
			// 如果是复合主键
			if(id_fields.length()>id_field.length()){
				// TODO 暂时不用
				id_fields = "("+id_fields+")";
			}
			// 最特殊情况  表中没有主键  --没有主键的表不能称之为表了
			if(StringUtils.isBlank(id_field)){
				//随便取一个字段
				id_field = d_id_field;
			}
			String table_fileds_str = StringUtil.lastComma(table_fileds.toString());
			//如果属性上没有字段注解，sql查询字段就设置为*
			if(StringUtils.isBlank(table_fileds_str)){
				table_fileds_str = "*";
			}
			
			String sql = "select a.*"+leftJoinField+" from " + tableName+" a "+leftJoinTable+" where 1=1 " + where_fields.toString()+" "+leftJoinWhere+" ORDER BY a."+id_field+" "+sort;
			if(pageSize != null && pageNum != null){
				int startNum = (pageNum-1)*pageSize;
				int endNum = pageNum*pageSize;
				sql = "select * from (select ROW_NUMBER() OVER(ORDER BY a."+id_field+" "+sort+") AS rowid,a.* "+leftJoinField+" from "+tableName+" a "+leftJoinTable+" where 1=1 "+where_fields.toString()+" "+leftJoinWhere+" ) t"
						+" where t.rowid>"+startNum+" AND t.rowid<="+endNum;
			}
			System.out.println("执行查询pojo语句: [ "+sql+" ]");
			System.out.println("参数："+params);
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazzVO);
			List<T> list = (List<T>)jdbcTemplate.query(sql, params.toArray(), rowMapper);
			resultMap.put("list", list);
		
			
			String countSql = "select ISNULL(count(*),0) AS count from " + tableName+" a "+leftJoinTable+"  where 1=1 " + where_fields.toString()+" "+leftJoinWhere;
			System.out.println("执行查询count语句: [ "+countSql+" ]");
			System.out.println("参数："+params);
			SqlRowSet rowSet = jdbcTemplate.queryForRowSet(countSql, params.toArray());
			Integer count = 0 ;
			while(rowSet.next()) {
				count = rowSet.getInt("count");
			}
			resultMap.put("count", count);
			return resultMap;
		}
		return null;
	}
	
	/**
	 * 说明 [查询记录数]
	 * @作者 LXT @2018年9月24日
	 */
	public Integer count(T model) {

		Table table = clazz.getAnnotation(Table.class); // 自定义注解 表
		String tableName;
		if(table != null){
			tableName = table.value(); // 数据库表名
			Field[] fields = clazz.getDeclaredFields(); // 实体类的属性字段
			StringBuffer where_fields = new StringBuffer(); // SQL的条件
			List<Object> params = new ArrayList<Object>(); // jdbc 需要的条件参数值
			
			String leftJoinTable = ""; // 关联表
			String leftJoinWhere = ""; // 关联表条件
			
			try {
				Field[] voFields = clazzVO.getDeclaredFields();
				for(Field field : voFields) {
					if(field.getName().equals("leftJoinTable")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinTable = field.get(model).toString();
						}
						continue;
					}
					if(field.getName().equals("leftJoinWhere")) {
						field.setAccessible(true);
						if(field.get(model) != null) {
							leftJoinWhere = field.get(model).toString();
						}
						continue;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			try {
				boolean jq_qx = false;
				String jq_name = "";
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					String table_field = field.getAnnotation(DbField.class).value();
					// 处理SQL where条件
					field.setAccessible(true);
					if(field.get(model) != null){
						params.add(field.get(model));
						where_fields.append(" and a."+table_field+"=?");
					}
					
					/** 监区权限 开始 */
					String field_name = table_field.toLowerCase();
					if("jq".equals(field_name) || "jq_no".equals(field_name)){
						jq_qx= true;
						jq_name=field_name;
					}
					/** 监区权限 结束 */
					
				}
				
				/** 监区权限 开始 */
				if(jq_qx){
					String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
					JqRoleManager jqRoleManager = new JqRoleManager();
					String jqs = jqRoleManager.getJqs(token);
					if("admin".equals(jqs)){
					}else if(StringUtils.isBlank(jqs)){
						leftJoinWhere+=" AND 1<>1 ";
					}else if(StringUtils.isNotBlank(jqs)){
						leftJoinWhere+=" AND a."+jq_name+" in ("+jqs+") ";
					}
				}
				/** 监区权限 结束 */
				
			} catch (Exception e) {
			}
			String countSql = "select ISNULL(count(*),0) AS count from " + tableName+" a "+leftJoinTable+"  where 1=1 " + where_fields.toString()+" "+leftJoinWhere;
			System.out.println("执行查询count语句: [ "+countSql+" ]");
			System.out.println("参数："+params);
			SqlRowSet rowSet = jdbcTemplate.queryForRowSet(countSql, params.toArray());
			Integer count = 0 ;
			while(rowSet.next()) {
				count = rowSet.getInt("count");
			}
			return count;
		}
		return 0;
	
	}
	
	@Override
	public T findOne(Object key) {
		Table table =clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			Field[] fields = clazz.getDeclaredFields();
			String id_field = null;
			for(Field field : fields){
				if(field.isAnnotationPresent(Id.class)){
					id_field = field.getAnnotation(DbField.class).value();
				}
			}
			String sql = "select * from "+tableName+" where 1=1 and "+id_field+"=?";
			List<Object> params = new ArrayList<>();
			params.add(key);
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazzVO);
			System.out.println("执行查询单条记录语句：[ "+sql+" ]");
			System.out.println("参数："+params);
			T t = jdbcTemplate.queryForObject(sql, params.toArray(), rowMapper);
			return t;
		}
		return null;
	}

	@Override
	public T add(T model) {
		Table table = clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			StringBuffer sql = new StringBuffer();
			StringBuffer table_field = new StringBuffer();
			StringBuffer table_value = new StringBuffer();
			sql.append("insert into "+tableName).append("(");
			List<Object> params = new ArrayList<Object>();
			List<Map<String, Object>> psList = new ArrayList<>(); // 实现 自增主键
			Field[] fields = clazz.getDeclaredFields();
			boolean isInc = false;
			Field idField = null;
			try{
				for(Field field: fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					Id id = field.getAnnotation(Id.class);
					if(id != null){
						if(id.inc() == true){
							isInc = true;
							idField = field;
							continue;
						}
						
					}
					String table_filed = field.getAnnotation(DbField.class).value();
					
					field.setAccessible(true);
					if(field.get(model) != null){
						table_field.append(table_filed+",");
						table_value.append("?,");
						params.add(field.get(model));
						
						Map<String, Object> map = new HashMap<>();
						map.put("class", field.getType());
						map.put("value", field.get(model));
						psList.add(map);
					}
				}
			}catch(Exception e){
			}
			sql.append(StringUtil.lastComma(table_field.toString()))
				.append(")")
				.append(" values(")
				.append(StringUtil.lastComma(table_value.toString()))
				.append(")");
			System.out.println("执行新增语句：[ "+sql+" ]");
			System.out.println("参数："+params);
			if(isInc == true){
				KeyHolder keyHolder = new GeneratedKeyHolder();
				Number id = null;
				final String sqlStr = sql.toString();
				jdbcTemplate.update(new PreparedStatementCreator(){  
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						 PreparedStatement ps = con.prepareStatement(sqlStr,PreparedStatement.RETURN_GENERATED_KEYS);
						 addPs(ps,psList);
				         return ps;  
					}  
			    }, keyHolder);
				id =  keyHolder.getKey();
				if(id != null){
					idField.setAccessible(true);
					try {
						if(idField.getType()==Long.class){
							idField.set(model, id.longValue());
						}else if(idField.getType()==Integer.class){
							idField.set(model, id.intValue());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					return model;
				}
					
			}else{
				jdbcTemplate.update(sql.toString(), params.toArray());
				return model;
			}
			
			//jdbcTemplate.update(sql.toString(), params.toArray());
			return model;
		}
		return null;
	}

	@Override
	public T edit(T model) {
		Table table = clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			StringBuffer sql = new StringBuffer();
			StringBuffer up_field = new StringBuffer();
			sql.append("update "+tableName+" set ");
			List<Object> params = new ArrayList<Object>();
			Field[] fields = clazz.getDeclaredFields();
			Field idField = null;
			String id_field="";
			try {
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					if(field.isAnnotationPresent(Id.class)){
						idField = field;
						id_field = field.getAnnotation(DbField.class).value();
						continue;
					}
					String table_filed = field.getAnnotation(DbField.class).value();
					field.setAccessible(true);
					if(field.get(model) != null){
						params.add(field.get(model));
						up_field.append(table_filed+"=?,");
					}
				}
				idField.setAccessible(true);
				sql.append(StringUtil.lastComma(up_field.toString()))
					.append(" where "+id_field+"=?");
				params.add(idField.get(model));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("执行update语句：["+sql+" ]");
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(),params.toArray());
			return model;
		}
		return null;
	}

	@Override
	public void deleteKey(Object key) {
		Table table = clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			StringBuffer sql = new StringBuffer();
			Field[] fields = clazz.getDeclaredFields();
			String id_filed = null;
			for(Field field : fields){
				if(field.isAnnotationPresent(Id.class)){
					id_filed = field.getAnnotation(DbField.class).value();
				}
			}
			List<Object> params = new ArrayList<>();
			params.add(key);
			sql.append("delete from "+tableName+" where "+id_filed+"=?");
			System.out.println("执行删除语句：[ "+sql+" ]");
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
	}

	public void delete(T model){
		Table table = clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			StringBuffer sql = new StringBuffer();
			StringBuffer where_field = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields){
				if(field.getName().equals("serialVersionUID"))
					continue;
				String table_filed = field.getAnnotation(DbField.class).value();
				field.setAccessible(true);
				try {
					if(field.get(model) != null){
						params.add(field.get(model));
						where_field.append(" AND "+table_filed+"=?,");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			sql.append("delete from "+tableName+" where 1=1 "+StringUtil.lastComma(where_field.toString()));
			System.out.println("执行删除语句：[ "+ sql+" ]");
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
	
	}
	
	
	private void addPs(PreparedStatement ps, List<Map<String, Object>> psList) throws SQLException{
		int i=1;
		for(Map<String, Object> map : psList){
			Class typeCla = (Class)map.get("class");
			if(typeCla == Integer.class){
				ps.setInt(i, (Integer) map.get("value"));
			}else if(typeCla == String.class){
				ps.setString(i, map.get("value").toString());
			}else if(typeCla == Byte.class){
				ps.setBytes(i, (byte[]) map.get("value"));
			}else if(typeCla == BigDecimal.class){
				ps.setBigDecimal(i, (BigDecimal) map.get("value"));
			}else if(typeCla == Date.class){
				ps.setDate(i, (Date) map.get("value"));
			}else if(typeCla == java.util.Date.class){
				java.util.Date date = (java.util.Date)map.get("value");
				long l = date.getTime();
				ps.setTimestamp(i, new Timestamp(l));
			}else if(typeCla == Long.class){
				ps.setLong(i, (long) map.get("value"));
			}else{
				
			}
			i++;
		}
	}
}
