package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlJq")
public class JlJqWeb extends Result{

    @Autowired
    private JlJqService jlJqSQL;
    @Autowired
	private SysLogService sysLogSQL;
    @Autowired
    private SysHjServerService sysHjServerSQL;

    @RequestMapping("/findList")
    public String findList(JlJqVO model,Integer pageSize, Integer pageNum){
        List<JlJqVO> list = jlJqSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlJqVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlJqSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlJqVO model){
        Integer count = jlJqSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJqVO model = jlJqSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJqVO model, HttpServletRequest request){
    	//查看监区编号或者监区名称是否存在
    	if(StringUtils.isNotBlank(model.getJqNo())){
    		JlJqVO jlJqQuery = new JlJqVO();
    		jlJqQuery.setJqNo(model.getJqNo());
    		if(StringUtils.isNotBlank(model.getJqName())){
    			jlJqQuery.setLeftJoinWhere(" OR a.JQ_Name='"+model.getJqName()+"'");
    		}
    		List<JlJqVO> jlJqList = jlJqSQL.findList(jlJqQuery);
    		if(jlJqList.size()>0){
    			this.error(error_102, "监区编号或监区名称不能重复");
    			return this.toResult();
    		}
    	}
    	
    	SysHjServerVO sysHjServer = new SysHjServerVO();
    	List<SysHjServerVO> list = sysHjServerSQL.findList(sysHjServer);
    	if(list.size()>0){
    		model.setJy(list.get(0).getServerName());
    	}else{
    		model.setJy("Server1");
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加监区");
		sysLog.setInfo("添加监区编号: "+model.getJqNo()+"，监区名称: "+model.getJqName()+"。");
		sysLog.setModel("监区设置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlJqSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJqVO model, HttpServletRequest request){
    	JlJqVO jlJqQuery = new JlJqVO();
    	jlJqQuery.setLeftJoinWhere(" AND a.WebID<>"+model.getWebid()+" AND (a.JQ_No='"+model.getJqNo()+"'"
    			+" OR a.JQ_Name='"+model.getJqName()+"')");
    	List<JlJqVO> list = jlJqSQL.findList(jlJqQuery);
    	if(list.size()>0){
    		this.error(error_102, "监区编号或监区名称不能重复");
			return this.toResult();
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑监区");
		sysLog.setInfo("编辑监区编号: "+model.getJqNo()+"，监区名称: "+model.getJqName()+"。");
		sysLog.setModel("监区设置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlJqSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id, HttpServletRequest request){
    	JlJqVO model = jlJqSQL.findOne(id);
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("删除监区");
		sysLog.setInfo("删除监区编号: "+model.getJqNo()+"，监区名称: "+model.getJqName()+"。");
		sysLog.setModel("监区设置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlJqSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [获取当前监区选中的会见星期日]
     * L_晓天  @2018年11月2日
     */
    @RequestMapping("/getCheckedWeek")
    public String getCheckedWeek(String jqNo){
    	return jlJqSQL.getCheckedWeek(jqNo);
    }
    
    /**
     * 说明 [提交当前监区的会见星期日]
     * L_晓天  @2018年11月2日
     */
    @RequestMapping("/addJqWeek")
    public String addJqWeek(String jqNo, String weeks, HttpServletRequest request){
    	JlJqVO model = new JlJqVO();
    	model.setJqNo(jqNo);
    	List<JlJqVO> list = jlJqSQL.findList(model);
    	if(list.size()>0){
    		model = list.get(0);
    	}
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("删除监区");
		sysLog.setInfo("删除监区编号: "+model.getJqNo()+"，监区名称: "+model.getJqName()+"。");
		sysLog.setModel("监区设置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
    	return jlJqSQL.addJqWeek(jqNo, weeks);
    }

    /**
     * 说明 [提交所有监区的会见星期日]
     * @return
     * L_晓天  @2019年4月11日
     */
    @RequestMapping("/addJqAllWeek")
    public String addJqAllWeek(String weeks){
    	return jlJqSQL.addJqAllWeek(weeks);
    }
}
