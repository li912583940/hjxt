package com.sl.ue.web.hj;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjFaceInfoVO;
import com.sl.ue.service.hj.HjdjFaceInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjFaceInfo")
public class HjdjFaceInfoWeb extends Result{

    @Autowired
    private HjdjFaceInfoService hjdjFaceInfoSQL;

    @RequestMapping("/findList")
    public String findList(HjdjFaceInfoVO model,Integer pageSize, Integer pageNum){
        List<HjdjFaceInfoVO> list = hjdjFaceInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HjdjFaceInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = hjdjFaceInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HjdjFaceInfoVO model){
        Integer count = hjdjFaceInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjFaceInfoVO model = hjdjFaceInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjFaceInfoVO model){
        hjdjFaceInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjFaceInfoVO model){
        hjdjFaceInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjFaceInfoSQL.deleteKey(id);
        return this.toResult();
    }

}
