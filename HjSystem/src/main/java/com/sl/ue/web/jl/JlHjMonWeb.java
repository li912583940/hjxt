package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjMon;
import com.sl.ue.service.jl.JlHjMonService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjMon")
public class JlHjMonWeb extends Result{

    @Autowired
    private JlHjMonService JlHjMonSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjMon model = new JlHjMon();
        List<JlHjMon> list = JlHjMonSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjMon model = JlHjMonSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjMon model){
        JlHjMonSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjMon model){
        JlHjMonSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjMonSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
