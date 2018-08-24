package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjRec;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjRec")
public class JlHjRecWeb extends Result{

    @Autowired
    private JlHjRecService jlHjRecSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjRec model = new JlHjRec();
        List<JlHjRec> list = jlHjRecSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRec model = jlHjRecSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjRec model){
        jlHjRecSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRec model){
        jlHjRecSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjRecSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
