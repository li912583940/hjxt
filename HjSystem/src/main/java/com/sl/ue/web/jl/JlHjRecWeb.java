package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjRec")
public class JlHjRecWeb extends Result{

    @Autowired
    private JlHjRecService jlHjRecSQL;

    @RequestMapping("/findList")
    public String findList(JlHjRecVO model,Integer pageSize, Integer pageNum){
        List<JlHjRecVO> list = jlHjRecSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjRecVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjRecSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjRecVO model){
        Integer count = jlHjRecSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRecVO model = jlHjRecSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjRecVO model){
        jlHjRecSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRecVO model){
        jlHjRecSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjRecSQL.deleteKey(id);
        return this.toResult();
    }

}
