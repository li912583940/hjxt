package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysHjServer;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysHjServer")
public class SysHjServerWeb extends Result{

    @Autowired
    private SysHjServerService sysHjServerSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysHjServer model = new SysHjServer();
        List<SysHjServer> list = sysHjServerSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjServer model = sysHjServerSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjServer model){
        sysHjServerSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjServer model){
        sysHjServerSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysHjServerSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
