package com.sl.ue.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.sl.ue.entity.User;
import com.sl.ue.service.BaseService;
import com.sl.ue.util.HumpCrossUnderline;
import com.sl.ue.util.Page;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.Table;

public class BaseImpl<T>  implements BaseService<T>{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
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
					String table_filed = HumpCrossUnderline.humpToUnderline(field.getName());
					table_fileds.append(table_filed+" AS "+field.getName()+",");
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
			List<T> list = (List<T>) jdbcTemplate.queryForList(sql, params.toArray(), model);
			return list;
			
			
		}
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}


	public void baeDelete(T model) {
		// TODO Auto-generated method stub
		
	}

	public List<T> baseFindList(T model) {
		return baseFindList(model, null, null);
	}







}
