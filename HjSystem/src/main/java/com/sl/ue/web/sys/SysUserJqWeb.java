package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysUserJqVO;
import com.sl.ue.service.sys.SysUserJqService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUserJq")
public class SysUserJqWeb extends Result{

    @Autowired
    private SysUserJqService sysUserJqSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysUserJqVO model = new SysUserJqVO();
        List<SysUserJqVO> list = sysUserJqSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserJqVO model = sysUserJqSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserJqVO model){
        sysUserJqSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserJqVO model){
        sysUserJqSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserJqSQL.deleteKey(id);
        return this.toResult();
    }

}
