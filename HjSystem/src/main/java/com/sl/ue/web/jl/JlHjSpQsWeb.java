package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjSpQsVO;
import com.sl.ue.service.jl.JlHjSpQsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpQs")
public class JlHjSpQsWeb extends Result{

    @Autowired
    private JlHjSpQsService jlHjSpQsSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjSpQsVO model = new JlHjSpQsVO();
        List<JlHjSpQsVO> list = jlHjSpQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpQsVO model = jlHjSpQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpQsVO model){
        jlHjSpQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpQsVO model){
        jlHjSpQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpQsSQL.deleteKey(id);
        return this.toResult();
    }

}
