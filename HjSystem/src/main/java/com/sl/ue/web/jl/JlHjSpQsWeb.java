package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjSpQs;
import com.sl.ue.service.jl.JlHjSpQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpQs")
public class JlHjSpQsWeb extends Result{

    @Autowired
    private JlHjSpQsService jlHjSpQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjSpQs model = new JlHjSpQs();
        List<JlHjSpQs> list = jlHjSpQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpQs model = jlHjSpQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpQs model){
        jlHjSpQsSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpQs model){
        jlHjSpQsSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpQsSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
