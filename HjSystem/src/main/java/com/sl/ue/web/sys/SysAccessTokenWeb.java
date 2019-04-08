package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysAccessTokenVO;
import com.sl.ue.service.sys.SysAccessTokenService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysAccessToken")
public class SysAccessTokenWeb extends Result{

    @Autowired
    private SysAccessTokenService sysAccessTokenSQL;

    @RequestMapping("/findList")
    public String findList(SysAccessTokenVO model,Integer pageSize, Integer pageNum){
        List<SysAccessTokenVO> list = sysAccessTokenSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysAccessTokenVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysAccessTokenSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysAccessTokenVO model){
        Integer count = sysAccessTokenSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysAccessTokenVO model = sysAccessTokenSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysAccessTokenVO model){
        sysAccessTokenSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysAccessTokenVO model){
        sysAccessTokenSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysAccessTokenSQL.deleteKey(id);
        return this.toResult();
    }

}
