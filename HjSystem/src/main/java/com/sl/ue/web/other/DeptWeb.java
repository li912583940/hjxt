package com.sl.ue.web.other;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsGxVO;
import com.sl.ue.entity.other.vo.DeptVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.other.DeptService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/dept")
public class DeptWeb extends Result{

    @Autowired
    private DeptService deptSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(DeptVO model,Integer pageSize, Integer pageNum){
        List<DeptVO> list = deptSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(DeptVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = deptSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(DeptVO model){
        Integer count = deptSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        DeptVO model = deptSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(DeptVO model, HttpServletRequest request){
    	DeptVO deptQuery = new DeptVO();
    	deptQuery.setDeptName(model.getDeptName());
    	Integer count = deptSQL.count(deptQuery);
    	if(count > 0){
    		this.error(error_103, "当前部门名称<"+model.getDeptName()+">已存在");
    		return this.toResult();
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加部门");
		sysLog.setInfo("添加部门: 部门名称"+model.getDeptName()+"。");
		sysLog.setModel("部门管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        deptSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(DeptVO model, HttpServletRequest request){
    	DeptVO oldDept = deptSQL.findOne(model.getId());
    	if(!oldDept.getDeptName().equals(model.getDeptName())){
    		DeptVO deptQuery = new DeptVO();
        	deptQuery.setDeptName(model.getDeptName());
        	Integer count = deptSQL.count(deptQuery);
        	if(count > 0){
        		this.error(error_103, "当前部门名称<"+model.getDeptName()+">已存在");
        		return this.toResult();
        	}
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑部门");
		sysLog.setInfo("编辑部门: 部门名称"+model.getDeptName()+"。");
		sysLog.setModel("部门管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        deptSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id, HttpServletRequest request){
    	DeptVO model = deptSQL.findOne(id);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("删除部门");
		sysLog.setInfo("删除部门: 部门名称"+model.getDeptName()+"。");
		sysLog.setModel("部门管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        deptSQL.deleteKey(id);
        return this.toResult();
    }

}
