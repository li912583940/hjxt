package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysRolePermissionVO;
import com.sl.ue.service.sys.SysRolePermissionService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysRolePermission")
public class SysRolePermissionWeb extends Result{

    @Autowired
    private SysRolePermissionService sysRolePermissionSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysRolePermissionVO model = new SysRolePermissionVO();
        List<SysRolePermissionVO> list = sysRolePermissionSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysRolePermissionVO model = sysRolePermissionSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysRolePermissionVO model){
        sysRolePermissionSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysRolePermissionVO model){
        sysRolePermissionSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysRolePermissionSQL.deleteKey(id);
        return this.toResult();
    }

}
