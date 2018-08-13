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
    private JlSpMxIdService JlSpMxIdSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlSpMxId model = new JlSpMxId();
        List<JlSpMxId> list = JlSpMxIdSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlSpMxId model = JlSpMxIdSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlSpMxId model){
        JlSpMxIdSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlSpMxId model){
        JlSpMxIdSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlSpMxIdSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
