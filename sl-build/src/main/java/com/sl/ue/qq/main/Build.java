package com.sl.ue.qq.main;

import com.sl.ue.qq.entity.Entity1;
import com.sl.ue.qq.service.BuildBean;
import com.sl.ue.qq.util.QueryDb;

public class Build {

	public static void main(String[] args){
		QueryDb queryDb = new QueryDb();
		queryDb.execute();
		Entity1 e1 = new Entity1();
		e1.main(null);
		BuildBean service = new BuildBean();
		service.main(null);
		com.sl.ue.qq.web.BuildBean web = new com.sl.ue.qq.web.BuildBean();
		web.main(null);
	}
}
