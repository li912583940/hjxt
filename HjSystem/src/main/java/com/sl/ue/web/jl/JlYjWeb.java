package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlYjVO;
import com.sl.ue.service.jl.JlYjService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [警察]
 * L_晓天  @2018年10月7日
 */
@RestController
@RequestMapping("/jlYj")
public class JlYjWeb extends Result{

    @Autowired
    private JlYjService jlYjSQL;

    @RequestMapping("/findList")
    public String findList(JlYjVO model,Integer pageSize, Integer pageNum){
        List<JlYjVO> list = jlYjSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlYjVO model, Integer pageSize, Integer pageNum){
        return jlYjSQL.findPojoJoin(model, pageSize, pageNum);
    }

    @RequestMapping("/findCount")
    public String findCount(JlYjVO model){
        Integer count = jlYjSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlYjVO model = jlYjSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlYjVO model){
    	JlYjVO jlYjQuery = new JlYjVO();
    	jlYjQuery.setYjNo(model.getYjNo());
    	int count = jlYjSQL.count(jlYjQuery);
    	if(count>0){
    		this.error(error_103, "警察编号已存在");
    		return this.toResult();
    	}
    	model.setJy("Server1");
        jlYjSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlYjVO model){
    	if(model.getWebid()!=null){
    		JlYjVO oldModel = jlYjSQL.findOne(model.getWebid());
    		if(oldModel.getYjNum()!=null && !oldModel.getYjNum().equals(model.getYjNum())){
    			JlYjVO jlYjQuery = new JlYjVO();
    			jlYjQuery.setYjNum(model.getYjNum());
    			int count = jlYjSQL.count(jlYjQuery);
    			if(count>0){
    				this.error(error_103, "警察警号已存在");
    	    		return this.toResult();
    			}
    		}
    	}
        jlYjSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlYjSQL.deleteKey(id);
        return this.toResult();
    }

}
