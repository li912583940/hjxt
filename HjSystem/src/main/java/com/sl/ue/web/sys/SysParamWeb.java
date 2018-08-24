package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysParam;
import com.sl.ue.service.sys.SysParamService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysParam")
public class SysParamWeb extends Result{

    @Autowired
    private SysParamService sysParamSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysParam model = new SysParam();
        List<SysParam> list = sysParamSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysParam model = sysParamSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysParam model){
        sysParamSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysParam model){
        sysParamSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysParamSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
