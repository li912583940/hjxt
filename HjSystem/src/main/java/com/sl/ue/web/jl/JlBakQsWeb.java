package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlBakQsVO;
import com.sl.ue.service.jl.JlBakQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlBakQs")
public class JlBakQsWeb extends Result{

    @Autowired
    private JlBakQsService jlBakQsSQL;

    @RequestMapping("/findList")
    public String findList(JlBakQsVO model,Integer pageSize, Integer pageNum){
        List<JlBakQsVO> list = jlBakQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlBakQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlBakQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlBakQsVO model){
        Integer count = jlBakQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlBakQsVO model = jlBakQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlBakQsVO model){
        jlBakQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlBakQsVO model){
        jlBakQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlBakQsSQL.deleteKey(id);
        return this.toResult();
    }

}
