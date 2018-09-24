package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysHjServer")
public class SysHjServerWeb extends Result{

    @Autowired
    private SysHjServerService sysHjServerSQL;

    @RequestMapping("/findList")
    public String findList(SysHjServerVO model,Integer pageSize, Integer pageNum){
        List<SysHjServerVO> list = sysHjServerSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysHjServerVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysHjServerSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysHjServerVO model){
        Integer count = sysHjServerSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjServerVO model = sysHjServerSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjServerVO model){
        sysHjServerSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjServerVO model){
        sysHjServerSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysHjServerSQL.deleteKey(id);
        return this.toResult();
    }

}
