package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUser")
public class SysUserWeb extends Result{

    @Autowired
    private SysUserService sysUserSQL;

    @RequestMapping("/findList")
    public String findList(SysUserVO model,Integer pageSize, Integer pageNum){
        List<SysUserVO> list = sysUserSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysUserVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysUserSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysUserVO model){
        Integer count = sysUserSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysUserVO model = sysUserSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysUserVO model){
        sysUserSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserVO model){
        sysUserSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysUserSQL.deleteKey(id);
        return this.toResult();
    }

    @RequestMapping("/getRoles")
    public String getRoles(String token){
    	List<String> roles = sysUserSQL.getRoles(token);
    	this.putJson(roles);
    	return this.toResult();
    }
}
