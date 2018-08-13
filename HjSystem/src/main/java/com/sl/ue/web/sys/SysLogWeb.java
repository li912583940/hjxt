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
    private SysLogService SysLogSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysLog model = new SysLog();
        List<SysLog> list = SysLogSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysLog model = SysLogSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysLog model){
        SysLogSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysLog model){
        SysLogSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        SysLogSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
