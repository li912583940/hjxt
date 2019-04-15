package com.sl.ue.web.hj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjAcdInfoVO;
import com.sl.ue.entity.hj.vo.HjdjAcdWindowsInfoVO;
import com.sl.ue.service.hj.HjdjAcdInfoService;
import com.sl.ue.service.hj.HjdjAcdWindowsInfoService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdInfo")
public class HjdjAcdInfoWeb extends Result{

    @Autowired
    private HjdjAcdInfoService hjdjAcdInfoSQL;
    @Autowired
    private HjdjAcdWindowsInfoService hjdjAcdWindowsInfoSQL;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @RequestMapping("/findList")
    public String findList(HjdjAcdInfoVO model,Integer pageSize, Integer pageNum){
        List<HjdjAcdInfoVO> list = hjdjAcdInfoSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HjdjAcdInfoVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = hjdjAcdInfoSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HjdjAcdInfoVO model){
        Integer count = hjdjAcdInfoSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdInfoVO model = hjdjAcdInfoSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdInfoVO model){
        hjdjAcdInfoSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdInfoVO model){
        hjdjAcdInfoSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdInfoSQL.deleteKey(id);
        return this.toResult();
    }

    @RequestMapping("/getAcdInfo")
    public String getAcdInfo(HttpServletRequest request, HttpServletResponse response){
    	List<HjdjAcdInfoVO> list = hjdjAcdInfoSQL.findList(new HjdjAcdInfoVO());
    	if(list.size()>0){
    		this.putJson("acdInfo", list.get(0));
    	}
    	HjdjAcdWindowsInfoVO hjdjAcdWindowsInfo = new HjdjAcdWindowsInfoVO();
		hjdjAcdWindowsInfo.setAcdip(request.getRemoteAddr());
		System.out.println(request.getRemoteAddr());
		List<HjdjAcdWindowsInfoVO>  windowsInfoList = hjdjAcdWindowsInfoSQL.findList(hjdjAcdWindowsInfo);
		if(windowsInfoList.size()>0){
			this.putJson("acdindex", windowsInfoList.get(0).getAcdindex());
			this.putJson("acdServerName", windowsInfoList.get(0).getServerName());
		}
    	return this.toResult();
    }
    
    @RequestMapping("/nextCallNum")
    public String nextCallNum(Integer acdindex, String acdServerName, HttpServletRequest request, HttpServletResponse response){
    	Long hjIndex = (Long) jdbcTemplate.execute(  // 调用存储过程 获取会见批次号
   		     new CallableStatementCreator() {
   				@Override
   				public CallableStatement createCallableStatement(Connection con) throws SQLException {
   					 String storedProc = "{call hjdj_acd_set(?,?,?,?,?)}";// 调用的sql   
   			           CallableStatement cs = con.prepareCall(storedProc);   
   			           cs.setString(1, acdServerName);// 设置输入参数的值   
   			           cs.setInt(2, acdindex);
   			           cs.registerOutParameter(3, java.sql.Types.INTEGER);
   			           cs.registerOutParameter(4, java.sql.Types.VARCHAR);
   			           cs.registerOutParameter(5, java.sql.Types.BIGINT);// 注册输出参数的类型   
   			           return cs;   
   				}
   			}, 
   		    new CallableStatementCallback<Long>() {  
   				@Override
   		        public Long doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
   		           cs.execute();   
   		           return cs.getLong(5);// 获取输出参数的值   
   		        }   
   		});
    	
    	List<HjdjAcdInfoVO> list = hjdjAcdInfoSQL.findList(new HjdjAcdInfoVO());
    	if(list.size()>0){
    		this.putJson("acdInfo", list.get(0));
    	}
    	return this.toResult();
    }
}
