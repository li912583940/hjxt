package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

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
    public String findList(SysHjVideoVO model,Integer pageSize, Integer pageNum){
        List<SysHjVideoVO> list = sysHjVideoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysHjVideoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysHjVideoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysHjVideoVO model){
        Integer count = sysHjVideoSQL.count(model);
        this.putJson("count", count);
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
