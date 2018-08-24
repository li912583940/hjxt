package com.sl.ue.web.jl;

import java.util.List;

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
    public String findList(Integer pageSize, Integer pageNum){
        JlHjRecRatingInfoVO model = new JlHjRecRatingInfoVO();
        List<JlHjRecRatingInfoVO> list = jlHjRecRatingInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
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
