package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysLog")
public class SysLogWeb extends Result{

    @Autowired
    private SysLogService sysLogSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysLogVO model = new SysLogVO();
        List<SysLogVO> list = sysLogSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysLogVO model = sysLogSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysLogVO model){
        sysLogSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysLogVO model){
        sysLogSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysLogSQL.deleteKey(id);
        return this.toResult();
    }

}
