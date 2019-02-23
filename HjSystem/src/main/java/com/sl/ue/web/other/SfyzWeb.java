package com.sl.ue.web.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sfyz")
public class SfyzWeb  extends Result{

	@Autowired
    private JlHjDjService jlHjDjSQL; 
	
	@RequestMapping("/djYz")
    public String djYz(String qsSfz){
		JlHjDjVO jlHjDj = new JlHjDjVO();
		jlHjDj.setState(0);
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ_QS AS b ON a.HJID=b.HJID");
		jlHjDj.setLeftJoinTable(leftJoinTable.toString());
		StringBuffer leftJoinWhere = new StringBuffer();
		leftJoinWhere.append(" AND b.QS_SFZ='"+qsSfz+"'");
		jlHjDj.setLeftJoinWhere(leftJoinWhere.toString());
		List<JlHjDjVO> jlHjDjList = jlHjDjSQL.findList(jlHjDj);
		if(jlHjDjList.size()>0){
			
		}
        return this.toResult();
    }
}
