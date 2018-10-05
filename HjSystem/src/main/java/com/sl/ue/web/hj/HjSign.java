package com.sl.ue.web.hj;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [会见签到--座位分配]
 * L_晓天  @2018年10月5日
 */
@RestController
@RequestMapping("/hjSign")
public class HjSign extends Result{

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
}
