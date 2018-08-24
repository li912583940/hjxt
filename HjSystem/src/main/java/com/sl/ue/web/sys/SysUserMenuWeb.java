package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysUserMenu;
import com.sl.ue.service.sys.SysUserMenuService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserMenu")
public class SysUserMenuWeb extends Result{

    @Autowired
    private SysUserMenuService sysUserMenuSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserMenu model = new SysUserMenu();
        List<SysUserMenu> list = sysUserMenuSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserMenu model = sysUserMenuSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserMenu model){
        sysUserMenuSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserMenu model){
        sysUserMenuSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserMenuSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
