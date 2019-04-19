package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlJb")
public class JlJbWeb extends Result{

    @Autowired
    private JlJbService jlJbSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(JlJbVO model,Integer pageSize, Integer pageNum){
        List<JlJbVO> list = jlJbSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlJbVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlJbSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlJbVO model){
        Integer count = jlJbSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJbVO model = jlJbSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJbVO model, HttpServletRequest request){
    	//查看级别编号或者级别名称是否存在
    	if(StringUtils.isNotBlank(model.getJbNo())){
    		JlJbVO jlJbQuery = new JlJbVO();
    		jlJbQuery.setJbNo(model.getJbNo());
    		if(StringUtils.isNotBlank(model.getJbName())){
    			jlJbQuery.setLeftJoinWhere(" OR a.JB_Name='"+model.getJbName()+"'");
    		}
    		List<JlJbVO> jlJbList = jlJbSQL.findList(jlJbQuery);
    		if(jlJbList.size()>0){
    			this.error(error_102, "级别编号或者级别名称不能重复");
    			return this.toResult();
    		}
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加级别");
		sysLog.setInfo("新增级别编号: "+model.getJbNo()+"，级别名称: "+model.getJbName()+"。");
		sysLog.setModel("级别管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlJbSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJbVO model, HttpServletRequest request){
    	JlJbVO jlJbQuery = new JlJbVO();
    	jlJbQuery.setLeftJoinWhere(" AND a.WebID<>"+model.getWebid()+" AND (a.JB_No='"+model.getJbNo()+"'"
    			+" OR a.JB_Name='"+model.getJbName()+"')");
    	List<JlJbVO> list = jlJbSQL.findList(jlJbQuery);
    	if(list.size()>0){
    		this.error(error_102, "级别编号或者级别名称不能重复");
			return this.toResult();
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑级别");
		sysLog.setInfo("编辑级别编号: "+model.getJbNo()+"，级别名称: "+model.getJbName()+"。");
		sysLog.setModel("级别管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlJbSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id, HttpServletRequest request){
    	JlJbVO model = jlJbSQL.findOne(id);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("删除级别");
		sysLog.setInfo("删除级别编号: "+model.getJbNo()+"，级别名称: "+model.getJbName()+"。");
		sysLog.setModel("级别管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlJbSQL.deleteKey(id);
        return this.toResult();
    }

}
