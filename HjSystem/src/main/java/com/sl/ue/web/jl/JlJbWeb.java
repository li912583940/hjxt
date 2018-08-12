package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlJb;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlJb")
public class JlJbWeb extends Result{

    @Autowired
    private JlJbService JlJbSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlJb model = new JlJb();
        List<JlJb> list = JlJbSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJb model = JlJbSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlJb model){
        JlJbSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlJb model){
        JlJbSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlJbSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
