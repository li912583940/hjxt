package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjRecVO;
import com.sl.ue.service.jl.JlHjRecService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [会见电话记录表]
 * L_晓天  @2018年10月8日
 */
@RestController
@RequestMapping("/jlHjRec")
public class JlHjRecWeb extends Result{

    @Autowired
    private JlHjRecService jlHjRecSQL;

    @RequestMapping("/findList")
    public String findList(JlHjRecVO model,Integer pageSize, Integer pageNum){
        List<JlHjRecVO> list = jlHjRecSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjRecVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjRecSQL.findPojoLeft(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjRecVO model){
        Integer count = jlHjRecSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjRecVO model = jlHjRecSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjRecVO model){
        jlHjRecSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjRecVO model){
        jlHjRecSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjRecSQL.deleteKey(id);
        return this.toResult();
    }

    /**
	 * 说明 [获取当前用户在此次会见记录的注释]
	 * L_晓天  @2018年11月21日
	 */
	@RequestMapping("/getZs")
	public String getZs(String callId){
		return jlHjRecSQL.getZs(callId);
	}
	
	/**
	 * 说明 [添加当前用户的在会见记录中的注释]
	 * L_晓天  @2018年11月24日
	 */
	@RequestMapping("/addRecordFlag")
	public String addRecordFlag(String callId, String writeTxt){
		return jlHjRecSQL.addRecordFlag(callId, writeTxt);
	}
	
    
	/**
	 * 说明 [获取录音评级]
	 * L_晓天  @2018年11月26日
	 */
	@RequestMapping("/getRatingState")
	public String getRatingState(String callId){
		return jlHjRecSQL.getRatingState(callId);
	}
	/**
	 * 说明 [修改录音评级]
	 * L_晓天  @2018年11月26日
	 */
	@RequestMapping("/updateRatingState")
	public String updateRatingState(Long webId, Integer recRatingState, String writeTxt){
		return jlHjRecSQL.updateRatingState(webId, recRatingState, writeTxt);
	}
	
	/**
	 * 说明 [获取当前会见记录的其它详情]
	 * L_晓天  @2018年11月30日
	 */
	@RequestMapping("/getOtherInfo")
	public String getOtherInfo(Long webId){
		return jlHjRecSQL.getOtherInfo(webId);
	}
	
	/**
	 * 说明 [导出excel]
	 * L_晓天  @2018年11月30日
	 */
	@RequestMapping("/exportExcel")
    public void exportExcel(JlHjRecVO model,
    		HttpServletRequest request, HttpServletResponse response) {
		jlHjRecSQL.exportExcel(model, request, response);
    }
	
	/**
	 * 说明 [获取一个星期内每天会见总数]
	 * L_晓天  @2018年12月2日
	 */
	@RequestMapping("/getWeekCount")
	public String getWeekCount(){
		return jlHjRecSQL.getWeekCount();
	}

}
