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
//		enMap.put("sys_role", "SysRole");
//		enMap.put("sys_resource", "SysResource");
//		enMap.put("sys_user_role", "SysUserRole");
//		enMap.put("sys_role_resource", "SysRoleResource");
		
		//enMap.put("JL_QS_GX", "JlQsGx");
		
		//enMap.put("sys_role_jq", "SysRoleJq");
		//enMap.put("JL_HJ_SP_FR", "JlHjSpFr");
		//enMap.put("SYS_SERVER", "SysServer");
//		enMap.put("jl_hj_sp", "JlHjSp");
//		enMap.put("jl_hj_sp_details", "JlHjSpDetails");
//		enMap.put("jl_hj_sp_set", "JlHjSpSet");
//		enMap.put("jl_hj_sp_user", "JlHjSpUser");
//		enMap.put("jl_qs_sp", "JlQsSp");
		//enMap.put("jl_hj_jq_holiday", "JlHjJqHoliday");
		//enMap.put("jl_hj_holiday", "JlHjHoliday");
		enMap.put("sys_notice_conf", "SysNoticeConf");
	}
	
	

}
