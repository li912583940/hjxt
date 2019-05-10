package com.sl.ue.util.component;


import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sl.ue.entity.sys.vo.SysAccessTokenVO;
import com.sl.ue.service.other.HttpAbmsDeptService;
import com.sl.ue.service.sys.SysAccessTokenService;
import com.sl.ue.util.Config;
import com.sl.ue.util.business.RecFileManage;
import com.sl.ue.util.http.token.JqRoleManager;
import com.sl.ue.util.http.token.TokenManager;

/**
 * 说明 [定时器]
 * @作者 LXT @2018年9月26日
 */
@Component
public class QuartzTimer {

	@Autowired
	private HttpAbmsDeptService httpAbmsDeptSQL;
	/**
	 * 说明 [每天凌晨1点执行]
	 * @作者 LXT @2018年9月26日
	 */
	@Scheduled(cron = "0 0 1 * * ?")
    public void clearTimer(){
		TokenManager tokenManager = new TokenManager();
		tokenManager.clearToken();
		
		SysAccessTokenService sysAccessTokenSQL = (SysAccessTokenService) SpringTool.getBean("sysAccessTokenSQL");
		sysAccessTokenSQL.delete(new SysAccessTokenVO());
    }


	/**
	 * 说明 [每天凌晨1点30执行]
	 * @作者 LXT @2018年9月26日
	 */
	@Scheduled(cron = "0 30 1 * * ?")
    public void clearJqs(){
		JqRoleManager jqRoleManager = new JqRoleManager();
		jqRoleManager.remove();
    }
	
	/**
	 * 说明 [每隔半小时执行一次; 删除下载录音录像打包的zip文件]
	 * @作者 LXT @2019年5月9日
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
    public void clearRecZip(){
		RecFileManage recFileManage = new RecFileManage();
		Map<String, Long> map = recFileManage.getAll();
		long now = System.currentTimeMillis();
		for(String key : map.keySet()){
			long l = map.get(key);
			if((now-l)>(30*60*1000)){ //大于30分钟直接删除
				File file = new File(key);
				if(file.exists()){
					file.delete();
					
				}
				recFileManage.deleteRecFile(key);
			}
		}
    }
	
	/**
	 * 说明 [凌晨3点清空会见记录下载临时压缩包。 漏掉的]
	 * L_晓天  @2019年5月9日
	 */
	@Scheduled(cron = "* * 3 * * ?")
    public void clearRecZipAll(){
		RecFileManage recFileManage = new RecFileManage();
		recFileManage.remove();
		
		String fileDir = Config.getPropertiesValue("file.path")+"/recZip";
		File file = new File(fileDir);
		File[] files = file.listFiles();
		for(int i=0;i<files.length;i++){
			File f = files[i];
			if(f.exists()){
				f.delete();
			}
		}
    }
	
	/**
	 * 说明 [每天凌晨2点执行] [通过接口获取abms部门]
	 * @作者 LXT @2018年9月26日
	 */
	@Scheduled(cron = "0 0 2 * * ?")
    public void httpGetDept(){
		httpAbmsDeptSQL.syncAbmsDept();
    }
}
