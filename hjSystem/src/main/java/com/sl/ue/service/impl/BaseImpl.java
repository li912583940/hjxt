package com.sl.ue.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.sl.ue.entity.SysUser;
import com.sl.ue.service.BaseService;
import com.sl.ue.util.HumpCrossUnderline;
import com.sl.ue.util.Page;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.Table;

public abstract class BaseImpl<T> implements BaseService<T>{
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public BaseImpl(){
		Type superClass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superClass;
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		System.out.println("Dao实现类是：" + clazz.getName());
	}
	
	
	/** 实际操作的实体类对象 */
	private Class<T> clazz; 
	
	public List<T> baseFindList(T model, Integer pageSize, Integer pageNum) {
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
					String table_filed = HumpCrossUnderline.humpToUnderline(field.getName());
					//table_fileds.append(table_filed+" AS "+field.getName()+",");
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
		// TODO Auto-generated method stub
		return null;
	}


	public T baseAdd(T model) {
		// TODO Auto-generated method stub
		return null;
	}


	public T baeEdit(T model) {
		return null;
	}


	public void baeDelete(T model) {
		// TODO Auto-generated method stub
		
	}

	public List<T> baseFindList(T model) {
		return baseFindList(model, null, null);
	}







}
