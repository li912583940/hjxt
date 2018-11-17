package com.sl.ue.service.jl.sqlImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlHjSpFrVO;
import com.sl.ue.entity.jl.vo.JlHjSpQsVO;
import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlHjSpFrService;
import com.sl.ue.service.jl.JlHjSpQsService;
import com.sl.ue.service.jl.JlHjSpService;
import com.sl.ue.util.http.Result;

@Service("jlHjSpSQL")
public class JlHjSpServiceImpl extends BaseSqlImpl<JlHjSpVO> implements JlHjSpService{

	@Autowired
	private JlHjDjService jlHjDjSQL;
	@Autowired
	private JlHjSpFrService jlHjSpFrSQL;
	@Autowired
	private JlHjSpQsService jlHjSpQsSQL;
	
	public Map<String, Object> findPojoJoin(JlHjSpVO model, Integer pageSize, Integer pageNum){
		StringBuffer leftJoinField = new StringBuffer();
		leftJoinField.append(",a.SP_Tj_UserName AS spTjUserName");
		leftJoinField.append(",a.SP_UserName AS spUserName");
		leftJoinField.append(",b.FR_Name AS frName");
		leftJoinField.append(",c.JQ_Name AS jqName");
		
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN JL_HJ_SP_FR AS b ON a.SPID=b.SPID");
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ AS c ON a.HJID=c.HJID");
		
		StringBuffer leftJoinWhere = new StringBuffer();
		leftJoinWhere.append(" AND a.SP_State<>0");
		
		if(StringUtils.isNotBlank(model.getCallTimeStart())){
			leftJoinWhere.append(" AND a.SP_Tj_Time>='"+model.getCallTimeStart()+"'");
		}
		if(StringUtils.isNotBlank(model.getCallTimeEnd())){
			leftJoinWhere.append(" AND a.SP_Tj_Time<='"+model.getCallTimeEnd()+"'");
		}
		if(StringUtils.isNotBlank(model.getFrName())){
			leftJoinWhere.append(" AND b.FR_Name like '%"+model.getFrName()+"%'");
		}
		
		model.setLeftJoinField(leftJoinField.toString());
		model.setLeftJoinTable(leftJoinTable.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
		
		Map<String, Object> map =this.findPojo(model, pageSize, pageNum);
//		List<JlHjSpVO> list = (List<JlHjSpVO>) map.get("list");
//		for(JlHjSpVO t : list){
//			
//		}
		return map;
	}
	
	
	public String findDetails(Long spId){
		Result result = new Result();
		if(spId == null){
			result.error(Result.error_102);
			return result.toResult();
		}
		
		JlHjSpVO jlHjSp = this.findOne(spId);
		if(jlHjSp!=null){
			if(jlHjSp.getHjId() != null){
				JlHjDjVO jlHjDj = jlHjDjSQL.findOne(jlHjSp.getHjId());
				if(jlHjDj != null){
					jlHjSp.setFrName(jlHjDj.getFrName());
					jlHjSp.setFrNo(jlHjDj.getFrNo());
					jlHjSp.setJqName(jlHjDj.getJqName());
					jlHjSp.setHjId(jlHjDj.getHjid());
				}else{
					result.error(Result.error_100,"系统数据异常");
					return result.toResult();
				}
			}
		}
		JlHjSpFrVO jlHjSpFr = jlHjSpFrSQL.findOne(spId);
		if(jlHjSpFr != null){
			jlHjSp.setSpReason(jlHjSpFr.getSpReason());
			jlHjSp.setSpRemark(jlHjSpFr.getSpRemarks());
		}
		result.putJson("jlHjSp", jlHjSp);
		
		JlHjSpQsVO jlHjSpQs = new JlHjSpQsVO();
		jlHjSpQs.setSpId(spId);
		List<JlHjSpQsVO> jlHjSpQsList = jlHjSpQsSQL.findList(jlHjSpQs);
		
		result.putData("jlHjSpQsList", jlHjSpQsList);
		
		return result.toResult();
	}
}
