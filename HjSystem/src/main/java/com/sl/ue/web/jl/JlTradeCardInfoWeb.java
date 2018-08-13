package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlTradeCardInfo;
import com.sl.ue.service.jl.JlTradeCardInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlTradeCardInfo")
public class JlTradeCardInfoWeb extends Result{

    @Autowired
    private JlTradeCardInfoService JlTradeCardInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlTradeCardInfo model = new JlTradeCardInfo();
        List<JlTradeCardInfo> list = JlTradeCardInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlTradeCardInfo model = JlTradeCardInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlTradeCardInfo model){
        JlTradeCardInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlTradeCardInfo model){
        JlTradeCardInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlTradeCardInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
