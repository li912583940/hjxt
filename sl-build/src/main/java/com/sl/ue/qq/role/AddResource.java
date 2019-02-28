package com.sl.ue.qq.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sl.ue.qq.util.Db;

public class AddResource {

	public static void main(String[] args){
		Connection conn = Db.getConnection();
		PreparedStatement ps = null;
		List<Map<String, String>> list = getRolePath();
		try {
			for(Map<String, String> map : list){
				String path = map.get("path");
				String name = map.get("name");
				
				String sql = "insert into sys_resource"
						+"(name,description,type,path_url)"
						+"VALUES(?,?,'menu',?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, name);
				ps.setString(3, path);
				ps.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//6、关闭资源
			try {
				if(ps!=null)ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static List<Map<String, String>> getRolePath(){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("path", "criminal");
		map.put("name", "服刑人员");
		list.add(map);
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("path", "relatives");
		map1.put("name", "亲属");
		list.add(map1);
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("path", "meetRegister");
		map2.put("name", "会见登记");
		list.add(map2);
		
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("path", "meetSign");
		map3.put("name", "座位分配");
		list.add(map3);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("path", "meetNotice");
		map4.put("name", "会见通知");
		list.add(map4);
		
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("path", "meetMonitor");
		map5.put("name", "会见监控");
		list.add(map5);
		
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("path", "meetSp");
		map6.put("name", "会见审批");
		list.add(map6);
		
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("path", "yjMessage");
		map7.put("name", "警察信息");
		list.add(map7);
		
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("path", "meetRecord");
		map8.put("name", "会见记录");
		list.add(map8);
		
		Map<String, String> map9 = new HashMap<String, String>();
		map9.put("path", "meetReport");
		map9.put("name", "会见报表");
		list.add(map9);
		
		Map<String, String> map10 = new HashMap<String, String>();
		map10.put("path", "operationLog");
		map10.put("name", "操作日志");
		list.add(map10);
		
		Map<String, String> map11 = new HashMap<String, String>();
		map11.put("path", "registerLog");
		map11.put("name", "登记记录");
		list.add(map11);
		
		Map<String, String> map12 = new HashMap<String, String>();
		map12.put("path", "entranceGuard");
		map12.put("name", "门禁记录");
		list.add(map12);
		
		Map<String, String> map13 = new HashMap<String, String>();
		map13.put("path", "sysUser");
		map13.put("name", "系统用户管理");
		list.add(map13);
		
		Map<String, String> map14 = new HashMap<String, String>();
		map14.put("path", "sysRoles");
		map14.put("name", "系统权限配置");
		list.add(map14);
		
		Map<String, String> map15 = new HashMap<String, String>();
		map15.put("path", "criminalLevel");
		map15.put("name", "服刑人员级别");
		list.add(map15);
		
		Map<String, String> map16 = new HashMap<String, String>();
		map16.put("path", "jqSet");
		map16.put("name", "监区设置");
		list.add(map16);
		
		Map<String, String> map17 = new HashMap<String, String>();
		map17.put("path", "lineSet");
		map17.put("name", "线路设置");
		list.add(map17);
		
		Map<String, String> map18 = new HashMap<String, String>();
		map18.put("path", "sysParam");
		map18.put("name", "系统参数");
		list.add(map18);
		return list;
	}
}
