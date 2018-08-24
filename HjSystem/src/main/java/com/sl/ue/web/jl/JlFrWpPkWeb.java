package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlFrWpPk;
import com.sl.ue.service.jl.JlFrWpPkService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFrWpPk")
public class JlFrWpPkWeb extends Result{

    @Autowired
    private JlFrWpPkService jlFrWpPkSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFrWpPk model = new JlFrWpPk();
        List<JlFrWpPk> list = jlFrWpPkSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrWpPk model = jlFrWpPkSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFrWpPk model){
        jlFrWpPkSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFrWpPk model){
        jlFrWpPkSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFrWpPkSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
