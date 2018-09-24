package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlMonitorVocVO;
import com.sl.ue.service.jl.JlMonitorVocService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlMonitorVoc")
public class JlMonitorVocWeb extends Result{

    @Autowired
    private JlMonitorVocService jlMonitorVocSQL;

    @RequestMapping("/findList")
    public String findList(JlMonitorVocVO model,Integer pageSize, Integer pageNum){
        List<JlMonitorVocVO> list = jlMonitorVocSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlMonitorVocVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlMonitorVocSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlMonitorVocVO model){
        Integer count = jlMonitorVocSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlMonitorVocVO model = jlMonitorVocSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlMonitorVocVO model){
        jlMonitorVocSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlMonitorVocVO model){
        jlMonitorVocSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlMonitorVocSQL.deleteKey(id);
        return this.toResult();
    }

}
