package com.sl.ue.web.jl;

import java.util.List;

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
    public String findList(Integer pageSize, Integer pageNum){
        JlMonitorVocVO model = new JlMonitorVocVO();
        List<JlMonitorVocVO> list = jlMonitorVocSQL.findList(model, pageSize, pageNum);
        this.putData(list);
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
