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
    private SysUserMenuService SysUserMenuSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserMenu model = new SysUserMenu();
        List<SysUserMenu> list = SysUserMenuSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserMenu model = SysUserMenuSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(SysUserMenu model){
        SysUserMenuSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(SysUserMenu model){
        SysUserMenuSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysUserMenuSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
