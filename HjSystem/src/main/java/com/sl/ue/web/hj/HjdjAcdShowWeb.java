package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.HjdjAcdShow;
import com.sl.ue.service.hj.HjdjAcdShowService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdShow")
public class HjdjAcdShowWeb extends Result{

    @Autowired
    private HjdjAcdShowService HjdjAcdShowSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdShow model = new HjdjAcdShow();
        List<HjdjAcdShow> list = HjdjAcdShowSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdShow model = HjdjAcdShowSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdShow model){
        HjdjAcdShowSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdShow model){
        HjdjAcdShowSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        HjdjAcdShowSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
