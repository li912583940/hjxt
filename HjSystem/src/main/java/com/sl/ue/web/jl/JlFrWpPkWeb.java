package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFrWpPkVO;
import com.sl.ue.service.jl.JlFrWpPkService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlFrWpPk")
public class JlFrWpPkWeb extends Result{

    @Autowired
    private JlFrWpPkService jlFrWpPkSQL;

    @RequestMapping("/findList")
    public String findList(JlFrWpPkVO model,Integer pageSize, Integer pageNum){
        List<JlFrWpPkVO> list = jlFrWpPkSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlFrWpPkVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFrWpPkSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlFrWpPkVO model){
        Integer count = jlFrWpPkSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrWpPkVO model = jlFrWpPkSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFrWpPkVO model){
        jlFrWpPkSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFrWpPkVO model){
        jlFrWpPkSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFrWpPkSQL.deleteKey(id);
        return this.toResult();
    }

}
