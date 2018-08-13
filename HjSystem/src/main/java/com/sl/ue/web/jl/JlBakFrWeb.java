package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlBakFr;
import com.sl.ue.service.jl.JlBakFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlBakFr")
public class JlBakFrWeb extends Result{

    @Autowired
    private JlBakFrService JlBakFrSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlBakFr model = new JlBakFr();
        List<JlBakFr> list = JlBakFrSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlBakFr model = JlBakFrSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlBakFr model){
        JlBakFrSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlBakFr model){
        JlBakFrSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlBakFrSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
