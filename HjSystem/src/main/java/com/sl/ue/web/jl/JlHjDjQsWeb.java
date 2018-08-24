package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjDjQsVO;
import com.sl.ue.service.jl.JlHjDjQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjDjQs")
public class JlHjDjQsWeb extends Result{

    @Autowired
    private JlHjDjQsService jlHjDjQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjDjQsVO model = new JlHjDjQsVO();
        List<JlHjDjQsVO> list = jlHjDjQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjDjQsVO model = jlHjDjQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjDjQsVO model){
        jlHjDjQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjDjQsVO model){
        jlHjDjQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjDjQsSQL.deleteKey(id);
        return this.toResult();
    }

}
