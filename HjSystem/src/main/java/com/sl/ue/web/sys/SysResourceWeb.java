package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

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
    public String findList(SysResourceVO model,Integer pageSize, Integer pageNum){
        List<SysResourceVO> list = sysResourceSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysResourceVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysResourceSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysResourceVO model){
        Integer count = sysResourceSQL.count(model);
        this.putJson("count", count);
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

    /**
     * 说明 [获取目录树形结构]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getMenuTree")
    public String getMenuTree(){
    	return sysResourceSQL.getMenuTree();
    }
    
    /**
     * 说明 [获取当前角色选中的目录 数组格式]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getCheckedMenu")
    public String getCheckedMenu(Integer roleId){
    	if(roleId == null){
    		this.error(error_102);
    		return this.toResult();
    	}
    	return sysResourceSQL.getCheckedMenu(roleId);
    }
    /**
     * 说明 [获取监区树形结构]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getJqTree")
    public String getJqTree(){
    	return sysResourceSQL.getJqTree();
    }
    
    /**
     * 说明 [获取当前角色选中的监区 数组格式]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getCheckedJq")
    public String getCheckedJq(Integer roleId){
    	return sysResourceSQL.getCheckedJq(roleId);
    }
}
