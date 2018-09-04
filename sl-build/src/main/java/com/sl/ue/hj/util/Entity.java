package com.sl.ue.hj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明 [需要根据表生成的实体类,对于不想生成的表就不加进来]
 * L_晓天  @2018年9月2日
 */
public class Entity {

	static final String url = "G:\\声联项目\\hjxt\\HjSystem";
	// key:表名, value:类名
	static final Map<String, String> enMap = new HashMap<String, String>();
	static{
		enMap.put("sys_role", "SysRole");
		enMap.put("sys_resource", "SysResource");
		enMap.put("sys_user_role", "SysUserRole");
		enMap.put("sys_role_resource", "SysRoleResource");
	}
	
	

}