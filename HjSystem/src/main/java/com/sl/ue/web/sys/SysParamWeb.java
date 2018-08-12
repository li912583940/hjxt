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
    private SysParamService SysParamSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysParam model = new SysParam();
        List<SysParam> list = SysParamSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysParam model = SysParamSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(SysParam model){
        SysParamSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(SysParam model){
        SysParamSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysParamSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
