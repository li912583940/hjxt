package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjSpQsVO;
import com.sl.ue.service.jl.JlHjSpQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpQs")
public class JlHjSpQsWeb extends Result{

    @Autowired
    private JlHjSpQsService jlHjSpQsSQL;

    @RequestMapping("/findList")
    public String findList(JlHjSpQsVO model,Integer pageSize, Integer pageNum){
        List<JlHjSpQsVO> list = jlHjSpQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjSpQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjSpQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjSpQsVO model){
        Integer count = jlHjSpQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpQsVO model = jlHjSpQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpQsVO model){
        jlHjSpQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpQsVO model){
        jlHjSpQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpQsSQL.deleteKey(id);
        return this.toResult();
    }

}
