package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.service.sys.SysRoleService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysRole")
public class SysRoleWeb extends Result{

    @Autowired
    private SysRoleService sysRoleSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysRoleVO model = new SysRoleVO();
        List<SysRoleVO> list = sysRoleSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysRoleVO model = sysRoleSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysRoleVO model){
        sysRoleSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysRoleVO model){
        sysRoleSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysRoleSQL.deleteKey(id);
        return this.toResult();
    }

}
