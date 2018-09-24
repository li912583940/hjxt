package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFbQsVO;
import com.sl.ue.service.jl.JlFbQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFbQs")
public class JlFbQsWeb extends Result{

    @Autowired
    private JlFbQsService jlFbQsSQL;

    @RequestMapping("/findList")
    public String findList(JlFbQsVO model,Integer pageSize, Integer pageNum){
        List<JlFbQsVO> list = jlFbQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlFbQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFbQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlFbQsVO model){
        Integer count = jlFbQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFbQsVO model = jlFbQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFbQsVO model){
        jlFbQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFbQsVO model){
        jlFbQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFbQsSQL.deleteKey(id);
        return this.toResult();
    }

}
