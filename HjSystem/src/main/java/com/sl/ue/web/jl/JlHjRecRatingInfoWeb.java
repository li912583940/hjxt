package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjRecRatingInfoVO;
import com.sl.ue.service.jl.JlHjRecRatingInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjRecRatingInfo")
public class JlHjRecRatingInfoWeb extends Result{

    @Autowired
    private JlHjRecRatingInfoService jlHjRecRatingInfoSQL;

    @RequestMapping("/findList")
    public String findList(JlHjRecRatingInfoVO model,Integer pageSize, Integer pageNum){
        List<JlHjRecRatingInfoVO> list = jlHjRecRatingInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjRecRatingInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjRecRatingInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjRecRatingInfoVO model){
        Integer count = jlHjRecRatingInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRecRatingInfoVO model = jlHjRecRatingInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjRecRatingInfoVO model){
        jlHjRecRatingInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRecRatingInfoVO model){
        jlHjRecRatingInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjRecRatingInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
