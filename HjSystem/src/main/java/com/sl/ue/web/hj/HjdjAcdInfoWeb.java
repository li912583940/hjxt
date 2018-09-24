package com.sl.ue.web.hj;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjAcdInfoVO;
import com.sl.ue.service.hj.HjdjAcdInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdInfo")
public class HjdjAcdInfoWeb extends Result{

    @Autowired
    private HjdjAcdInfoService hjdjAcdInfoSQL;

    @RequestMapping("/findList")
    public String findList(HjdjAcdInfoVO model,Integer pageSize, Integer pageNum){
        List<HjdjAcdInfoVO> list = hjdjAcdInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HjdjAcdInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = hjdjAcdInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HjdjAcdInfoVO model){
        Integer count = hjdjAcdInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdInfoVO model = hjdjAcdInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdInfoVO model){
        hjdjAcdInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdInfoVO model){
        hjdjAcdInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
