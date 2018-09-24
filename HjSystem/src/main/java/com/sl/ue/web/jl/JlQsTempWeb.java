package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsTempVO;
import com.sl.ue.service.jl.JlQsTempService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQsTemp")
public class JlQsTempWeb extends Result{

    @Autowired
    private JlQsTempService jlQsTempSQL;

    @RequestMapping("/findList")
    public String findList(JlQsTempVO model,Integer pageSize, Integer pageNum){
        List<JlQsTempVO> list = jlQsTempSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlQsTempVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsTempSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlQsTempVO model){
        Integer count = jlQsTempSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsTempVO model = jlQsTempSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsTempVO model){
        jlQsTempSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsTempVO model){
        jlQsTempSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsTempSQL.deleteKey(id);
        return this.toResult();
    }

}
