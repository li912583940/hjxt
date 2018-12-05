package com.sl.ue.web.hj;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [会见通知]
 * L_晓天  @2018年10月5日
 */
@RestController
@RequestMapping("/hjNotice")
public class HjNoticeWeb extends Result{

	@Autowired
	private JlHjDjService  jlHjDjSQL;
	
	@RequestMapping("/findPojo")
    public String findPojo(Integer pageSize, Integer pageNum){
		JlHjDjVO jlHjDj = new JlHjDjVO();
		jlHjDj.setState(0);
        Map<String, Object> map = jlHjDjSQL.findPojo(jlHjDj, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }
	
	@RequestMapping("/findNotTzList")
	public String findNotTzList(){
		JlHjDjVO jlHjDj = new JlHjDjVO();
		jlHjDj.setState(0);
		jlHjDj.setPageTzState(0);
		List<JlHjDjVO> list = jlHjDjSQL.findList(jlHjDj);
		this.putData(list);
		return this.toResult();
	}
}
