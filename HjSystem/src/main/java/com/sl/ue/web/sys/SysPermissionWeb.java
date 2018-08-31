package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysPermissionVO;
import com.sl.ue.service.sys.SysPermissionService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysPermission")
public class SysPermissionWeb extends Result{

    @Autowired
    private SysPermissionService sysPermissionSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysPermissionVO model = new SysPermissionVO();
        List<SysPermissionVO> list = sysPermissionSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysPermissionVO model = sysPermissionSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysPermissionVO model){
        sysPermissionSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysPermissionVO model){
        sysPermissionSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysPermissionSQL.deleteKey(id);
        return this.toResult();
    }

}
