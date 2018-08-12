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
    private SysUserJqService SysUserJqSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserJq model = new SysUserJq();
        List<SysUserJq> list = SysUserJqSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserJq model = SysUserJqSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(SysUserJq model){
        SysUserJqSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(SysUserJq model){
        SysUserJqSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysUserJqSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
