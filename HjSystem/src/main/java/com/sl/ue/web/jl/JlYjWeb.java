package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlYjVO;
import com.sl.ue.service.jl.JlYjService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlYj")
public class JlYjWeb extends Result{

    @Autowired
    private JlYjService jlYjSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlYjVO model = new JlYjVO();
        List<JlYjVO> list = jlYjSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlYjVO model = jlYjSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlYjVO model){
        jlYjSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlYjVO model){
        jlYjSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlYjSQL.deleteKey(id);
        return this.toResult();
    }

}
