package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjMonVO;
import com.sl.ue.service.jl.JlHjMonService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjMon")
public class JlHjMonWeb extends Result{

    @Autowired
    private JlHjMonService jlHjMonSQL;

    @RequestMapping("/findList")
    public String findList(JlHjMonVO model,Integer pageSize, Integer pageNum){
        List<JlHjMonVO> list = jlHjMonSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjMonVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjMonSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjMonVO model){
        Integer count = jlHjMonSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjMonVO model = jlHjMonSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjMonVO model){
        jlHjMonSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjMonVO model){
        jlHjMonSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjMonSQL.deleteKey(id);
        return this.toResult();
    }

}
