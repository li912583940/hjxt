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
    	StringBuffer leftJoinWhere = new StringBuffer();
    	if(StringUtils.isNotBlank(model.getYjNo())){
    		String str = model.getYjNo();
    		leftJoinWhere.append(" AND a.YJ_No LIKE '%"+str+"%' ");
    		model.setYjNo(null);
    	}
    	if(StringUtils.isNotBlank(model.getYjName())){
    		String str = model.getYjName();
    		leftJoinWhere.append(" AND a.YJ_Name LIKE '%"+str+"%' ");
    		model.setYjName(null);
    	}
    	model.setLeftJoinWhere(leftJoinWhere.toString());
        Map<String, Object> map = jlYjSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
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
    		this.error(error_103, "狱警编号已存在");
    		return this.toResult();
    	}
    	model.setJy("");
    	model.setJq("");
        jlYjSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlYjVO model){
        jlYjSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlYjSQL.deleteKey(id);
        return this.toResult();
    }

}
