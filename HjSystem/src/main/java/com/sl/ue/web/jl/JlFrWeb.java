package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.anno.IgnoreSecurity;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlFr")
public class JlFrWeb extends Result{

    @Autowired
    private JlFrService jlFrSQL;
    @Autowired
	private SysLogService sysLogSQL;
    
    @RequestMapping("/findList")
    public String findList(JlFrVO model,Integer pageSize, Integer pageNum){
        List<JlFrVO> list = jlFrSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlFrVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFrSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlFrVO model){
        Integer count = jlFrSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlFrVO model = jlFrSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlFrVO model){
    	// 查看罪犯编号是否已存在 
		boolean b = jlFrSQL.frExist(model.getFrNo());
		if(b){
			this.error(error_103, "当前罪犯编号已存在");
			return this.toResult();
		}
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("添加罪犯信息");
		sysLog.setInfo("新增罪犯编号: "+model.getFrNo()+"，罪犯姓名: "+model.getFrName()+"。");
		sysLog.setModel("罪犯管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
		
		
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlFrVO model){
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("正常");
		sysLog.setOp("编辑罪犯信息");
		sysLog.setInfo("编辑罪犯编号: "+model.getFrNo()+"，罪犯姓名: "+model.getFrName()+"。");
		sysLog.setModel("罪犯管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
		
        jlFrSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
    	JlFrVO model = jlFrSQL.findOne(id);
    	
    	SysUserVO user = TokenUser.getUser();
    	SysLogVO sysLog = new SysLogVO();
    	sysLog.setType("严重");
		sysLog.setOp("删除罪犯信息");
		sysLog.setInfo("删除罪犯编号: "+model.getFrNo()+"，罪犯姓名: "+model.getFrName()+"。");
		sysLog.setModel("罪犯管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
		
        jlFrSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [导出excel]
     * L_晓天  @2018年11月30日
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(JlFrVO model,
    		HttpServletRequest request, HttpServletResponse response) {
    	jlFrSQL.exportExcel(model, request, response);
    }
    
    @RequestMapping(value="/importExcel",method={RequestMethod.GET,RequestMethod.POST})
    @IgnoreSecurity
    public String importExcel(HttpServletRequest request, HttpServletResponse response){
    	return jlFrSQL.importExcel(request, response);
    } 
    
}
