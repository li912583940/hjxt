package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysHjLine")
public class SysHjLineWeb extends Result{

    @Autowired
    private SysHjLineService sysHjLineSQL;

    @RequestMapping("/findList")
    public String findList(SysHjLineVO model,Integer pageSize, Integer pageNum){
        List<SysHjLineVO> list = sysHjLineSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysHjLineVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysHjLineSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysHjLineVO model){
        Integer count = sysHjLineSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjLineVO model = sysHjLineSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjLineVO model){
        sysHjLineSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjLineVO model){
        sysHjLineSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysHjLineSQL.deleteKey(id);
        return this.toResult();
    }

}
