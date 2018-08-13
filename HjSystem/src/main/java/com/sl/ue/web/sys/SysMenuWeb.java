package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysMenu;
import com.sl.ue.service.sys.SysMenuService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuWeb extends Result{

    @Autowired
    private SysMenuService SysMenuSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysMenu model = new SysMenu();
        List<SysMenu> list = SysMenuSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysMenu model = SysMenuSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysMenu model){
        SysMenuSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysMenu model){
        SysMenuSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysMenuSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
