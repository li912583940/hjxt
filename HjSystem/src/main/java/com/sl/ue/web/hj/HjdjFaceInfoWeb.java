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
    private HjdjFaceInfoService HjdjFaceInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjFaceInfo model = new HjdjFaceInfo();
        List<HjdjFaceInfo> list = HjdjFaceInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjFaceInfo model = HjdjFaceInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(HjdjFaceInfo model){
        HjdjFaceInfoSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(HjdjFaceInfo model){
        HjdjFaceInfoSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        HjdjFaceInfoSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
