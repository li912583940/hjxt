package com.sl.ue.web.sys;

import java.util.List;

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
    public String findList(Integer pageSize, Integer pageNum){
        SysRoleResourceVO model = new SysRoleResourceVO();
        List<SysRoleResourceVO> list = sysRoleResourceSQL.findList(model, pageSize, pageNum);
        this.putData(list);
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
