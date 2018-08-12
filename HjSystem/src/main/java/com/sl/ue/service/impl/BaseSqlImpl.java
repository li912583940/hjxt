package com.sl.ue.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sl.ue.service.BaseService;
import com.sl.ue.util.HumpCrossUnderline;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

/**
 * 说明 [持久层 实现类 SQL Server]
 * @author lxt
 */
public abstract class BaseSqlImpl<T> implements BaseService<T>{

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/** 实际操作的实体类对象 */
	private Class<T> clazz; 
	
	public BaseSqlImpl(){
		Type superClass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superClass;
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	
	
	
	@Override
	public List<T> baseFindList(T model) {
		return baseFindList(model, null, null);
	}

	@Override
	public List<T> baseFindList(T model,  Integer pageNum, Integer pageSize) {
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
			try {
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					String table_filed = field.getAnnotation(DbField.class).value();
					// 处理主键  区分单个主键 和 复合 主键
					if(field.isAnnotationPresent(Id.class)){
						id_field = table_filed;
						if(StringUtils.isBlank(id_fields)){
							id_fields=table_filed;
						}else{
							id_fields = id_fields+"+"+table_filed;
						}
						
					}
					if(StringUtils.isBlank(d_id_field)){
						d_id_field = table_filed;
					}
					// 拼接SQL字段
					table_fileds.append(table_filed+",");
					// 处理SQL where条件
					field.setAccessible(true);
					if(field.get(model) != null){
						params.add(field.get(model));
						where_fields.append(" and "+table_filed+"=?");
					}
				}
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
			String sql = "select "+ table_fileds_str + " from " + tableName+"  where 1=1 " + where_fields.toString();
			if(pageSize != null && pageNum != null){
				int startNum = (pageNum-1)*pageSize;
				int endNum = pageNum*pageSize;
				sql = "select * from (select ROW_NUMBER() OVER(ORDER BY "+id_field+" DESC) AS rowid,* from "+tableName+" ) t"
						+" where t.rowid>"+startNum+" AND t.rowid<="+endNum;
			}
			System.out.println("执行查询list语句: "+sql);
			System.out.println("参数："+params);
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
			List<T> list = (List<T>)jdbcTemplate.query(sql, params.toArray(), rowMapper);
			return list;
		}
		return null;
	}

	@Override
	public T baseFindOne(Object key) {
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
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
			System.out.println("执行查询key语句："+sql);
			System.out.println("参数："+params);
			//T t = jdbcTemplate.query(sql, obj, rowMapper).get(0);
			T t = jdbcTemplate.queryForObject(sql, params.toArray(), rowMapper);
			return t;
		}
		return null;
	}

	@Override
	public T baseAdd(T model) {
		Table table = clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			StringBuffer sql = new StringBuffer();
			StringBuffer table_field = new StringBuffer();
			StringBuffer table_value = new StringBuffer();
			sql.append("insert into "+tableName).append("(");
			List<Object> params = new ArrayList<Object>();
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
						}
						continue;
					}
					String table_filed = field.getAnnotation(DbField.class).value();
					field.setAccessible(true);
					if(field.get(model) != null){
						table_field.append(table_filed+",");
						table_value.append("?,");
						params.add(field.get(model));
					}
				}
			}catch(Exception e){
			}
			sql.append(StringUtil.lastComma(table_field.toString()))
				.append(")")
				.append(" values(")
				.append(StringUtil.lastComma(table_value.toString()))
				.append(")");
			/*if(isInc == true){
				KeyHolder keyHolder = new GeneratedKeyHolder();
				int id = 0;
				final String sqlStr = sql.toString();
				jdbcTemplate.update(new PreparedStatementCreator(){  
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						 PreparedStatement ps = con.prepareStatement(sqlStr,PreparedStatement.RETURN_GENERATED_KEYS); 
				         return ps;  
					}  
			    }, keyHolder);
				id = keyHolder.getKey().intValue();
				if(id != 0){
					idField.setAccessible(true);
					try {
						idField.set(model, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return model;
				}
					
			}else{
				jdbcTemplate.update(sql.toString());
				return model;
			}*/
			System.out.println("执行新增语句："+sql);
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(), params.toArray());
			return model;
		}
		return null;
	}

	@Override
	public T baseEdit(T model) {
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
			try {
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					if(field.isAnnotationPresent(Id.class)){
						idField = field;
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
					.append(" where "+HumpCrossUnderline.humpToUnderline(idField.getName())+"=?");
				params.add(idField.get(model));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("执行update语句："+sql);
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(),params.toArray());
			return model;
		}
		return null;
	}

	@Override
	public void baseDeleteKey(Object key) {
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
			System.out.println("执行删除语句："+sql);
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
	}

	public void baseDelete(T model){
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
						where_field.append(table_filed+"=?,");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			sql.append("delete from "+tableName+" where "+StringUtil.lastComma(where_field.toString()));
			System.out.println("执行删除语句： "+ sql);
			System.out.println("参数："+params);
			jdbcTemplate.update(sql.toString(), params.toArray());
		}
	
	}
}
