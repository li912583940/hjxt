package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlJq;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlJq")
public class JlJqWeb extends Result{

    @Autowired
    private JlJqService jlJqSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlJq model = new JlJq();
        List<JlJq> list = jlJqSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJq model = jlJqSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJq model){
        jlJqSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJq model){
        jlJqSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlJqSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
