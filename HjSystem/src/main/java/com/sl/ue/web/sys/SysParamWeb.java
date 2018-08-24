package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysParamVO;
import com.sl.ue.service.sys.SysParamService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysParam")
public class SysParamWeb extends Result{

    @Autowired
    private SysParamService sysParamSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysParamVO model = new SysParamVO();
        List<SysParamVO> list = sysParamSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysParamVO model = sysParamSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysParamVO model){
        sysParamSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysParamVO model){
        sysParamSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysParamSQL.deleteKey(id);
        return this.toResult();
    }

}
