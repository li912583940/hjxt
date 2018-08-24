package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlMonitorVoc;
import com.sl.ue.service.jl.JlMonitorVocService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlMonitorVoc")
public class JlMonitorVocWeb extends Result{

    @Autowired
    private JlMonitorVocService jlMonitorVocSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlMonitorVoc model = new JlMonitorVoc();
        List<JlMonitorVoc> list = jlMonitorVocSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlMonitorVoc model = jlMonitorVocSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlMonitorVoc model){
        jlMonitorVocSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlMonitorVoc model){
        jlMonitorVocSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlMonitorVocSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
