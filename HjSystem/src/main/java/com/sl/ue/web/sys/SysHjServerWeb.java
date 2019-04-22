package com.sl.ue.web.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/sysHjServer")
public class SysHjServerWeb extends Result{

    @Autowired
    private SysHjServerService sysHjServerSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(SysHjServerVO model,Integer pageSize, Integer pageNum){
        List<SysHjServerVO> list = sysHjServerSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(SysHjServerVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = sysHjServerSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(SysHjServerVO model){
        Integer count = sysHjServerSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        SysHjServerVO model = sysHjServerSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(SysHjServerVO model){
        sysHjServerSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(SysHjServerVO model, HttpServletRequest request){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑线路");
		sysLog.setInfo("编辑线路: IP"+model.getIp()+"，端口："+model.getPort()+"，监听端口："+model.getAudioPort()+"，录音网络地址："+model.getRecurl());
		sysLog.setModel("线路设置");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
        sysHjServerSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        sysHjServerSQL.deleteKey(id);
        return this.toResult();
    }

}
