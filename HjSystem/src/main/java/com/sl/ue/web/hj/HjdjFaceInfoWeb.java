package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.HjdjFaceInfo;
import com.sl.ue.service.hj.HjdjFaceInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjFaceInfo")
public class HjdjFaceInfoWeb extends Result{

    @Autowired
    private HjdjFaceInfoService hjdjFaceInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjFaceInfo model = new HjdjFaceInfo();
        List<HjdjFaceInfo> list = hjdjFaceInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjFaceInfo model = hjdjFaceInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjFaceInfo model){
        hjdjFaceInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjFaceInfo model){
        hjdjFaceInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjFaceInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
