package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysHjVideoVO;
import com.sl.ue.service.sys.SysHjVideoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysHjVideo")
public class SysHjVideoWeb extends Result{

    @Autowired
    private SysHjVideoService sysHjVideoSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysHjVideoVO model = new SysHjVideoVO();
        List<SysHjVideoVO> list = sysHjVideoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjVideoVO model = sysHjVideoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjVideoVO model){
        sysHjVideoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjVideoVO model){
        sysHjVideoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysHjVideoSQL.deleteKey(id);
        return this.toResult();
    }

}
