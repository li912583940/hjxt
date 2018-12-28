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
	@RequestMapping("/qxFpZw")
	public String qxFpZw(Long hjId, HttpServletRequest request){
		return jlHjDjSQL.qxFpZw(hjId,request);
	}
	
	/**
	 * 说明 [获取剩余的座位号]
	 * L_晓天  @2018年11月9日
	 */
	@RequestMapping("/getSurplusZw")
	public String getSurplusZw(Long hjId){
		return jlHjDjSQL.getSurplusZw(hjId);
	}
	
	/**
	 * 说明 [人工分配座位]
	 * L_晓天  @2018年11月9日
	 */
	@RequestMapping("/rgFpZw")
	public String rgFpZw(Long hjId, Integer lineNo, HttpServletRequest request){
		return jlHjDjSQL.rgFpZw(hjId, lineNo, request);
	}
	
	/**
	 * 说明 [授权才能使用电话]
	 * L_晓天  @2018年11月12日
	 */
	@RequestMapping("/grantCall")
	public String grantCall(Long hjId, HttpServletRequest request){
		return jlHjDjSQL.grantCall(hjId, request);
	}
	
	/**
	 * 说明 [取消授权才能使用电话]
	 * L_晓天  @2018年11月12日
	 */
	@RequestMapping("/cancelGrantCall")
	public String cancelGrantCall(Long hjId, HttpServletRequest request){
		return jlHjDjSQL.cancelGrantCall(hjId, request);
	}
}
