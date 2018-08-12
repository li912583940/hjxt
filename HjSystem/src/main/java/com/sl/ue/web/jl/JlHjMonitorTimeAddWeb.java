package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjMonitorTimeAdd;
import com.sl.ue.service.jl.JlHjMonitorTimeAddService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjMonitorTimeAdd")
public class JlHjMonitorTimeAddWeb extends Result{

    @Autowired
    private JlHjMonitorTimeAddService JlHjMonitorTimeAddSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjMonitorTimeAdd model = new JlHjMonitorTimeAdd();
        List<JlHjMonitorTimeAdd> list = JlHjMonitorTimeAddSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjMonitorTimeAdd model = JlHjMonitorTimeAddSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjMonitorTimeAdd model){
        JlHjMonitorTimeAddSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjMonitorTimeAdd model){
        JlHjMonitorTimeAddSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjMonitorTimeAddSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
