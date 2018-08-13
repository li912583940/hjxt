package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysHjVideo;
import com.sl.ue.service.sys.SysHjVideoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysHjVideo")
public class SysHjVideoWeb extends Result{

    @Autowired
    private SysHjVideoService SysHjVideoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysHjVideo model = new SysHjVideo();
        List<SysHjVideo> list = SysHjVideoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjVideo model = SysHjVideoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjVideo model){
        SysHjVideoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjVideo model){
        SysHjVideoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysHjVideoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
