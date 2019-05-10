package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlTtsVO;
import com.sl.ue.service.jl.JlTtsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlTts")
public class JlTtsWeb extends Result{

    @Autowired
    private JlTtsService jlTtsSQL;

    @RequestMapping("/findList")
    public String findList(JlTtsVO model,Integer pageSize, Integer pageNum){
        List<JlTtsVO> list = jlTtsSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlTtsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlTtsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlTtsVO model){
        Integer count = jlTtsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlTtsVO model = jlTtsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlTtsVO model){
        jlTtsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlTtsVO model){
        jlTtsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlTtsSQL.deleteKey(id);
        return this.toResult();
    }

}
