package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.Constants;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.WebContextUtil;
import com.sl.ue.util.http.token.JqRoleManager;

@RestController
@RequestMapping("/jlQs")
public class JlQsWeb extends Result{

    @Autowired
    private JlQsService jlQsSQL;

    @RequestMapping("/findList")
    public String findList(JlQsVO model,Integer pageSize, Integer pageNum){
        List<JlQsVO> list = jlQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlQsVO model){
    	StringBuffer leftJoinTable = new StringBuffer();
    	StringBuffer leftJoinWhere = new StringBuffer();
    	String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
		JqRoleManager jqRoleManager = new JqRoleManager();
		String jqs = jqRoleManager.getJqs(token);
		if("admin".equals(jqs)){
		}else if(StringUtils.isBlank(jqs)){
			leftJoinWhere.append(" AND 1<>1 ");
		}else if(StringUtils.isNotBlank(jqs)){
			leftJoinTable.append(" LEFT JOIN JL_FR AS b ON a.FR_No=b.FR_No");
			leftJoinWhere.append(" AND b.JQ in("+jqs+")");
		}
		model.setLeftJoinTable(leftJoinTable.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
        Integer count = jlQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsVO model = jlQsSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsVO model){
        jlQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsVO model){
        jlQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlQsSQL.deleteKey(id);
        return this.toResult();
    }

}
