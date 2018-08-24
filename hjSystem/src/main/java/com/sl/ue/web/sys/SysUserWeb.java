package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUser")
public class SysUserWeb extends Result{

    @Autowired
    private SysUserService sysUserSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUser model = new SysUser();
        List<SysUser> list = sysUserSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUser model = sysUserSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUser model){
        sysUserSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUser model){
        sysUserSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
