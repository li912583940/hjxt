package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.HjdjAcdInfo;
import com.sl.ue.service.hj.HjdjAcdInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdInfo")
public class HjdjAcdInfoWeb extends Result{

    @Autowired
    private HjdjAcdInfoService hjdjAcdInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdInfo model = new HjdjAcdInfo();
        List<HjdjAcdInfo> list = hjdjAcdInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdInfo model = hjdjAcdInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdInfo model){
        hjdjAcdInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdInfo model){
        hjdjAcdInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
