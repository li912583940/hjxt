package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysUserJq;
import com.sl.ue.service.sys.SysUserJqService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserJq")
public class SysUserJqWeb extends Result{

    @Autowired
    private SysUserJqService sysUserJqSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserJq model = new SysUserJq();
        List<SysUserJq> list = sysUserJqSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserJq model = sysUserJqSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserJq model){
        sysUserJqSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserJq model){
        sysUserJqSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserJqSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
