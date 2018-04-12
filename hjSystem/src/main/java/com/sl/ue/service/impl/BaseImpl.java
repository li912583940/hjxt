package com.sl.ue.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.BaseService;
import com.sl.ue.util.HumpCrossUnderline;
import com.sl.ue.util.Page;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.anno.DbField;
import com.sl.ue.util.anno.Id;
import com.sl.ue.util.anno.Table;

public abstract class BaseImpl<T> implements BaseService<T>{
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public BaseImpl(){
		Type superClass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superClass;
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	
	/** 实际操作的实体类对象 */
	private Class<T> clazz; 
	
	public List<T> baseFindList(T model, Integer pageSize, Integer pageNum) {
		Table table = clazz.getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			Field[] fields = clazz.getDeclaredFields();
			StringBuffer where_fields = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			try {
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					String table_filed = HumpCrossUnderline.humpToUnderline(field.getName());
					field.setAccessible(true);
					if(field.get(model) != null){
						params.add(field.get(model));
						where_fields.append(" and "+table_filed+"=?");
					}
				}
			} catch (Exception e) {
			}
			String limit = "";
			if(pageSize != null && pageNum != null){
				int startNum = (pageNum-1)*pageSize;
				limit =  " limit " + startNum + "," + pageSize;
			}
			String sql = "select * from " + tableName+"  where 1=1 " + where_fields.toString() + limit;
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
			List<T> list = (List<T>)jdbcTemplate.query(sql, params.toArray(), rowMapper);
			return list;
		}
		return null;
	}
	
	/**
	 * 说明 [弃用的方法]
	 * @author L_晓天    @2018年4月7日
	 */
	private List<T> baseFindList1(T model, Integer pageSize, Integer pageNum) {
		Table table = model.getClass().getAnnotation(Table.class);
		String tableName;
		if(table != null){
			tableName = table.value();
			Field[] fields = model.getClass().getDeclaredFields();
			StringBuffer table_fileds = new StringBuffer();
			StringBuffer where_fields = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			try {
				for(Field field : fields){
					if(field.getName().equals("serialVersionUID"))
						continue;
					//String table_filed = HumpCrossUnderline.humpToUnderline(field.getName());
					String table_filed = field.getAnnotation(DbField.class).value();
					table_fileds.append(table_filed+",");
					field.setAccessible(true);
					if(field.get(model) != null){
						params.add(field.get(model));
						where_fields.append(" and "+table_filed+"=?");
					}
				}
			} catch (Exception e) {
			}
			String limit = "";
			if(pageSize != null && pageNum != null){
				int startNum = (pageNum-1)*pageSize;
				limit =  " limit " + startNum + "," + pageSize;
			}
			String table_fileds_str = StringUtil.lastComma(table_fileds.toString());
			String sql = "select "+ table_fileds_str + " from " + tableName+"  where 1=1 " + where_fields.toString() + limit;
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
			List<T> list = (List<T>)jdbcTemplate.query(sql, params.toArray(), rowMapper);
			return list;
		}
		return null;
	}

	
	

	public T baseFindOne(Object... key) {
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
			Object[] obj = new Object[]{key};
			RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
			T t = jdbcTemplate.query(sql, obj, rowMapper).get(0);
			return t;
		}
		return null;
	}


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
			jdbcTemplate.update(sql.toString(), params.toArray());
			return model;
		}
		return null;
	}


	public T baeEdit(T model) {
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
			jdbcTemplate.update(sql.toString(),params.toArray());
			return model;
		}
		return null;
	}


	public void baeDelete(Object key) {
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
			sql.append("delete from "+tableName+" where "+id_filed+"=?");
			Object obj = new Object[]{key};
			jdbcTemplate.update(sql.toString(), obj);
		}
	}

	public List<T> baseFindList(T model) {
		return baseFindList(model, null, null);
	}







}
