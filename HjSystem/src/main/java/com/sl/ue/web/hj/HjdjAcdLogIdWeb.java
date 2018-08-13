package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.HjdjAcdLogId;
import com.sl.ue.service.hj.HjdjAcdLogIdService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdLogId")
public class HjdjAcdLogIdWeb extends Result{

    @Autowired
    private HjdjAcdLogIdService HjdjAcdLogIdSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdLogId model = new HjdjAcdLogId();
        List<HjdjAcdLogId> list = HjdjAcdLogIdSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdLogId model = HjdjAcdLogIdSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdLogId model){
        HjdjAcdLogIdSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdLogId model){
        HjdjAcdLogIdSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        HjdjAcdLogIdSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
