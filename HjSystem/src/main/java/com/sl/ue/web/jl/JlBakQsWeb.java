package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlBakQs;
import com.sl.ue.service.jl.JlBakQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlBakQs")
public class JlBakQsWeb extends Result{

    @Autowired
    private JlBakQsService JlBakQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlBakQs model = new JlBakQs();
        List<JlBakQs> list = JlBakQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlBakQs model = JlBakQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlBakQs model){
        JlBakQsSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlBakQs model){
        JlBakQsSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlBakQsSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
