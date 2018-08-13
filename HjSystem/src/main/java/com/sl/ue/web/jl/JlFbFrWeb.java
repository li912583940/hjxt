package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlFbFr;
import com.sl.ue.service.jl.JlFbFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFbFr")
public class JlFbFrWeb extends Result{

    @Autowired
    private JlFbFrService JlFbFrSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFbFr model = new JlFbFr();
        List<JlFbFr> list = JlFbFrSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFbFr model = JlFbFrSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFbFr model){
        JlFbFrSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFbFr model){
        JlFbFrSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlFbFrSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
