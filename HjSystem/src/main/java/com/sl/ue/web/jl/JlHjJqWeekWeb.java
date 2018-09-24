package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjJqWeekVO;
import com.sl.ue.service.jl.JlHjJqWeekService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjJqWeek")
public class JlHjJqWeekWeb extends Result{

    @Autowired
    private JlHjJqWeekService jlHjJqWeekSQL;

    @RequestMapping("/findList")
    public String findList(JlHjJqWeekVO model,Integer pageSize, Integer pageNum){
        List<JlHjJqWeekVO> list = jlHjJqWeekSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjJqWeekVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjJqWeekSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjJqWeekVO model){
        Integer count = jlHjJqWeekSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjJqWeekVO model = jlHjJqWeekSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjJqWeekVO model){
        jlHjJqWeekSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjJqWeekVO model){
        jlHjJqWeekSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjJqWeekSQL.deleteKey(id);
        return this.toResult();
    }

}
