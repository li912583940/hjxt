package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlJq")
public class JlJqWeb extends Result{

    @Autowired
    private JlJqService jlJqSQL;

    @RequestMapping("/findList")
    public String findList(JlJqVO model,Integer pageSize, Integer pageNum){
        List<JlJqVO> list = jlJqSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlJqVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlJqSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlJqVO model){
        Integer count = jlJqSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJqVO model = jlJqSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJqVO model){
        jlJqSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJqVO model){
        jlJqSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlJqSQL.deleteKey(id);
        return this.toResult();
    }

}
