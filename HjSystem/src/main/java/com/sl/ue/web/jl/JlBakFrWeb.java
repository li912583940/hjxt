package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlBakFrVO;
import com.sl.ue.service.jl.JlBakFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlBakFr")
public class JlBakFrWeb extends Result{

    @Autowired
    private JlBakFrService jlBakFrSQL;

    @RequestMapping("/findList")
    public String findList(JlBakFrVO model,Integer pageSize, Integer pageNum){
        List<JlBakFrVO> list = jlBakFrSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlBakFrVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlBakFrSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlBakFrVO model){
        Integer count = jlBakFrSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlBakFrVO model = jlBakFrSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlBakFrVO model){
        jlBakFrSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlBakFrVO model){
        jlBakFrSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlBakFrSQL.deleteKey(id);
        return this.toResult();
    }

}
