package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlFrTemp;
import com.sl.ue.service.jl.JlFrTempService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFrTemp")
public class JlFrTempWeb extends Result{

    @Autowired
    private JlFrTempService JlFrTempSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFrTemp model = new JlFrTemp();
        List<JlFrTemp> list = JlFrTempSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrTemp model = JlFrTempSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFrTemp model){
        JlFrTempSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFrTemp model){
        JlFrTempSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlFrTempSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
