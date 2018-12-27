package com.sl.ue.web.hj;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.util.http.Result;

/**
 * 说明 [登记记录]
 * L_晓天  @2018年12月25日
 */
@RestController
@RequestMapping("/hjdjLog")
public class HjdjLog extends Result{

	@Autowired
	private JlHjDjService  jlHjDjSQL;
	
	@RequestMapping("/findPojo")
    public String findPojo(JlHjDjVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjDjSQL.findPojoByLog(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }
	
	@RequestMapping("/exportExcel")
	public void exportExcel(JlHjDjVO model, 
			HttpServletRequest request, HttpServletResponse response){
		jlHjDjSQL.exportExcelByLog(model, request, response);
	}
}