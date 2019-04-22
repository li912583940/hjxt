package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.sys.SysHjLineService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/sysHjLine")
public class SysHjLineWeb extends Result{

    @Autowired
    private SysHjLineService sysHjLineSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(SysHjLineVO model,Integer pageSize, Integer pageNum){
        List<SysHjLineVO> list = sysHjLineSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysHjLineVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysHjLineSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysHjLineVO model){
        Integer count = sysHjLineSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjLineVO model = sysHjLineSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjLineVO model){
        sysHjLineSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjLineVO model, HttpServletRequest request){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑线路");
		sysLog.setInfo("编辑线路: 状态"+(model.getState()==0?"关闭":"开启")+"，线路模式："+(model.getModel()==0?"正常":"特殊"));
		sysLog.setModel("线路设置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        sysHjLineSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysHjLineSQL.deleteKey(id);
        return this.toResult();
    }

    @RequestMapping("/getSurplusZwCount")
	public String getSurplusZwCount(){
		return sysHjLineSQL.getSurplusZwCount();
	}
}
