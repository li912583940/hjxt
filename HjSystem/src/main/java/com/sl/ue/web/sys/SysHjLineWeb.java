package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysHjLine;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysHjLine")
public class SysHjLineWeb extends Result{

    @Autowired
    private SysHjLineService SysHjLineSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysHjLine model = new SysHjLine();
        List<SysHjLine> list = SysHjLineSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjLine model = SysHjLineSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjLine model){
        SysHjLineSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjLine model){
        SysHjLineSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysHjLineSQL.baseDeleteKey(id);
        return this.toResult();
    }

}