package com.sl.ue.util.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sl.ue.util.http.token.JqRoleManager;
import com.sl.ue.util.http.token.TokenManager;

/**
 * 说明 [定时器]
 * @作者 LXT @2018年9月26日
 */
@Component
public class QuartzTimer {

	/**
	 * 说明 [每天凌晨1点执行]
	 * @作者 LXT @2018年9月26日
	 */
	@Scheduled(cron = "0 0 1 * * ?")
    public void clearTimer(){
		TokenManager tokenManager = new TokenManager();
		tokenManager.clearToken();
    }


	/**
	 * 说明 [每天凌晨1点执行]
	 * @作者 LXT @2018年9月26日
	 */
	@Scheduled(cron = "0 30 1 * * ?")
    public void clearJqs(){
		JqRoleManager jqRoleManager = new JqRoleManager();
		jqRoleManager.remove();
    }
}
