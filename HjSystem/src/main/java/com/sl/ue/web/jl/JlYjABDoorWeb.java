package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlYjABDoorVO;
import com.sl.ue.service.jl.JlYjABDoorService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlYjABDoor")
public class JlYjABDoorWeb extends Result{

    @Autowired
    private JlYjABDoorService jlYjABDoorSQL;

    @RequestMapping("/findList")
    public String findList(JlYjABDoorVO model,Integer pageSize, Integer pageNum){
        List<JlYjABDoorVO> list = jlYjABDoorSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlYjABDoorVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlYjABDoorSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlYjABDoorVO model){
        Integer count = jlYjABDoorSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlYjABDoorVO model = jlYjABDoorSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlYjABDoorVO model){
        jlYjABDoorSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlYjABDoorVO model){
        jlYjABDoorSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlYjABDoorSQL.deleteKey(id);
        return this.toResult();
    }

}
