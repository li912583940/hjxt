package com.sl.ue.web.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysRoleVO;
import com.sl.ue.entity.sys.vo.SysUserRoleVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.service.sys.SysRoleService;
import com.sl.ue.service.sys.SysUserRoleService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/sysRole")
public class SysRoleWeb extends Result{
	@Autowired
	private SysUserRoleService sysUserRoleSQL;
    @Autowired
    private SysRoleService sysRoleSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
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
    public String add(SysRoleVO model, HttpServletRequest request){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加角色权限");
		sysLog.setInfo("新增角色名称: "+model.getName()+"。");
		sysLog.setModel("权限配置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
    	model.setCreateTime(new Date());
    	model.setCreateUserId(TokenUser.getUser().getWebid());
        sysRoleSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysRoleVO model, HttpServletRequest request){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑角色权限");
		sysLog.setInfo("编辑角色名称: "+model.getName()+"。");
		sysLog.setModel("权限配置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
    	model.setCreateTime(new Date());
    	model.setCreateUserId(TokenUser.getUser().getWebid());
        sysRoleSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id, HttpServletRequest request){
    	SysRoleVO sysRole = sysRoleSQL.findOne(id);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("删除角色权限");
		sysLog.setInfo("删除角色名称: "+sysRole.getName()+"。");
		sysLog.setModel("权限配置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
		//删除权限相关的用户
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setRoleId(id);
		sysUserRoleSQL.delete(sysUserRole);
		
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
    public String addRoleMenu(Integer roleId, String menus, HttpServletRequest request){
    	SysRoleVO sysRole =sysRoleSQL.findOne(roleId);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("为角色添加目录权限");
		sysLog.setInfo("为角色名称: "+sysRole.getName()+"添加目录权限。");
		sysLog.setModel("权限管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
    	return sysRoleSQL.addRoleMenu(roleId, menus);
    }
    
    /**
     * 说明 [为当前角色配置监区权限]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/addRoleJq")
    public String addRoleJq(Integer roleId, String jqs, HttpServletRequest request){
    	SysRoleVO sysRole =sysRoleSQL.findOne(roleId);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("为角色添加监区权限");
		sysLog.setInfo("为角色名称: "+sysRole.getName()+"添加监区权限。");
		sysLog.setModel("权限管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
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
    public String addRoleUser(Integer roleId, String users, HttpServletRequest request){
    	SysRoleVO sysRole =sysRoleSQL.findOne(roleId);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("为角色添加用户");
		sysLog.setInfo("为角色名称: "+sysRole.getName()+"添加用户。");
		sysLog.setModel("权限管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
    	return sysRoleSQL.addRoleUser(roleId, users);
    }
}
