package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjInfo;
import com.sl.ue.service.jl.JlHjInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjInfo")
public class JlHjInfoWeb extends Result{

    @Autowired
    private JlHjInfoService jlHjInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjInfo model = new JlHjInfo();
        List<JlHjInfo> list = jlHjInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjInfo model = jlHjInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjInfo model){
        jlHjInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjInfo model){
        jlHjInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
