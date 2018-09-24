package com.sl.ue.web.hj;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjAcdWindowsInfoVO;
import com.sl.ue.service.hj.HjdjAcdWindowsInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdWindowsInfo")
public class HjdjAcdWindowsInfoWeb extends Result{

    @Autowired
    private HjdjAcdWindowsInfoService hjdjAcdWindowsInfoSQL;

    @RequestMapping("/findList")
    public String findList(HjdjAcdWindowsInfoVO model,Integer pageSize, Integer pageNum){
        List<HjdjAcdWindowsInfoVO> list = hjdjAcdWindowsInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HjdjAcdWindowsInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = hjdjAcdWindowsInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HjdjAcdWindowsInfoVO model){
        Integer count = hjdjAcdWindowsInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdWindowsInfoVO model = hjdjAcdWindowsInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdWindowsInfoVO model){
        hjdjAcdWindowsInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdWindowsInfoVO model){
        hjdjAcdWindowsInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdWindowsInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
