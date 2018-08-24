package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysUserGroup;
import com.sl.ue.service.sys.SysUserGroupService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserGroup")
public class SysUserGroupWeb extends Result{

    @Autowired
    private SysUserGroupService sysUserGroupSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserGroup model = new SysUserGroup();
        List<SysUserGroup> list = sysUserGroupSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserGroup model = sysUserGroupSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserGroup model){
        sysUserGroupSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserGroup model){
        sysUserGroupSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserGroupSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
