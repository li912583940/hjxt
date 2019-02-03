package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjJqHolidayVO;
import com.sl.ue.service.jl.JlHjJqHolidayService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjJqHoliday")
public class JlHjJqHolidayWeb extends Result{

    @Autowired
    private JlHjJqHolidayService jlHjJqHolidaySQL;

    @RequestMapping("/findList")
    public String findList(JlHjJqHolidayVO model,Integer pageSize, Integer pageNum){
        List<JlHjJqHolidayVO> list = jlHjJqHolidaySQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjJqHolidayVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjJqHolidaySQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjJqHolidayVO model){
        Integer count = jlHjJqHolidaySQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjJqHolidayVO model = jlHjJqHolidaySQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjJqHolidayVO model){
        jlHjJqHolidaySQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjJqHolidayVO model){
        jlHjJqHolidaySQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjJqHolidaySQL.deleteKey(id);
        return this.toResult();
    }

}
