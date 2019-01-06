package com.sl.ue.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.JlFr;
import com.sl.ue.entity.jl.JlHjDj;
import com.sl.ue.entity.jl.JlQs;
import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.BaseLogService;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.http.token.TokenUser;

@Service("baseLogService")
public class BaseLogImpl<T> implements BaseLogService<T>{

	@Autowired
	private SysLogService sysLogSQL;
	@Autowired
	private JlFrService jlFrSQL;
	@Autowired
	private JlHjDjService jlHjDjSQL;
	@Autowired
	private JlQsService jlQsSQL;
	
	@Override
	public void execute(String tableName, T model, String state) {
//		if("JL_FR".equals(tableName)){
//			JlFrVO t = (JlFrVO) model;
//			frExecute(t, state);
//		}else if("JL_HJ_DJ".equals(tableName)){
//			JlHjDjVO t = (JlHjDjVO) model;
//			jlHjDjExecute(t, state);
//		}else if("JL_QS".equals(tableName)){
//			JlQsVO t= (JlQsVO) model;
//			qsExecute(t, state);
//		}
		
	}

	public void executeDel(String tableName, Object key){
		if("JL_FR".equals(tableName)){
			JlFrVO t =  jlFrSQL.findOne(key);
			frExecute(t, "delete");
		}else if("JL_HJ_DJ".equals(tableName)){
			JlHjDjVO  t = jlHjDjSQL.findOne(key);
			jlHjDjExecute(t, "delete");
		}else if("JL_QS".equals(tableName)){
			JlQsVO t = jlQsSQL.findOne(key);
			qsExecute(t, "delete");
		}
	}
	/**
	 * 说明 [罪犯]
	 * @param t
	 * @param state
	 * L_晓天  @2018年12月28日
	 */
	private void frExecute(JlFrVO t, String state){
		SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		if("add".equals(state)){
			sysLog.setType("正常");
			sysLog.setOp("添加罪犯信息");
			sysLog.setInfo("新增罪犯编号: "+t.getFrNo()+"，罪犯姓名: "+t.getFrName()+"。");
		}else if("edit".equals(state)){
			sysLog.setType("正常");
			sysLog.setOp("编辑罪犯信息");
			sysLog.setInfo("编辑罪犯编号: "+t.getFrNo()+"，罪犯姓名: "+t.getFrName()+"。");
		}else if("delete".equals(state)){
			sysLog.setType("严重");
			sysLog.setOp("删除罪犯信息");
			sysLog.setInfo("删除罪犯编号: "+t.getFrNo()+"，罪犯姓名: "+t.getFrName()+"。");
		}
		sysLog.setModel("罪犯管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
	}
	
	/**
	 * 说明 [亲属]
	 * @param model
	 * @param state
	 * L_晓天  @2018年12月28日
	 */
	private void qsExecute(JlQsVO t, String state){
		SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		if("add".equals(state)){
			sysLog.setType("正常");
			sysLog.setOp("添加亲属信息");
			sysLog.setInfo("为罪犯编号: "+t.getFrNo()+"添加亲属。亲属姓名: "+t.getQsName()+"。");
		}else if("edit".equals(state)){
			sysLog.setType("正常");
			sysLog.setOp("编辑亲属信息");
			sysLog.setInfo("为罪犯编号: "+t.getFrNo()+"编辑亲属。亲属姓名: "+t.getQsName()+"。");
		}else if("delete".equals(state)){
			sysLog.setType("严重");
			sysLog.setOp("删除亲属信息");
			sysLog.setInfo("为罪犯编号: "+t.getFrNo()+"删除亲属。罪犯姓名: "+t.getQsName()+".");
		}
		sysLog.setModel("亲属管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
	}
	
	private void jlHjDjExecute(JlHjDjVO t, String state){
		SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		String hjType="";
		if(t.getHjType()==1){
			hjType="亲属会见";
		}else if(t.getHjType()==2){
			hjType="监护人会见";
		}else if(t.getHjType()==3){
			hjType="律师会见";
		}else if(t.getHjType()==4){
			hjType="使领馆探视";
		}else if(t.getHjType()==5){
			hjType="提审会见";
		}else if(t.getHjType()==6){
			hjType="公务会见";
		}else if(t.getHjType()==9){
			hjType="特批会见";
		}else if(t.getHjType()==99){
			hjType="其他会见";
		}
		
		String hjMode="";
		if(t.getHjMode()==1){
			hjMode="隔离会见";
		}else if(t.getHjMode()==2){
			hjMode="非隔离会见";
		}else if(t.getHjMode()==3){
			hjMode="远程视频会见";
		}else if(t.getHjMode()==9){
			hjMode="其他方式";
		}
		Integer hjsc = t.getHjTime()!=null?t.getHjTime()/60:0;
		if("add".equals(state)){
			sysLog.setType("正常");
			sysLog.setOp("添加会见登记");
			sysLog.setInfo("添加会见登记，罪犯编号： "+t.getFrNo()+"，亲属姓名： "+t.getQsInfo1()+"，会见类型："+hjType+"，会见方式："+hjMode+"，会见时长："+hjsc);
		}else if("edit".equals(state)){
			sysLog.setType("正常");
			sysLog.setOp("编辑会见登记");
			sysLog.setInfo("编辑会见登记，罪犯编号： "+t.getFrNo()+"，亲属姓名： "+t.getQsInfo1()+"，会见类型："+hjType+"，会见方式："+hjMode+"，会见时长："+hjsc);
		}else if("delete".equals(state)){
			sysLog.setType("严重");
			sysLog.setOp("删除会见登记");
			sysLog.setInfo("删除会见登记，罪犯编号： "+t.getFrNo()+"，亲属姓名： "+t.getQsInfo1()+"，会见类型："+hjType+"，会见方式："+hjMode+"，会见时长："+hjsc);
		}
		sysLog.setModel("会见登记");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
	}
}
