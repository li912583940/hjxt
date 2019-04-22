package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysConfVO;
import com.sl.ue.service.sys.SysConfService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysConf")
public class SysConfWeb extends Result{

    @Autowired
    private SysConfService sysConfSQL;

    @RequestMapping("/findConf")
    public String findConf(){
    	SysConfVO model = null;
    	List<SysConfVO> list = sysConfSQL.findList(new SysConfVO());
    	if(list.size()>0){
    		model = list.get(0);
    	}else{
    		model = new SysConfVO();
    	}
    	this.putJson(model);
    	return this.toResult();
    }
    
    @RequestMapping("/editConf")
    public String editConf(SysConfVO sysConf){
        return sysConfSQL.editConf(sysConf);
    }

    /**
     * 说明 [获取选中的会见类型数组]
     * @return
     * L_晓天  @2019年4月22日
     */
    @RequestMapping("/getCheckedHjType")
    public String getCheckedHjType(){
    	SysConfVO model = null;
    	List<SysConfVO> list = sysConfSQL.findList(new SysConfVO());
    	if(list.size()>0){
    		model = list.get(0);
    		this.putData(model.getHjTypes().split(","));
    	}else{
    		String[] str = {};
    		this.putData(str);
    	}
    	return this.toResult();
    }
}
