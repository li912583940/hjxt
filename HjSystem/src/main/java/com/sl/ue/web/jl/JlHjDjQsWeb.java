package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjDjQsVO;
import com.sl.ue.service.jl.JlHjDjQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjDjQs")
public class JlHjDjQsWeb extends Result{

    @Autowired
    private JlHjDjQsService jlHjDjQsSQL;

    @RequestMapping("/findList")
    public String findList(JlHjDjQsVO model,Integer pageSize, Integer pageNum){
        List<JlHjDjQsVO> list = jlHjDjQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjDjQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjDjQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjDjQsVO model){
        Integer count = jlHjDjQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjDjQsVO model = jlHjDjQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjDjQsVO model){
        jlHjDjQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjDjQsVO model){
        jlHjDjQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjDjQsSQL.deleteKey(id);
        return this.toResult();
    }

}
