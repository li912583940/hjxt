package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsTempVO;
import com.sl.ue.service.jl.JlQsTempService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQsTemp")
public class JlQsTempWeb extends Result{

    @Autowired
    private JlQsTempService jlQsTempSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlQsTempVO model = new JlQsTempVO();
        List<JlQsTempVO> list = jlQsTempSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsTempVO model = jlQsTempSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsTempVO model){
        jlQsTempSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsTempVO model){
        jlQsTempSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsTempSQL.deleteKey(id);
        return this.toResult();
    }

}
