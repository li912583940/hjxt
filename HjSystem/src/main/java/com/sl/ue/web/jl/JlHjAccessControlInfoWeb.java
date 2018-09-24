package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjAccessControlInfoVO;
import com.sl.ue.service.jl.JlHjAccessControlInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjAccessControlInfo")
public class JlHjAccessControlInfoWeb extends Result{

    @Autowired
    private JlHjAccessControlInfoService jlHjAccessControlInfoSQL;

    @RequestMapping("/findList")
    public String findList(JlHjAccessControlInfoVO model,Integer pageSize, Integer pageNum){
        List<JlHjAccessControlInfoVO> list = jlHjAccessControlInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjAccessControlInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjAccessControlInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjAccessControlInfoVO model){
        Integer count = jlHjAccessControlInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjAccessControlInfoVO model = jlHjAccessControlInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjAccessControlInfoVO model){
        jlHjAccessControlInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjAccessControlInfoVO model){
        jlHjAccessControlInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjAccessControlInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
