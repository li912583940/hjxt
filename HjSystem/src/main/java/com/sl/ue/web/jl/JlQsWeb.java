package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlQs;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQs")
public class JlQsWeb extends Result{

    @Autowired
    private JlQsService jlQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlQs model = new JlQs();
        List<JlQs> list = jlQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQs model = jlQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQs model){
        jlQsSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQs model){
        jlQsSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
