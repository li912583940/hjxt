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
    private HjdjAcdInfoService HjdjAcdInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdInfo model = new HjdjAcdInfo();
        List<HjdjAcdInfo> list = HjdjAcdInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdInfo model = HjdjAcdInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdInfo model){
        HjdjAcdInfoSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdInfo model){
        HjdjAcdInfoSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        HjdjAcdInfoSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
