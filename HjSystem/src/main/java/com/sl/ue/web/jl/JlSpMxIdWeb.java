package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlSpMxIdVO;
import com.sl.ue.service.jl.JlSpMxIdService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlSpMxId")
public class JlSpMxIdWeb extends Result{

    @Autowired
    private JlSpMxIdService jlSpMxIdSQL;

    @RequestMapping("/findList")
    public String findList(JlSpMxIdVO model,Integer pageSize, Integer pageNum){
        List<JlSpMxIdVO> list = jlSpMxIdSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlSpMxIdVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlSpMxIdSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlSpMxIdVO model){
        Integer count = jlSpMxIdSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlSpMxIdVO model = jlSpMxIdSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlSpMxIdVO model){
        jlSpMxIdSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlSpMxIdVO model){
        jlSpMxIdSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlSpMxIdSQL.deleteKey(id);
        return this.toResult();
    }

}
