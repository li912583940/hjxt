package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.service.sys.SysNoticeConfService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysNoticeConf")
public class SysNoticeConfWeb extends Result{

    @Autowired
    private SysNoticeConfService sysNoticeConfSQL;

    @RequestMapping("/findList")
    public String findList(SysNoticeConfVO model,Integer pageSize, Integer pageNum){
        List<SysNoticeConfVO> list = sysNoticeConfSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysNoticeConfVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysNoticeConfSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysNoticeConfVO model){
        Integer count = sysNoticeConfSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysNoticeConfVO model = sysNoticeConfSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysNoticeConfVO model){
        sysNoticeConfSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysNoticeConfVO model){
        sysNoticeConfSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysNoticeConfSQL.deleteKey(id);
        return this.toResult();
    }

}
