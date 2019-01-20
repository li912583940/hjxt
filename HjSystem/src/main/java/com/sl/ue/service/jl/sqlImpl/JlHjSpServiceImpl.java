package com.sl.ue.service.jl.sqlImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjSpDetailsVO;
import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjSpDetailsService;
import com.sl.ue.service.jl.JlHjSpService;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.token.TokenUser;

@Service("jlHjSpSQL")
public class JlHjSpServiceImpl extends BaseSqlImpl<JlHjSpVO> implements JlHjSpService{

	@Autowired
	private JlHjDjService jlHjDjSQL;
	@Autowired
	private JlHjSpDetailsService jlHjSpDetailsSQL;
	
	@Override
	public Map<String, Object> findPojoLeft(JlHjSpVO model, Integer pageSize, Integer pageNum) {
		model.setState(0); // 查询进行中审批
		// 查询当前用户可查看的审批记录
		SysUserVO sysUser = TokenUser.getUser();
		String sql = "SELECT a.id from jl_hj_sp AS a LEFT JOIN jl_hj_sp_user AS b ON a.set_no=b.sp_set_no"
				+ " WHERE a.state=0 AND a.speed_progress=b.sp_level"
				+ " AND (b.sp_user_no='"+sysUser.getUserNo()+"' OR b.sp_dept_id="+sysUser.getDeptId()+")";
		List<Map<String, Object>> spIdList = this.jdbcTemplate.queryForList(sql);
		List<Integer> sId = new ArrayList<Integer>();
		for(Map<String, Object> m : spIdList){
			sId.add((int)m.get("id"));
		}
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		List<JlHjSpVO> list = (List<JlHjSpVO>) map.get("list");
		for(JlHjSpVO t : list){
			JlHjDjVO jlHjDj = jlHjDjSQL.findOne(t.getHjid());
			if(jlHjDj==null){
				continue;
			}
			t.setFrNo(jlHjDj.getFrNo());
			t.setFrName(jlHjDj.getFrName());
			t.setJqName(jlHjDj.getJqName());
			t.setDjTime(jlHjDj.getDjTime());
			String qsInfo = "";
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo1())){
				qsInfo=jlHjDj.getQsInfo1();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo2())){
				qsInfo+=";"+jlHjDj.getQsInfo2();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo3())){
				qsInfo+=";"+jlHjDj.getQsInfo3();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo4())){
				qsInfo+=";"+jlHjDj.getQsInfo4();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo5())){
				qsInfo+=";"+jlHjDj.getQsInfo5();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo6())){
				qsInfo+=";"+jlHjDj.getQsInfo6();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo7())){
				qsInfo+=";"+jlHjDj.getQsInfo7();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo8())){
				qsInfo+=";"+jlHjDj.getQsInfo8();
			}
			if(StringUtils.isNotBlank(jlHjDj.getQsInfo9())){
				qsInfo+=";"+jlHjDj.getQsInfo9();
			}
			t.setQsInfo(qsInfo);
			if(sId.contains(t.getId())){ //有审批权限
				t.setSpPermission(1);
			}else{
				t.setSpPermission(0);
			}
		}
		return map;
	}

	public Map<String, Object> findPojoLog(JlHjSpVO model, Integer pageSize, Integer pageNum){
		// 查询当前用户可查看的审批记录
//		SysUserVO sysUser = TokenUser.getUser();
//		String sql = "SELECT a.id from jl_hj_sp AS a LEFT JOIN jl_hj_sp_user AS b ON a.set_no=b.sp_set_no"
//				+ " WHERE a.state=0 AND a.speed_progress=b.sp_level"
//				+ " AND (b.sp_user_no='"+sysUser.getUserNo()+"' OR b.sp_dept_id="+sysUser.getDeptId()+")";
//		List<Map<String, Object>> spIdList = this.jdbcTemplate.queryForList(sql);
//		List<Integer> sId = new ArrayList<Integer>();
//		for(Map<String, Object> m : spIdList){
//			sId.add((int)m.get("id"));
//		}
		StringBuffer leftJoinField = new StringBuffer();
		leftJoinField.append(",b.JQ_Name,b.FR_No,b.FR_Name,b.DJ_Time");
		leftJoinField.append(",b.QS_Info1,b.QS_Info2,b.QS_Info3,b.QS_Info4,b.QS_Info5");
		leftJoinField.append(",b.QS_Info6,b.QS_Info7,b.QS_Info8,b.QS_Info9");
		model.setLeftJoinField(leftJoinField.toString());
		
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ AS b ON a.hjid=b.hjid");
		model.setLeftJoinTable(leftJoinTable.toString());
		
		StringBuffer leftJoinWhere = new StringBuffer();
		if(StringUtils.isNotBlank(model.getFrNo())){
			String str = model.getFrNo();
			leftJoinWhere.append(" AND b.FR_No LIKE '%"+str+"%' ");
			model.setFrNo(null);
		}
		if(StringUtils.isNotBlank(model.getFrName())){
			String str = model.getFrName();
			leftJoinWhere.append(" AND (b.FR_Name LIKE '%"+str+"%' OR dbo.f_get_fryp(b.FR_Name,'"+str+"') =1 )");
			model.setFrName(null);
		}
		if(StringUtils.isNotBlank(model.getJqNo())){
			String str = model.getJqNo();
			leftJoinWhere.append(" AND b.JQ_No='"+str+"'");
			model.setFrName(null);
		}
		leftJoinWhere.append(" AND a.state<>0");
		model.setLeftJoinWhere(leftJoinWhere.toString()); // 查询审批结束的记录
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		List<JlHjSpVO> list = (List<JlHjSpVO>) map.get("list");
		for(JlHjSpVO t : list){
			String qsInfo = "";
			if(StringUtils.isNotBlank(t.getQsInfo1())){
				qsInfo=t.getQsInfo1();
			}
			if(StringUtils.isNotBlank(t.getQsInfo2())){
				qsInfo+=";"+t.getQsInfo2();
			}
			if(StringUtils.isNotBlank(t.getQsInfo3())){
				qsInfo+=";"+t.getQsInfo3();
			}
			if(StringUtils.isNotBlank(t.getQsInfo4())){
				qsInfo+=";"+t.getQsInfo4();
			}
			if(StringUtils.isNotBlank(t.getQsInfo5())){
				qsInfo+=";"+t.getQsInfo5();
			}
			if(StringUtils.isNotBlank(t.getQsInfo6())){
				qsInfo+=";"+t.getQsInfo6();
			}
			if(StringUtils.isNotBlank(t.getQsInfo7())){
				qsInfo+=";"+t.getQsInfo7();
			}
			if(StringUtils.isNotBlank(t.getQsInfo8())){
				qsInfo+=";"+t.getQsInfo8();
			}
			if(StringUtils.isNotBlank(t.getQsInfo9())){
				qsInfo+=";"+t.getQsInfo9();
			}
			t.setQsInfo(qsInfo);
//			if(sId.contains(t.getId())){ //有审批权限
//				t.setSpPermission(1);
//			}else{
//				t.setSpPermission(0);
//			}
		}
		return map;
	}
	
	@Override
	public String saveSpResult(Integer spId, Integer speedProgress, String explain, Integer state) {
		Result result =new Result();
		if(spId==null){
			result.error(Result.error_102);
			return result.toResult();
		}
		JlHjSpVO  jlHjSp = this.findOne(spId);
		if(jlHjSp == null){
			result.error(Result.error_103, "查询不到当前审批记录");
			return result.toResult();
		}
		SysUserVO sysUser = TokenUser.getUser();
		//对当前不同的审批结果进行判断
		if(state == 1){ //审批通过
			//查看当前审批共有几级
			if(jlHjSp.getMaxNum()==speedProgress){ //当最后一级时，修改会见登记状态state=0
				JlHjDjVO jlHjDj = jlHjDjSQL.findOne(jlHjSp.getHjid());
				jlHjDj.setState(0);
				jlHjDjSQL.edit(jlHjDj);
				
				jlHjSp.setState(1); //修改状态为完成
				jlHjSp.setLastSpTime(new Date());
				this.edit(jlHjSp);
				result.msg("审批通过，请通知罪犯及亲属参加会见");
			}else if(jlHjSp.getMaxNum()>speedProgress){ //进入下一级
				jlHjSp.setSpeedProgress(speedProgress+1);
				this.edit(jlHjSp);
				result.msg("当前阶段审批通过，已移交至下一阶段");
			}else if(jlHjSp.getMaxNum()<speedProgress){
				result.error(Result.error_103, "系统内部错误，出现这种错误多为内部测试修改数据");
				return result.toResult();
			}
		}else{ // 审批不通过
			if(jlHjSp.getMaxNum()>=speedProgress){ 
				JlHjDjVO jlHjDj = jlHjDjSQL.findOne(jlHjSp.getHjid());
				jlHjDj.setState(2);
				jlHjDj.setCancelInfo("当前罪犯会见登记，审批不通过");
				jlHjDjSQL.edit(jlHjDj);
				
				jlHjSp.setState(2); //修改状态为审批失败
				jlHjSp.setLastSpTime(new Date());
				this.edit(jlHjSp);
				result.msg("审批不通过");
			}else if(jlHjSp.getMaxNum()<speedProgress){
				result.error(Result.error_103, "系统内部错误，出现这种错误多为内部测试修改数据");
				return result.toResult();
			}
		}
				
		//存储审批结果
		JlHjSpDetailsVO jlHjSpDetails = new JlHjSpDetailsVO();
		jlHjSpDetails.setSpId(spId);
		jlHjSpDetails.setUserNo(sysUser.getUserNo());
		jlHjSpDetails.setUserName(sysUser.getUserName());
		jlHjSpDetails.setDeptId(sysUser.getDeptId());
		jlHjSpDetails.setDeptName(sysUser.getDeptName());
		jlHjSpDetails.setState(state);
		jlHjSpDetails.setExplain(explain);
		jlHjSpDetails.setSpTime(new Date());
		jlHjSpDetails.setSpeedProgress(speedProgress);
		jlHjSpDetailsSQL.add(jlHjSpDetails);
		
		return result.toResult();
	}

}
