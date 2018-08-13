package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlYj;
import com.sl.ue.service.jl.JlYjService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlYj")
public class JlYjWeb extends Result{

    @Autowired
    private JlYjService JlYjSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlYj model = new JlYj();
        List<JlYj> list = JlYjSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlYj model = JlYjSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlYj model){
        JlYjSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlYj model){
        JlYjSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlYjSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
