package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

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
    public String findList(SysRoleVO model,Integer pageSize, Integer pageNum){
        List<SysRoleVO> list = sysRoleSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysRoleVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysRoleSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysRoleVO model){
        Integer count = sysRoleSQL.count(model);
        this.putJson("count", count);
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
