package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsGxVO;
import com.sl.ue.service.jl.JlQsGxService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlQsGx")
public class JlQsGxWeb extends Result{

    @Autowired
    private JlQsGxService jlQsGxSQL;

    @RequestMapping("/findList")
    public String findList(JlQsGxVO model,Integer pageSize, Integer pageNum){
        List<JlQsGxVO> list = jlQsGxSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlQsGxVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsGxSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlQsGxVO model){
        Integer count = jlQsGxSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsGxVO model = jlQsGxSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsGxVO model){
        jlQsGxSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsGxVO model){
        jlQsGxSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsGxSQL.deleteKey(id);
        return this.toResult();
    }

}
