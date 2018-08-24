package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjAccessControlInfo;
import com.sl.ue.service.jl.JlHjAccessControlInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjAccessControlInfo")
public class JlHjAccessControlInfoWeb extends Result{

    @Autowired
    private JlHjAccessControlInfoService jlHjAccessControlInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjAccessControlInfo model = new JlHjAccessControlInfo();
        List<JlHjAccessControlInfo> list = jlHjAccessControlInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjAccessControlInfo model = jlHjAccessControlInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjAccessControlInfo model){
        jlHjAccessControlInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjAccessControlInfo model){
        jlHjAccessControlInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjAccessControlInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
