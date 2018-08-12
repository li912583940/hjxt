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
    private JlHjInfoService JlHjInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjInfo model = new JlHjInfo();
        List<JlHjInfo> list = JlHjInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjInfo model = JlHjInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjInfo model){
        JlHjInfoSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjInfo model){
        JlHjInfoSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjInfoSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
