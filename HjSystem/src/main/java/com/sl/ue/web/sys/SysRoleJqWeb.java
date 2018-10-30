package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysRoleJqVO;
import com.sl.ue.service.sys.SysRoleJqService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysRoleJq")
public class SysRoleJqWeb extends Result{

    @Autowired
    private SysRoleJqService sysRoleJqSQL;

    @RequestMapping("/findList")
    public String findList(SysRoleJqVO model,Integer pageSize, Integer pageNum){
        List<SysRoleJqVO> list = sysRoleJqSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysRoleJqVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysRoleJqSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysRoleJqVO model){
        Integer count = sysRoleJqSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysRoleJqVO model = sysRoleJqSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysRoleJqVO model){
        sysRoleJqSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysRoleJqVO model){
        sysRoleJqSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysRoleJqSQL.deleteKey(id);
        return this.toResult();
    }

}
