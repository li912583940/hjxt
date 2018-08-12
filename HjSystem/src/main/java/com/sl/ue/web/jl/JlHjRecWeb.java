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
    private JlHjRecService JlHjRecSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjRec model = new JlHjRec();
        List<JlHjRec> list = JlHjRecSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRec model = JlHjRecSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjRec model){
        JlHjRecSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRec model){
        JlHjRecSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjRecSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
