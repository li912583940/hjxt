package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlTradeCardInfoVO;
import com.sl.ue.service.jl.JlTradeCardInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlTradeCardInfo")
public class JlTradeCardInfoWeb extends Result{

    @Autowired
    private JlTradeCardInfoService jlTradeCardInfoSQL;

    @RequestMapping("/findList")
    public String findList(JlTradeCardInfoVO model,Integer pageSize, Integer pageNum){
        List<JlTradeCardInfoVO> list = jlTradeCardInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlTradeCardInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlTradeCardInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlTradeCardInfoVO model){
        Integer count = jlTradeCardInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlTradeCardInfoVO model = jlTradeCardInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlTradeCardInfoVO model){
        jlTradeCardInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlTradeCardInfoVO model){
        jlTradeCardInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlTradeCardInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
