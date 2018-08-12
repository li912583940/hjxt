package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlHjRecAssessmentInfo;
import com.sl.ue.service.jl.JlHjRecAssessmentInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjRecAssessmentInfo")
public class JlHjRecAssessmentInfoWeb extends Result{

    @Autowired
    private JlHjRecAssessmentInfoService JlHjRecAssessmentInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlHjRecAssessmentInfo model = new JlHjRecAssessmentInfo();
        List<JlHjRecAssessmentInfo> list = JlHjRecAssessmentInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRecAssessmentInfo model = JlHjRecAssessmentInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(JlHjRecAssessmentInfo model){
        JlHjRecAssessmentInfoSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRecAssessmentInfo model){
        JlHjRecAssessmentInfoSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlHjRecAssessmentInfoSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
