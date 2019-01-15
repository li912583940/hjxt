package com.sl.ue.service.jl.sqlImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjSpService;
import com.sl.ue.util.http.token.TokenUser;

@Service("jlHjSpSQL")
public class JlHjSpServiceImpl extends BaseSqlImpl<JlHjSpVO> implements JlHjSpService{

	@Autowired
	private JlHjDjService jlHjDjSQL;
	
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

}
