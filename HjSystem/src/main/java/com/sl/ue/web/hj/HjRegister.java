package com.sl.ue.web.hj;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [会见登记]
 * L_晓天  @2018年10月6日
 */
@RestController
@RequestMapping("/hjRegister")
public class HjRegister extends Result{

	@Autowired
    private JlFrService jlFrSQL;
	
	@Autowired
    private JlQsService jlQsSQL;
	
	/**
	 * 说明 [查询罪犯]
	 * L_晓天  @2018年10月6日
	 */
	@RequestMapping("/findFrPojo")
    public String findFrPojo(JlFrVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlFrSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }
	
	/**
	 * 说明 [查询家属]
	 * L_晓天  @2018年10月6日
	 */
	@RequestMapping("/findQsPojo")
    public String findQsPojo(JlQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }
	
}
