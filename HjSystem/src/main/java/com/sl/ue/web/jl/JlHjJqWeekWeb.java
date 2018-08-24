package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjJqWeek;
import com.sl.ue.service.jl.JlHjJqWeekService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjJqWeek")
public class JlHjJqWeekWeb extends Result{

    @Autowired
    private JlHjJqWeekService JlHjJqWeekSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjJqWeek model = new JlHjJqWeek();
        List<JlHjJqWeek> list = JlHjJqWeekSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjJqWeek model = JlHjJqWeekSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjJqWeek model){
        JlHjJqWeekSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjJqWeek model){
        JlHjJqWeekSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjJqWeekSQL.baseDeleteKey(id);
        return this.toResult();
    }

}