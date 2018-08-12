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
    private JlFrWpPkService JlFrWpPkSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFrWpPk model = new JlFrWpPk();
        List<JlFrWpPk> list = JlFrWpPkSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrWpPk model = JlFrWpPkSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlFrWpPk model){
        JlFrWpPkSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlFrWpPk model){
        JlFrWpPkSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlFrWpPkSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
