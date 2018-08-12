package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.HjdjAcdWindowsInfo;
import com.sl.ue.service.hj.HjdjAcdWindowsInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdWindowsInfo")
public class HjdjAcdWindowsInfoWeb extends Result{

    @Autowired
    private HjdjAcdWindowsInfoService HjdjAcdWindowsInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdWindowsInfo model = new HjdjAcdWindowsInfo();
        List<HjdjAcdWindowsInfo> list = HjdjAcdWindowsInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toString();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdWindowsInfo model = HjdjAcdWindowsInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toString();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdWindowsInfo model){
        HjdjAcdWindowsInfoSQL.baseAdd(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdWindowsInfo model){
        HjdjAcdWindowsInfoSQL.baseEdit(model);
        succes();
        return this.toString();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        HjdjAcdWindowsInfoSQL.baseDeleteKey(id);
        succes();
        return this.toString();
    }

}
