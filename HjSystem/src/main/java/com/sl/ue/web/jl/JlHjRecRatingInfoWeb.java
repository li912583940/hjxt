package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjRecRatingInfo;
import com.sl.ue.service.jl.JlHjRecRatingInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjRecRatingInfo")
public class JlHjRecRatingInfoWeb extends Result{

    @Autowired
    private JlHjRecRatingInfoService JlHjRecRatingInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjRecRatingInfo model = new JlHjRecRatingInfo();
        List<JlHjRecRatingInfo> list = JlHjRecRatingInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRecRatingInfo model = JlHjRecRatingInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjRecRatingInfo model){
        JlHjRecRatingInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRecRatingInfo model){
        JlHjRecRatingInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjRecRatingInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
