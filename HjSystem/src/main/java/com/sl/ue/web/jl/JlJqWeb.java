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
    private JlJqService JlJqSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlJq model = new JlJq();
        List<JlJq> list = JlJqSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJq model = JlJqSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlJq model){
        JlJqSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlJq model){
        JlJqSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlJqSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
