package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.sys.vo.SysConfVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.sys.SysConfService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlHjDj")
public class JlHjDjWeb extends Result{

    @Autowired
    private JlHjDjService jlHjDjSQL;
	@Autowired
	private SysLogService sysLogSQL;
	@Autowired
	private SysConfService sysConfSQL;
	
    @RequestMapping("/findList")
    public String findList(JlHjDjVO model,Integer pageSize, Integer pageNum){
        List<JlHjDjVO> list = jlHjDjSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjDjVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjDjSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjDjVO model){
        Integer count = jlHjDjSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjDjVO model = jlHjDjSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjDjVO model){
        jlHjDjSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjDjVO model){
        jlHjDjSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjDjSQL.deleteKey(id);
        return this.toResult();
    }

    /**
	 * 说明 [查询罪犯]
	 * L_晓天  @2018年10月6日
	 */
	@RequestMapping("/findFrPojo")
    public String findFrPojo(JlFrVO model, Integer pageSize, Integer pageNum, String qsName, String qsSfz){
        Map<String, Object> map = jlHjDjSQL.findPojoJoin(model, pageSize, pageNum, qsName, qsSfz);
        this.putPojo(map);
        return this.toResult();
    }
	
	
	/**
	 * 说明 [提交会见登记]
	 * L_晓天  @2018年10月9日
	 */
	@RequestMapping("/addHjdj")
	public String addHjdj(
			String frNo, // 罪犯编号
			String qsIds, // 亲属id集合
			Integer hjsc, // 会见时长  单位：分钟
			String hjInfo, // 会见说明
			Integer hjType, // 会见类型
			Integer hjMode, //会见方式
			Integer callNo, //排队号
			Integer tpQsNum, //特批亲属个数
			Integer qzSp, // 强制审批
			HttpServletRequest request
			){
		return jlHjDjSQL.addHjdj(frNo, qsIds, hjsc, hjInfo, hjType, hjMode, callNo, tpQsNum, qzSp, request);
		
	}
	
	
	/**
	 * 说明 [打印小票]
	 * L_晓天  @2018年10月12日
	 */
	@RequestMapping("/printXp")
	public String printXp(Long hjid){ // 会见ID
		if(hjid == null){
			this.error(error_102);
			return this.toResult();
		}
		List<SysConfVO> sysConfList = sysConfSQL.findList(new SysConfVO());
		SysConfVO sysConf = null;
		if(sysConfList.size()>0){
			sysConf = sysConfList.get(0);
		}
		if(sysConf!=null && sysConf.getPrintFormat()==1){
			return jlHjDjSQL.printXp(hjid);
		}else{
			return jlHjDjSQL.printXpA4(hjid);
		}
		
	}
	
	/**
	 * 说明 [获取当前会见登记的亲属id集合]
	 * @return
	 * L_晓天  @2018年12月27日
	 */
	@RequestMapping("/getQsIdsByHjid")
	public String getQsListByHjid(Long hjid){
		if(hjid == null){
			this.error(error_102);
			return this.toResult();
		}
		return jlHjDjSQL.getQsListByHjid(hjid);
	}
	/**
	 * 说明 [修改会见登记]
	 * @param model
	 * @return
	 * L_晓天  @2018年12月27日
	 */
	@RequestMapping("/editDj")
	public String editDj(Long hjid, Integer hjTime, Integer hjType, Integer hjMode, String hjInfo, String qsIds, HttpServletRequest request){
		JlHjDjVO model = jlHjDjSQL.findOne(hjid);
		SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		String hjTypeStr="";
		if(hjType==1){
			hjTypeStr="亲属会见";
		}else if(hjType==2){
			hjTypeStr="监护人会见";
		}else if(hjType==3){
			hjTypeStr="律师会见";
		}else if(hjType==4){
			hjTypeStr="使领馆探视";
		}else if(hjType==5){
			hjTypeStr="提审会见";
		}else if(hjType==6){
			hjTypeStr="公务会见";
		}else if(hjType==9){
			hjTypeStr="特批会见";
		}else if(hjType==99){
			hjTypeStr="其他会见";
		}
		
		String hjModeStr = "";
		if(hjMode==1){
			hjModeStr = "隔离会见";
		}else if(hjMode==2){
			hjModeStr = "非隔离会见";
		}else if(hjMode==3){
			hjModeStr = "远程视频会见";
		}else if(hjMode==9){
			hjModeStr = "其他方式";
		}
		Integer hjscInt = hjTime;
		sysLog.setType("正常");
		sysLog.setOp("修改会见登记");
		sysLog.setInfo("修改会见登记，罪犯编号： "+model.getFrNo()+"，罪犯姓名："+model.getFrName()+"，会见类型："+hjTypeStr+"，会见方式："+hjModeStr+"，会见时长："+hjscInt+"分钟");
		sysLog.setModel("会见登记");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
		return jlHjDjSQL.editDj(hjid, hjTime, hjType, hjMode, hjInfo, qsIds);
	}
	
	/**
	 * 说明 [取消登记]
	 * L_晓天  @2018年10月12日
	 */
	@RequestMapping("/cancelDj")
	public String cancelDj(Long id, String cancelInfo, HttpServletRequest request){ // 会见ID
		if(id == null){
			this.error(error_102);
			return this.toResult();
		}
		JlHjDjVO model = jlHjDjSQL.findOne(id);
		SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		sysLog.setType("严重");
		sysLog.setOp("取消会见登记");
		sysLog.setInfo("取消会见登记，罪犯编号： "+model.getFrNo()+"罪犯姓名："+model.getFrName()+"，亲属姓名： "+model.getQsInfo1());
		sysLog.setModel("会见登记");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLog.setUserIp(request.getRemoteAddr());
		sysLogSQL.add(sysLog);
		
		return jlHjDjSQL.cancelDj(id, cancelInfo);
	}
	
	@RequestMapping("/syncQs")
	public String syncQs(String frNo){
		return jlHjDjSQL.syncQs(frNo);
	}
}
