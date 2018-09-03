package com.sl.ue.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysResourceVO;
import com.sl.ue.service.sys.SysResourceService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysResource")
public class SysResourceWeb extends Result{

    @Autowired
    private SysResourceService sysResourceSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        SysResourceVO model = new SysResourceVO();
        List<SysResourceVO> list = sysResourceSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysResourceVO model = sysResourceSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysResourceVO model){
        sysResourceSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysResourceVO model){
        sysResourceSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysResourceSQL.deleteKey(id);
        return this.toResult();
    }

}
