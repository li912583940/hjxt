package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlQsTemp;
import com.sl.ue.service.jl.JlQsTempService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQsTemp")
public class JlQsTempWeb extends Result{

    @Autowired
    private JlQsTempService JlQsTempSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlQsTemp model = new JlQsTemp();
        List<JlQsTemp> list = JlQsTempSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsTemp model = JlQsTempSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsTemp model){
        JlQsTempSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsTemp model){
        JlQsTempSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlQsTempSQL.baseDeleteKey(id);
        return this.toResult();
    }

}