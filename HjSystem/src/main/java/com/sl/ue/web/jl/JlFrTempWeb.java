package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

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
    public String findList(JlFrTempVO model,Integer pageSize, Integer pageNum){
        List<JlFrTempVO> list = jlFrTempSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlFrTempVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFrTempSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlFrTempVO model){
        Integer count = jlFrTempSQL.count(model);
        this.putJson("count", count);
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
