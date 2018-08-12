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
    private JlHjSpQsService JlHjSpQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjSpQs model = new JlHjSpQs();
        List<JlHjSpQs> list = JlHjSpQsSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpQs model = JlHjSpQsSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjSpQs model){
        JlHjSpQsSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpQs model){
        JlHjSpQsSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjSpQsSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
