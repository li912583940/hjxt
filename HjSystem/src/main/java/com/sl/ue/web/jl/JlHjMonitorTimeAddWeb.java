package com.sl.ue.web.jl;

import java.util.List;

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
    public String findList(Integer pageSize, Integer pageNum){
        JlHjMonitorTimeAddVO model = new JlHjMonitorTimeAddVO();
        List<JlHjMonitorTimeAddVO> list = jlHjMonitorTimeAddSQL.findList(model, pageSize, pageNum);
        this.putData(list);
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
