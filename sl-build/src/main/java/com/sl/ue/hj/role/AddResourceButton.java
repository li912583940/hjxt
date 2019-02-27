package com.sl.ue.hj.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sl.ue.hj.util.Db;

public class AddResourceButton {

	public static void main(String[] args) {
		Connection conn = Db.getConnection();
		Statement stmt = null;
		PreparedStatement ps = null;
		try {
			stmt = conn.createStatement();
			String querySQL = "select * from sys_resource where type='menu' and id not in "
					+ "(select s2.parent_id from sys_resource s2 where type='menu' and s2.parent_id is not null)";
			ResultSet res = stmt.executeQuery(querySQL);
			while(res.next()){
				Integer menuId = res.getInt("id");
				// 批量增删该查方法
				for(int i=1;i<5;i++){
					String addButtonSQL="";
					if(i==1){
						addButtonSQL = "insert into sys_resource(name,description,type,parent_id,path_url,sort) "
								+ "values('查询','"+res.getString("name")+"','button',"+menuId+",'queryPermission',"+i+")";
					}else if(i==2){
						addButtonSQL = "insert into sys_resource(name,description,type,parent_id,path_url,sort) "
								+ "values('添加','"+res.getString("name")+"','button',"+menuId+",'addPermission',"+i+")";
					}else if(i==3){
						addButtonSQL = "insert into sys_resource(name,description,type,parent_id,path_url,sort) "
								+ "values('修改','"+res.getString("name")+"','button',"+menuId+",'editPermission',"+i+")";
					}else if(i==4){
						addButtonSQL = "insert into sys_resource(name,description,type,parent_id,path_url,sort) "
								+ "values('删除','"+res.getString("name")+"','button',"+menuId+",'deletePermission',"+i+")";
					}
					ps = conn.prepareStatement(addButtonSQL);
					ps.execute();
				}
			}
		} catch (Exception e) {
		}
		finally {
			try {
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
				if(ps!=null)ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	private void add(Integer parentId, String description){
		
	}
}
