package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlJb;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlJb")
public class JlJbWeb extends Result{

    @Autowired
    private JlJbService jlJbSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlJb model = new JlJb();
        List<JlJb> list = jlJbSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJb model = jlJbSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJb model){
        jlJbSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJb model){
        jlJbSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlJbSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
