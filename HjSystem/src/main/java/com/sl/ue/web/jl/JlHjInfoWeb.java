package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjInfoVO;
import com.sl.ue.service.jl.JlHjInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjInfo")
public class JlHjInfoWeb extends Result{

    @Autowired
    private JlHjInfoService jlHjInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjInfoVO model = new JlHjInfoVO();
        List<JlHjInfoVO> list = jlHjInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjInfoVO model = jlHjInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjInfoVO model){
        jlHjInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjInfoVO model){
        jlHjInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
