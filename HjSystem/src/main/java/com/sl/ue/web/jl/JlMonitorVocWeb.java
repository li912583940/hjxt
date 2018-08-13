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
    private JlMonitorVocService JlMonitorVocSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlMonitorVoc model = new JlMonitorVoc();
        List<JlMonitorVoc> list = JlMonitorVocSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlMonitorVoc model = JlMonitorVocSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlMonitorVoc model){
        JlMonitorVocSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlMonitorVoc model){
        JlMonitorVocSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlMonitorVocSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
