package com.sl.ue.web.jl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjHolidayVO;
import com.sl.ue.entity.jl.vo.JlHjJqHolidayVO;
import com.sl.ue.service.jl.JlHjHolidayService;
import com.sl.ue.service.jl.JlHjJqHolidayService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjHoliday")
public class JlHjHolidayWeb extends Result{

    @Autowired
    private JlHjHolidayService jlHjHolidaySQL;
    @Autowired
    private JlHjJqHolidayService jlHjJqHolidaySQL;
    
    @RequestMapping("/findList")
    public String findList(JlHjHolidayVO model,Integer pageSize, Integer pageNum){
        List<JlHjHolidayVO> list = jlHjHolidaySQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjHolidayVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjHolidaySQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjHolidayVO model){
        Integer count = jlHjHolidaySQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(String holiday){
        JlHjHolidayVO model = jlHjHolidaySQL.findOne(holiday);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(String holidays){
    	if(StringUtils.isBlank(holidays)){
    		this.error(error_102);
    		return this.toResult();
    	}
    	for(String holiday : holidays.split(",")){
    		JlHjHolidayVO model = new JlHjHolidayVO();
    		model.setHoliday(holiday);
    		int count = jlHjHolidaySQL.count(model);
    		if(count<=0){
    			jlHjHolidaySQL.add(model);
    		}
    	}
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjHolidayVO model){
        jlHjHolidaySQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(String holiday){
        jlHjHolidaySQL.deleteKey(holiday);
        return this.toResult();
    }
    
    @RequestMapping("/getCheckedJq")
    public String getCheckedJq(){
    	List<JlHjJqHolidayVO> list = jlHjJqHolidaySQL.findList(new JlHjJqHolidayVO());
    	List<String> res = new ArrayList<String>();
    	for(JlHjJqHolidayVO t : list){
    		res.add(t.getJqNo());
    	}
    	this.putJson(res);
    	return this.toResult();
    }
    
    @RequestMapping("/addJqHoliday")
    public String addJqHoliday(String jqValues){
    	if(StringUtils.isBlank(jqValues)){
    		this.error(error_102);
    		return this.toResult();
    	}
    	jlHjJqHolidaySQL.delete(new JlHjJqHolidayVO());
    	for(String jqNo : jqValues.split(",")){
    		JlHjJqHolidayVO model = new JlHjJqHolidayVO();
    		model.setJqNo(jqNo);
    		jlHjJqHolidaySQL.add(model);
    	}
    	return this.toResult();
    
    }
    @RequestMapping("/emptyDate")
    public String emptyDate(){
    	jlHjHolidaySQL.delete(new JlHjHolidayVO());
    	return this.toResult();
    }
}
