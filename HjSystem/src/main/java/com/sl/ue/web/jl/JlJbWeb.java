package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlJb")
public class JlJbWeb extends Result{

    @Autowired
    private JlJbService jlJbSQL;

    @RequestMapping("/findList")
    public String findList(JlJbVO model,Integer pageSize, Integer pageNum){
        List<JlJbVO> list = jlJbSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlJbVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlJbSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlJbVO model){
        Integer count = jlJbSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJbVO model = jlJbSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJbVO model){
        jlJbSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJbVO model){
        jlJbSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlJbSQL.deleteKey(id);
        return this.toResult();
    }

}
