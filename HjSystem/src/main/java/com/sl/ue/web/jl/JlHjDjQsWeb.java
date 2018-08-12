package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjDjQs;
import com.sl.ue.service.jl.JlHjDjQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjDjQs")
public class JlHjDjQsWeb extends Result{

    @Autowired
    private JlHjDjQsService JlHjDjQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjDjQs model = new JlHjDjQs();
        List<JlHjDjQs> list = JlHjDjQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjDjQs model = JlHjDjQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjDjQs model){
        JlHjDjQsSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjDjQs model){
        JlHjDjQsSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjDjQsSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
