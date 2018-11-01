package com.sl.ue.web.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.service.sys.SysRoleService;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/sysRole")
public class SysRoleWeb extends Result{

    @Autowired
    private SysRoleService sysRoleSQL;

    @RequestMapping("/findList")
    public String findList(SysRoleVO model,Integer pageSize, Integer pageNum){
        List<SysRoleVO> list = sysRoleSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysRoleVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysRoleSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysRoleVO model){
        Integer count = sysRoleSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysRoleVO model = sysRoleSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysRoleVO model){
    	model.setCreateTime(new Date());
    	model.setCreateUserId(TokenUser.getUser().getWebId());
        sysRoleSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysRoleVO model){
    	model.setCreateTime(new Date());
    	model.setCreateUserId(TokenUser.getUser().getWebId());
        sysRoleSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysRoleSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [获取目录树形结构]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getMenuTree")
    public String getMenuTree(){
    	return sysRoleSQL.getMenuTree();
    }
    
    /**
     * 说明 [获取当前角色选中的目录 数组格式]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getCheckedMenu")
    public String getCheckedMenu(Integer roleId){
    	return sysRoleSQL.getCheckedMenu(roleId);
    }
    /**
     * 说明 [获取监区树形结构]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getJqTree")
    public String getJqTree(){
    	return sysRoleSQL.getJqTree();
    }
    
    /**
     * 说明 [获取当前角色选中的监区 数组格式]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getCheckedJq")
    public String getCheckedJq(Integer roleId){
    	return sysRoleSQL.getCheckedJq(roleId);
    }
    
    /**
     * 说明 [为当前角色配置目录权限]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/addRoleMenu")
    public String addRoleMenu(Integer roleId, String menus){
    	return sysRoleSQL.addRoleMenu(roleId, menus);
    }
    
    /**
     * 说明 [为当前角色配置监区权限]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/addRoleJq")
    public String addRoleJq(Integer roleId, String jqs){
    	return sysRoleSQL.addRoleJq(roleId, jqs);
    }
    
    /**
     * 说明 [获取当前角色的用户列表]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/getCheckedUser")
    public String getCheckedUser(Integer roleId){
    	return sysRoleSQL.getCheckedUser(roleId);
    }
    
    /**
     * 说明 [为当前角色添加用户]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/addRoleUser")
    public String addRoleUser(Integer roleId, String users){
    	return sysRoleSQL.addRoleUser(roleId, users);
    }
}
