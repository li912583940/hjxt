package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlFr")
public class JlFrWeb extends Result{

    @Autowired
    private JlFrService jlFrSQL;

    @RequestMapping("/findList")
    public String findList(JlFrVO model,Integer pageSize, Integer pageNum){
        List<JlFrVO> list = jlFrSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlFrVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFrSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlFrVO model){
        Integer count = jlFrSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrVO model = jlFrSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFrVO model){
        jlFrSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFrVO model){
        jlFrSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlFrSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [导出excel]
     * L_晓天  @2018年11月30日
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(JlFrVO model,
    		HttpServletRequest request, HttpServletResponse response) {
    	jlFrSQL.exportExcel(model, request, response);
    }
}
