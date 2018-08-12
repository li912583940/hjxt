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
    private JlFrService JlFrSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFr model = new JlFr();
        List<JlFr> list = JlFrSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFr model = JlFrSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlFr model){
        JlFrSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlFr model){
        JlFrSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlFrSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
