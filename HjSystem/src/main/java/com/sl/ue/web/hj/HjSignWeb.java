package com.sl.ue.web.hj;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
public class HjSignWeb extends Result{

	@Autowired
	private JlHjDjService  jlHjDjSQL;
	
	@RequestMapping("/findPojo")
    public String findPojo(Integer pageSize, Integer pageNum){
		JlHjDjVO jlHjDj = new JlHjDjVO();
		jlHjDj.setState(0);
        Map<String, Object> map = jlHjDjSQL.findPojoByHjSign(jlHjDj, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }
	
	/**
	 * 说明 [分配座位]
	 * L_晓天  @2018年11月8日
	 */
	@RequestMapping("/fpZw")
	public String fpZw(Long hjId){
		return jlHjDjSQL.fpZw(hjId);
	}
	
	/**
	 * 说明 [取消分配座位]
	 * L_晓天  @2018年11月8日
	 */
	@RequestMapping("/qxZw")
	public String qxZw(Long hjId, HttpServletRequest request){
		return jlHjDjSQL.qxZw(hjId,request);
	}
}
