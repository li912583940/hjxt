package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjDj;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjDj")
public class JlHjDjWeb extends Result{

    @Autowired
    private JlHjDjService JlHjDjSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjDj model = new JlHjDj();
        List<JlHjDj> list = JlHjDjSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjDj model = JlHjDjSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjDj model){
        JlHjDjSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjDj model){
        JlHjDjSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjDjSQL.baseDeleteKey(id);
        return this.toResult();
    }

}