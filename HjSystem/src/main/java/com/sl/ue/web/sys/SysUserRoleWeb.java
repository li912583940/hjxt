package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysUserRoleVO;
import com.sl.ue.service.sys.SysUserRoleService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserRole")
public class SysUserRoleWeb extends Result{

    @Autowired
    private SysUserRoleService sysUserRoleSQL;

    @RequestMapping("/findList")
    public String findList(SysUserRoleVO model,Integer pageSize, Integer pageNum){
        List<SysUserRoleVO> list = sysUserRoleSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysUserRoleVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysUserRoleSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysUserRoleVO model){
        Integer count = sysUserRoleSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserRoleVO model = sysUserRoleSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserRoleVO model){
        sysUserRoleSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserRoleVO model){
        sysUserRoleSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserRoleSQL.deleteKey(id);
        return this.toResult();
    }

}
