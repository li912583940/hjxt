package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlSpMxId;
import com.sl.ue.service.jl.JlSpMxIdService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlSpMxId")
public class JlSpMxIdWeb extends Result{

    @Autowired
    private JlSpMxIdService jlSpMxIdSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlSpMxId model = new JlSpMxId();
        List<JlSpMxId> list = jlSpMxIdSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlSpMxId model = jlSpMxIdSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlSpMxId model){
        jlSpMxIdSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlSpMxId model){
        jlSpMxIdSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlSpMxIdSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
