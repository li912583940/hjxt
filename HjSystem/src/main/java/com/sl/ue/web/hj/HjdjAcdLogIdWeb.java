package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjAcdLogIdVO;
import com.sl.ue.service.hj.HjdjAcdLogIdService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdLogId")
public class HjdjAcdLogIdWeb extends Result{

    @Autowired
    private HjdjAcdLogIdService hjdjAcdLogIdSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdLogIdVO model = new HjdjAcdLogIdVO();
        List<HjdjAcdLogIdVO> list = hjdjAcdLogIdSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdLogIdVO model = hjdjAcdLogIdSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdLogIdVO model){
        hjdjAcdLogIdSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdLogIdVO model){
        hjdjAcdLogIdSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdLogIdSQL.deleteKey(id);
        return this.toResult();
    }

}
