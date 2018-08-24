package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjMonitorTimeAdd;
import com.sl.ue.service.jl.JlHjMonitorTimeAddService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjMonitorTimeAdd")
public class JlHjMonitorTimeAddWeb extends Result{

    @Autowired
    private JlHjMonitorTimeAddService jlHjMonitorTimeAddSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjMonitorTimeAdd model = new JlHjMonitorTimeAdd();
        List<JlHjMonitorTimeAdd> list = jlHjMonitorTimeAddSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjMonitorTimeAdd model = jlHjMonitorTimeAddSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjMonitorTimeAdd model){
        jlHjMonitorTimeAddSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjMonitorTimeAdd model){
        jlHjMonitorTimeAddSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjMonitorTimeAddSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
