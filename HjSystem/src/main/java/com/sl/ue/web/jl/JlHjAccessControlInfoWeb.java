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
    private JlHjAccessControlInfoService JlHjAccessControlInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjAccessControlInfo model = new JlHjAccessControlInfo();
        List<JlHjAccessControlInfo> list = JlHjAccessControlInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjAccessControlInfo model = JlHjAccessControlInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjAccessControlInfo model){
        JlHjAccessControlInfoSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjAccessControlInfo model){
        JlHjAccessControlInfoSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjAccessControlInfoSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
