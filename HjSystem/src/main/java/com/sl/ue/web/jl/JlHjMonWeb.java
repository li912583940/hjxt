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
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjMon model = JlHjMonSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjMon model){
        JlHjMonSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjMon model){
        JlHjMonSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjMonSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
