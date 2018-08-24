package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlBakFrVO;
import com.sl.ue.service.jl.JlBakFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlBakFr")
public class JlBakFrWeb extends Result{

    @Autowired
    private JlBakFrService jlBakFrSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlBakFrVO model = new JlBakFrVO();
        List<JlBakFrVO> list = jlBakFrSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlBakFrVO model = jlBakFrSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlBakFrVO model){
        jlBakFrSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlBakFrVO model){
        jlBakFrSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlBakFrSQL.deleteKey(id);
        return this.toResult();
    }

}
