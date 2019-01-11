package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjSpDetailsVO;
import com.sl.ue.service.jl.JlHjSpDetailsService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpDetails")
public class JlHjSpDetailsWeb extends Result{

    @Autowired
    private JlHjSpDetailsService jlHjSpDetailsSQL;

    @RequestMapping("/findList")
    public String findList(JlHjSpDetailsVO model,Integer pageSize, Integer pageNum){
        List<JlHjSpDetailsVO> list = jlHjSpDetailsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjSpDetailsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjSpDetailsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjSpDetailsVO model){
        Integer count = jlHjSpDetailsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpDetailsVO model = jlHjSpDetailsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpDetailsVO model){
        jlHjSpDetailsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpDetailsVO model){
        jlHjSpDetailsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpDetailsSQL.deleteKey(id);
        return this.toResult();
    }

}
