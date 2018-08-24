package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.SysLog;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysLog")
public class SysLogWeb extends Result{

    @Autowired
    private SysLogService sysLogSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysLog model = new SysLog();
        List<SysLog> list = sysLogSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysLog model = sysLogSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysLog model){
        sysLogSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysLog model){
        sysLogSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysLogSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
