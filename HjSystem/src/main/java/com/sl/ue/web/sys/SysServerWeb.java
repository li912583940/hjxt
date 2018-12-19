package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysServerVO;
import com.sl.ue.service.sys.SysServerService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysServer")
public class SysServerWeb extends Result{

    @Autowired
    private SysServerService sysServerSQL;

    @RequestMapping("/findList")
    public String findList(SysServerVO model,Integer pageSize, Integer pageNum){
        List<SysServerVO> list = sysServerSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysServerVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysServerSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysServerVO model){
        Integer count = sysServerSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysServerVO model = sysServerSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysServerVO model){
        sysServerSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysServerVO model){
        sysServerSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysServerSQL.deleteKey(id);
        return this.toResult();
    }

}
