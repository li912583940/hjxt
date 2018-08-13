package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.HjdjAcdLog;
import com.sl.ue.service.hj.HjdjAcdLogService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdLog")
public class HjdjAcdLogWeb extends Result{

    @Autowired
    private HjdjAcdLogService HjdjAcdLogSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdLog model = new HjdjAcdLog();
        List<HjdjAcdLog> list = HjdjAcdLogSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdLog model = HjdjAcdLogSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdLog model){
        HjdjAcdLogSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdLog model){
        HjdjAcdLogSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        HjdjAcdLogSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
