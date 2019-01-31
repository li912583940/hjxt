package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsSpVO;
import com.sl.ue.service.jl.JlQsSpService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQsSp")
public class JlQsSpWeb extends Result{

    @Autowired
    private JlQsSpService jlQsSpSQL;

    @RequestMapping("/findList")
    public String findList(JlQsSpVO model,Integer pageSize, Integer pageNum){
        List<JlQsSpVO> list = jlQsSpSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlQsSpVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsSpSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlQsSpVO model){
        Integer count = jlQsSpSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsSpVO model = jlQsSpSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsSpVO model){
        jlQsSpSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsSpVO model){
        jlQsSpSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsSpSQL.deleteKey(id);
        return this.toResult();
    }

}
