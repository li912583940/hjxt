package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjMonitorTimeAddVO;
import com.sl.ue.service.jl.JlHjMonitorTimeAddService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjMonitorTimeAdd")
public class JlHjMonitorTimeAddWeb extends Result{

    @Autowired
    private JlHjMonitorTimeAddService jlHjMonitorTimeAddSQL;

    @RequestMapping("/findList")
    public String findList(JlHjMonitorTimeAddVO model,Integer pageSize, Integer pageNum){
        List<JlHjMonitorTimeAddVO> list = jlHjMonitorTimeAddSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjMonitorTimeAddVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjMonitorTimeAddSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjMonitorTimeAddVO model){
        Integer count = jlHjMonitorTimeAddSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjMonitorTimeAddVO model = jlHjMonitorTimeAddSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjMonitorTimeAddVO model){
        jlHjMonitorTimeAddSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjMonitorTimeAddVO model){
        jlHjMonitorTimeAddSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjMonitorTimeAddSQL.deleteKey(id);
        return this.toResult();
    }

}
