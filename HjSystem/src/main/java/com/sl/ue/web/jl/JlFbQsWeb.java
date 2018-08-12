package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlFbQs;
import com.sl.ue.service.jl.JlFbQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFbQs")
public class JlFbQsWeb extends Result{

    @Autowired
    private JlFbQsService JlFbQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFbQs model = new JlFbQs();
        List<JlFbQs> list = JlFbQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFbQs model = JlFbQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlFbQs model){
        JlFbQsSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlFbQs model){
        JlFbQsSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlFbQsSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
