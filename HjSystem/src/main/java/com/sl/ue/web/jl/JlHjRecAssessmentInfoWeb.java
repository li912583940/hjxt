package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjRecAssessmentInfoVO;
import com.sl.ue.service.jl.JlHjRecAssessmentInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjRecAssessmentInfo")
public class JlHjRecAssessmentInfoWeb extends Result{

    @Autowired
    private JlHjRecAssessmentInfoService jlHjRecAssessmentInfoSQL;

    @RequestMapping("/findList")
    public String findList(JlHjRecAssessmentInfoVO model,Integer pageSize, Integer pageNum){
        List<JlHjRecAssessmentInfoVO> list = jlHjRecAssessmentInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjRecAssessmentInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjRecAssessmentInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjRecAssessmentInfoVO model){
        Integer count = jlHjRecAssessmentInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRecAssessmentInfoVO model = jlHjRecAssessmentInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjRecAssessmentInfoVO model){
        jlHjRecAssessmentInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRecAssessmentInfoVO model){
        jlHjRecAssessmentInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjRecAssessmentInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
