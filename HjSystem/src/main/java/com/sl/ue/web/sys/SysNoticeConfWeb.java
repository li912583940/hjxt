package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.service.sys.SysNoticeConfService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysNoticeConf")
public class SysNoticeConfWeb extends Result{

    @Autowired
    private SysNoticeConfService sysNoticeConfSQL;

    @RequestMapping("/findNotice")
    public String findNotice(){
    	SysNoticeConfVO model = null;
    	List<SysNoticeConfVO> list = sysNoticeConfSQL.findList(new SysNoticeConfVO());
    	if(list.size()>0){
    		model = list.get(0);
    	}else{
    		model = new SysNoticeConfVO();
    	}
    	this.putJson(model);
    	return this.toResult();
    }
    
    @RequestMapping("/noticeChange")
    public String noticeChange(Integer noticeValue){
        return sysNoticeConfSQL.noticeChange(noticeValue);
    }

}
