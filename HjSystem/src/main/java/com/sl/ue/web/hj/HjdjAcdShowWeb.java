package com.sl.ue.web.hj;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjAcdShowVO;
import com.sl.ue.service.hj.HjdjAcdShowService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdShow")
public class HjdjAcdShowWeb extends Result{

    @Autowired
    private HjdjAcdShowService hjdjAcdShowSQL;

    @RequestMapping("/findList")
    public String findList(HjdjAcdShowVO model,Integer pageSize, Integer pageNum){
        List<HjdjAcdShowVO> list = hjdjAcdShowSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HjdjAcdShowVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = hjdjAcdShowSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HjdjAcdShowVO model){
        Integer count = hjdjAcdShowSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdShowVO model = hjdjAcdShowSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdShowVO model){
        hjdjAcdShowSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdShowVO model){
        hjdjAcdShowSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdShowSQL.deleteKey(id);
        return this.toResult();
    }

}
