package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFrTempVO;
import com.sl.ue.service.jl.JlFrTempService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFrTemp")
public class JlFrTempWeb extends Result{

    @Autowired
    private JlFrTempService jlFrTempSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlFrTempVO model = new JlFrTempVO();
        List<JlFrTempVO> list = jlFrTempSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrTempVO model = jlFrTempSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFrTempVO model){
        jlFrTempSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFrTempVO model){
        jlFrTempSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFrTempSQL.deleteKey(id);
        return this.toResult();
    }

}
