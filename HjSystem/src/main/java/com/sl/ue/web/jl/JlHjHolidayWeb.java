package com.sl.ue.web.jl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjHolidayVO;
import com.sl.ue.entity.jl.vo.JlHjJqHolidayVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.jl.JlHjHolidayService;
import com.sl.ue.service.jl.JlHjJqHolidayService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlHjHoliday")
public class JlHjHolidayWeb extends Result{

    @Autowired
    private JlHjHolidayService jlHjHolidaySQL;
    @Autowired
    private JlHjJqHolidayService jlHjJqHolidaySQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(JlHjHolidayVO model,Integer pageSize, Integer pageNum){
        List<JlHjHolidayVO> list = jlHjHolidaySQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjHolidayVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjHolidaySQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjHolidayVO model){
        Integer count = jlHjHolidaySQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(String holiday){
        JlHjHolidayVO model = jlHjHolidaySQL.findOne(holiday);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(String holidays, HttpServletRequest request){
    	if(StringUtils.isBlank(holidays)){
    		this.error(error_102);
    		return this.toResult();
    	}
    	for(String holiday : holidays.split(",")){
    		JlHjHolidayVO model = new JlHjHolidayVO();
    		model.setHoliday(holiday);
    		int count = jlHjHolidaySQL.count(model);
    		if(count<=0){
    			jlHjHolidaySQL.add(model);
    		}
    	}
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加特殊会见日");
		sysLog.setInfo("添加特殊会见日: "+holidays+"。");
		sysLog.setModel("特殊会见日");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjHolidayVO model, HttpServletRequest request){
        jlHjHolidaySQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(String holiday, HttpServletRequest request){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("删除特殊会见日");
		sysLog.setInfo("删除特殊会见日: "+holiday+"。");
		sysLog.setModel("特殊会见日");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        jlHjHolidaySQL.deleteKey(holiday);
        return this.toResult();
    }
    
    @RequestMapping("/getCheckedJq")
    public String getCheckedJq(){
    	List<JlHjJqHolidayVO> list = jlHjJqHolidaySQL.findList(new JlHjJqHolidayVO());
    	List<String> res = new ArrayList<String>();
    	for(JlHjJqHolidayVO t : list){
    		res.add(t.getJqNo());
    	}
    	this.putJson(res);
    	return this.toResult();
    }
    
    @RequestMapping("/addJqHoliday")
    public String addJqHoliday(String jqValues, HttpServletRequest request){
    	if(StringUtils.isBlank(jqValues)){
    		this.error(error_102);
    		return this.toResult();
    	}
    	jlHjJqHolidaySQL.delete(new JlHjJqHolidayVO());
    	for(String jqNo : jqValues.split(",")){
    		JlHjJqHolidayVO model = new JlHjJqHolidayVO();
    		model.setJqNo(jqNo);
    		jlHjJqHolidaySQL.add(model);
    	}
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("配置监区");
		sysLog.setInfo("配置监区: "+jqValues+"。");
		sysLog.setModel("特殊会见日");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
    	return this.toResult();
    
    }
    @RequestMapping("/emptyDate")
    public String emptyDate(HttpServletRequest request){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("清空特殊会见日");
		sysLog.setInfo("清空特殊会见日 。");
		sysLog.setModel("特殊会见日");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
    	jlHjHolidaySQL.delete(new JlHjHolidayVO());
    	return this.toResult();
    }
}
