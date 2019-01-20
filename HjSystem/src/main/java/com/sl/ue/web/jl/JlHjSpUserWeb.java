package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjSpUserVO;
import com.sl.ue.service.jl.JlHjSpUserService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpUser")
public class JlHjSpUserWeb extends Result{

    @Autowired
    private JlHjSpUserService jlHjSpUserSQL;

    @RequestMapping("/findList")
    public String findList(JlHjSpUserVO model,Integer pageSize, Integer pageNum){
        List<JlHjSpUserVO> list = jlHjSpUserSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjSpUserVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjSpUserSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjSpUserVO model){
        Integer count = jlHjSpUserSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpUserVO model = jlHjSpUserSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpUserVO model){
        jlHjSpUserSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpUserVO model){
        jlHjSpUserSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpUserSQL.deleteKey(id);
        return this.toResult();
    }

    @RequestMapping("/findSpeedSpUser")
    public String findSpeedSpUser(String spSetNo, Integer speedProgress){
    	return jlHjSpUserSQL.findSpeedSpUser(spSetNo, speedProgress);
    }
}
