package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlFr;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFr")
public class JlFrWeb extends Result{

    @Autowired
    private JlFrService jlFrSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFr model = new JlFr();
        List<JlFr> list = jlFrSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFr model = jlFrSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFr model){
        jlFrSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFr model){
        jlFrSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFrSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
