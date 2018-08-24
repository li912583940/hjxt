package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysUserMenuVO;
import com.sl.ue.service.sys.SysUserMenuService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserMenu")
public class SysUserMenuWeb extends Result{

    @Autowired
    private SysUserMenuService sysUserMenuSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserMenuVO model = new SysUserMenuVO();
        List<SysUserMenuVO> list = sysUserMenuSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserMenuVO model = sysUserMenuSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserMenuVO model){
        sysUserMenuSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserMenuVO model){
        sysUserMenuSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserMenuSQL.deleteKey(id);
        return this.toResult();
    }

}
