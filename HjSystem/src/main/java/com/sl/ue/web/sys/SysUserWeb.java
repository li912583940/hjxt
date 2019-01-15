package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.other.vo.DeptVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.other.DeptService;
import com.sl.ue.service.sys.SysUserService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sysUser")
public class SysUserWeb extends Result{

    @Autowired
    private SysUserService sysUserSQL;
    @Autowired
    private DeptService deptSQL;
    
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
    public String add(SysUserVO model){
    	model.setUserPwd("123456");
    	if(model.getDeptId()!=null){
    		DeptVO dept = deptSQL.findOne(model.getDeptId());
    		if(dept!=null){
    			model.setDeptName(dept.getDeptName());
    		}
    	}
        sysUserSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysUserVO model){
    	if(model.getDeptId()!=null){
    		DeptVO dept = deptSQL.findOne(model.getDeptId());
    		if(dept!=null){
    			model.setDeptName(dept.getDeptName());
    		}
    	}
        sysUserSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
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
    public String addUserRole(Integer userId, String roles){
    	return sysUserSQL.addUserRole(userId, roles);
    }
}
