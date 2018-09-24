package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysRoleResourceVO;
import com.sl.ue.service.sys.SysRoleResourceService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysRoleResource")
public class SysRoleResourceWeb extends Result{

    @Autowired
    private SysRoleResourceService sysRoleResourceSQL;

    @RequestMapping("/findList")
    public String findList(SysRoleResourceVO model,Integer pageSize, Integer pageNum){
        List<SysRoleResourceVO> list = sysRoleResourceSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysRoleResourceVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysRoleResourceSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysRoleResourceVO model){
        Integer count = sysRoleResourceSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysRoleResourceVO model = sysRoleResourceSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysRoleResourceVO model){
        sysRoleResourceSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysRoleResourceVO model){
        sysRoleResourceSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysRoleResourceSQL.deleteKey(id);
        return this.toResult();
    }

}
