package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjSpFrVO;
import com.sl.ue.service.jl.JlHjSpFrService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpFr")
public class JlHjSpFrWeb extends Result{

    @Autowired
    private JlHjSpFrService jlHjSpFrSQL;

    @RequestMapping("/findList")
    public String findList(JlHjSpFrVO model,Integer pageSize, Integer pageNum){
        List<JlHjSpFrVO> list = jlHjSpFrSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjSpFrVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjSpFrSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjSpFrVO model){
        Integer count = jlHjSpFrSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpFrVO model = jlHjSpFrSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpFrVO model){
        jlHjSpFrSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpFrVO model){
        jlHjSpFrSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpFrSQL.deleteKey(id);
        return this.toResult();
    }

}
