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
    private HjdjAcdWindowsInfoService hjdjAcdWindowsInfoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdWindowsInfo model = new HjdjAcdWindowsInfo();
        List<HjdjAcdWindowsInfo> list = hjdjAcdWindowsInfoSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdWindowsInfo model = hjdjAcdWindowsInfoSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdWindowsInfo model){
        hjdjAcdWindowsInfoSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdWindowsInfo model){
        hjdjAcdWindowsInfoSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdWindowsInfoSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
