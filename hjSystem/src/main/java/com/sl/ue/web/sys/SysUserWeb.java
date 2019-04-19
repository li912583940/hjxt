package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.other.vo.DeptVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserRoleVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.other.DeptService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.service.sys.SysUserRoleService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/sysUser")
public class SysUserWeb extends Result{

	@Autowired
	private SysUserRoleService sysUserRoleSQL;
    @Autowired
    private SysUserService sysUserSQL;
    @Autowired
    private DeptService deptSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(SysUserVO model,Integer pageSize, Integer pageNum){
        List<SysUserVO> list = sysUserSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysUserVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysUserSQL.findPojoJoin(model, pageSize, pageNum);
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
    public String add(SysUserVO model, HttpServletRequest request){
    	model.setUserPwd("123456");
    	if(model.getDeptId()!=null){
    		DeptVO dept = deptSQL.findOne(model.getDeptId());
    		if(dept!=null){
    			model.setDeptName(dept.getDeptName());
    		}
    	}
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加用户");
		sysLog.setInfo("新增用户编号: "+model.getUserNo()+"，用户姓名: "+model.getUserName()+"。");
		sysLog.setModel("用户管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        sysUserSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserVO model, HttpServletRequest request){
    	if(model.getDeptId()!=null){
    		DeptVO dept = deptSQL.findOne(model.getDeptId());
    		if(dept!=null){
    			model.setDeptName(dept.getDeptName());
    		}
    	}
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑用户");
		sysLog.setInfo("编辑用户编号: "+model.getUserNo()+"，用户姓名: "+model.getUserName()+"。");
		sysLog.setModel("用户管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        sysUserSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id, HttpServletRequest request){
    	SysUserVO  sysUser =sysUserSQL.findOne(id);
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("删除用户");
		sysLog.setInfo("删除用户编号: "+sysUser.getUserNo()+"，用户姓名: "+sysUser.getUserName()+"。");
		sysLog.setModel("用户管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
		//删除用户相关的权限
		SysUserRoleVO sysUserRole = new SysUserRoleVO();
		sysUserRole.setUserId(id);
		sysUserRoleSQL.delete(sysUserRole);
				
        sysUserSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [获取目录资源]
     * L_晓天  @2018年10月30日
     */
    @RequestMapping("/getRoles")
    public String getRoles(String token){
    	return sysUserSQL.getRoles(token);
    }
    
    /**
     * 说明 [获取当前用户的角色列表]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/getCheckedRole")
    public String getCheckedRole(Integer userId){
    	return sysUserSQL.getCheckedRole(userId);
    }
    
    /**
     * 说明 [为当前用户添加角色]
     * L_晓天  @2018年11月1日
     */
    @RequestMapping("/addUserRole")
    public String addUserRole(Integer userId, String roles, HttpServletRequest request){
    	SysUserVO  sysUser =sysUserSQL.findOne(userId);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("为用户添加角色权限");
		sysLog.setInfo("为用户编号: "+sysUser.getUserNo()+"，用户姓名: "+sysUser.getUserName()+"添加角色权限。");
		sysLog.setModel("用户管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
    	return sysUserSQL.addUserRole(userId, roles);
    }
    
    @RequestMapping("/resetPassword")
	public String resetPassword(Integer id, HttpServletRequest request){
		if(id == null){
			this.error(error_102);
			return this.toResult();
		}
		SysUserVO sysUser =  sysUserSQL.findOne(id);
		if(sysUser != null){
			
	    	SysUserVO user = TokenUser.getUser();
	    	SysLogVO sysLog = new SysLogVO();
	    	sysLog.setType("严重");
			sysLog.setOp("为用户重置密码");
			sysLog.setInfo("为用户编号: "+sysUser.getUserNo()+"，用户姓名: "+sysUser.getUserName()+"重置密码。");
			sysLog.setModel("用户管理");
			sysLog.setUserNo(user.getUserNo());
			sysLog.setUserName(user.getUserName());
			sysLog.setLogTime(DateUtil.getDefaultNow());
			sysLog.setUserIp(request.getRemoteAddr());
			sysLogSQL.add(sysLog);
			
			sysUser.setUserPwd("123456");
			sysUserSQL.edit(sysUser);
		}else{
			this.error(error_103, "查询不到用户信息，密码重置失败。");
		}
		return this.toResult();
	}
}
