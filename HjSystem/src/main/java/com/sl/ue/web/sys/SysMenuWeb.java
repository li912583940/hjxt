package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysMenuVO;
import com.sl.ue.service.sys.SysMenuService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuWeb extends Result{

    @Autowired
    private SysMenuService sysMenuSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysMenuVO model = new SysMenuVO();
        List<SysMenuVO> list = sysMenuSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysMenuVO model = sysMenuSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysMenuVO model){
        sysMenuSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysMenuVO model){
        sysMenuSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysMenuSQL.deleteKey(id);
        return this.toResult();
    }

}
