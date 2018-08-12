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
    private JlQsService JlQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlQs model = new JlQs();
        List<JlQs> list = JlQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQs model = JlQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlQs model){
        JlQsSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlQs model){
        JlQsSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlQsSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
