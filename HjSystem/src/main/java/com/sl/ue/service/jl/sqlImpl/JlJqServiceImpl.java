package com.sl.ue.service.jl.sqlImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjJqWeekVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjJqWeekService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.util.StringUtil;
import com.sl.ue.util.http.Result;

@Service("jlJqSQL")
public class JlJqServiceImpl extends BaseSqlImpl<JlJqVO> implements JlJqService{

	@Autowired
	private JlHjJqWeekService jlHjJqWeekSQL;
	@Autowired
    private SysHjServerService sysHjServerSQL;
	
	public Map<String, Object> findPojoJoin(JlJqVO model, Integer pageSize, Integer pageNum){
		if(StringUtils.isNotBlank(model.getJqName())){
			String str = model.getJqName();
			model.setLeftJoinWhere(" AND a.JQ_Name LIKE '%"+str+"%' ");
			model.setJqName(null);
		}
		Map<String, Object> map = this.findPojo(model, pageSize, pageNum);
		List<JlJqVO> list = (List<JlJqVO>) map.get("list");
		for(JlJqVO t : list){
			JlHjJqWeekVO week = new JlHjJqWeekVO();
			week.setJqNo(t.getJqNo());
			List<JlHjJqWeekVO> weekList = jlHjJqWeekSQL.findList(week);
			String weekStr = "";
			for(JlHjJqWeekVO w : weekList){
				switch (w.getJqWeek()) {
				case 1: weekStr+="星期一,";break;
				case 2: weekStr+="星期二,";break;
				case 3: weekStr+="星期三,";break;
				case 4: weekStr+="星期四,";break;
				case 5: weekStr+="星期五,";break;
				case 6: weekStr+="星期六,";break;
				case 7: weekStr+="星期日,";break;
				}
			}
			weekStr = StringUtil.lastComma(weekStr);
			t.setJqWeek(weekStr);
		}
		return map;
	}
	
	@Override
	public String getCheckedWeek(String jqNo) {
		Result result = new Result();
		if(jqNo == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		JlHjJqWeekVO jlHjJqWeek = new JlHjJqWeekVO();
		jlHjJqWeek.setJqNo(jqNo);
		List<JlHjJqWeekVO> list = jlHjJqWeekSQL.findList(jlHjJqWeek);
		List<Integer> reList = new ArrayList<Integer>();
		for(JlHjJqWeekVO t : list){
			reList.add(t.getJqWeek());
		}
		result.putJson(reList);
		return result.toResult();
	}

	@Override
	public String addJqWeek(String jqNo, String weeks) {
		Result result = new Result();
		if(jqNo == null){
			result.error(Result.error_102);
    		return result.toResult();
    	}
		// 先删
		JlHjJqWeekVO jlHjJqWeek = new JlHjJqWeekVO();
		jlHjJqWeek.setJqNo(jqNo);
		jlHjJqWeekSQL.delete(jlHjJqWeek);
		
		String jy = "Server1";
		SysHjServerVO sysHjServer = new SysHjServerVO();
    	List<SysHjServerVO> list = sysHjServerSQL.findList(sysHjServer);
    	if(list.size()>0){
    		jy = list.get(0).getServerName();
    	}
    	
		//再添加
		if(StringUtils.isNotBlank(weeks)){
			for(String i : weeks.split(",")){
				JlHjJqWeekVO t = new JlHjJqWeekVO();
				t.setJy(jy);
				t.setJqNo(jqNo);
				t.setJqWeek(Integer.parseInt(i));
				jlHjJqWeekSQL.add(t);
			}
		}
		return result.toResult();
	}


}
