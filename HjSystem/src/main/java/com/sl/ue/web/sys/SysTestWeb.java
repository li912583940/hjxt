package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysTestVO;
import com.sl.ue.service.sys.SysTestService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysTest")
public class SysTestWeb extends Result{

    @Autowired
    private SysTestService sysTestSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysTestVO model = new SysTestVO();
        List<SysTestVO> list = sysTestSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysTestVO model = sysTestSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysTestVO model){
        sysTestSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysTestVO model){
        sysTestSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysTestSQL.deleteKey(id);
        return this.toResult();
    }
    
    

}
