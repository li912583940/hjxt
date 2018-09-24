package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFbFrVO;
import com.sl.ue.service.jl.JlFbFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFbFr")
public class JlFbFrWeb extends Result{

    @Autowired
    private JlFbFrService jlFbFrSQL;

    @RequestMapping("/findList")
    public String findList(JlFbFrVO model,Integer pageSize, Integer pageNum){
        List<JlFbFrVO> list = jlFbFrSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlFbFrVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFbFrSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlFbFrVO model){
        Integer count = jlFbFrSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFbFrVO model = jlFbFrSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFbFrVO model){
        jlFbFrSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFbFrVO model){
        jlFbFrSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFbFrSQL.deleteKey(id);
        return this.toResult();
    }

}
