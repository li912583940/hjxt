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
    private SysHjServerService SysHjServerSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysHjServer model = new SysHjServer();
        List<SysHjServer> list = SysHjServerSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjServer model = SysHjServerSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(SysHjServer model){
        SysHjServerSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(SysHjServer model){
        SysHjServerSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysHjServerSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
