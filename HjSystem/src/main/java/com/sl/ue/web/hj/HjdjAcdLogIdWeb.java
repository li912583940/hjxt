package com.sl.ue.web.hj;

import java.util.List;
import java.util.Map;

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
    public String findList(HjdjAcdLogIdVO model,Integer pageSize, Integer pageNum){
        List<HjdjAcdLogIdVO> list = hjdjAcdLogIdSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HjdjAcdLogIdVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = hjdjAcdLogIdSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HjdjAcdLogIdVO model){
        Integer count = hjdjAcdLogIdSQL.count(model);
        this.putJson("count", count);
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
