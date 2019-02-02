package com.sl.ue.web.other;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsGxVO;
import com.sl.ue.entity.other.vo.DeptVO;
import com.sl.ue.service.other.DeptService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/dept")
public class DeptWeb extends Result{

    @Autowired
    private DeptService deptSQL;

    @RequestMapping("/findList")
    public String findList(DeptVO model,Integer pageSize, Integer pageNum){
        List<DeptVO> list = deptSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(DeptVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = deptSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(DeptVO model){
        Integer count = deptSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        DeptVO model = deptSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(DeptVO model){
    	DeptVO deptQuery = new DeptVO();
    	deptQuery.setDeptName(model.getDeptName());
    	Integer count = deptSQL.count(deptQuery);
    	if(count > 0){
    		this.error(error_103, "当前部门名称<"+model.getDeptName()+">已存在");
    		return this.toResult();
    	}
        deptSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(DeptVO model){
    	DeptVO oldDept = deptSQL.findOne(model.getId());
    	if(!oldDept.getDeptName().equals(model.getDeptName())){
    		DeptVO deptQuery = new DeptVO();
        	deptQuery.setDeptName(model.getDeptName());
        	Integer count = deptSQL.count(deptQuery);
        	if(count > 0){
        		this.error(error_103, "当前部门名称<"+model.getDeptName()+">已存在");
        		return this.toResult();
        	}
    	}
        deptSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        deptSQL.deleteKey(id);
        return this.toResult();
    }

}
