package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysUserGroupVO;
import com.sl.ue.service.sys.SysUserGroupService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserGroup")
public class SysUserGroupWeb extends Result{

    @Autowired
    private SysUserGroupService sysUserGroupSQL;

    @RequestMapping("/findList")
    public String findList(SysUserGroupVO model,Integer pageSize, Integer pageNum){
        List<SysUserGroupVO> list = sysUserGroupSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysUserGroupVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysUserGroupSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysUserGroupVO model){
        Integer count = sysUserGroupSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserGroupVO model = sysUserGroupSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserGroupVO model){
        sysUserGroupSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserGroupVO model){
        sysUserGroupSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserGroupSQL.deleteKey(id);
        return this.toResult();
    }

}
