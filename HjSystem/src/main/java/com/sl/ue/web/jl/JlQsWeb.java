package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQs")
public class JlQsWeb extends Result{

    @Autowired
    private JlQsService jlQsSQL;

    @RequestMapping("/findList")
    public String findList(JlQsVO model,Integer pageSize, Integer pageNum){
        List<JlQsVO> list = jlQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlQsVO model){
        Integer count = jlQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsVO model = jlQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsVO model){
        jlQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsVO model){
        jlQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsSQL.deleteKey(id);
        return this.toResult();
    }

}
