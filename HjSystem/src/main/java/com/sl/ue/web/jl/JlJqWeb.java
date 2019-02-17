package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlJq")
public class JlJqWeb extends Result{

    @Autowired
    private JlJqService jlJqSQL;
    @Autowired
    private SysHjServerService sysHjServerSQL;

    @RequestMapping("/findList")
    public String findList(JlJqVO model,Integer pageSize, Integer pageNum){
        List<JlJqVO> list = jlJqSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlJqVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlJqSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlJqVO model){
        Integer count = jlJqSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlJqVO model = jlJqSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlJqVO model){
    	//查看监区编号或者监区名称是否存在
    	if(StringUtils.isNotBlank(model.getJqNo())){
    		JlJqVO jlJqQuery = new JlJqVO();
    		jlJqQuery.setJqNo(model.getJqNo());
    		if(StringUtils.isNotBlank(model.getJqName())){
    			jlJqQuery.setLeftJoinWhere(" OR a.JQ_Name='"+model.getJqName()+"'");
    		}
    		List<JlJqVO> jlJqList = jlJqSQL.findList(jlJqQuery);
    		if(jlJqList.size()>0){
    			this.error(error_102, "监区编号或监区名称不能重复");
    			return this.toResult();
    		}
    	}
    	
    	SysHjServerVO sysHjServer = new SysHjServerVO();
    	List<SysHjServerVO> list = sysHjServerSQL.findList(sysHjServer);
    	if(list.size()>0){
    		model.setJy(list.get(0).getServerName());
    	}else{
    		model.setJy("Server1");
    	}
        jlJqSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlJqVO model){
    	JlJqVO jlJqQuery = new JlJqVO();
    	jlJqQuery.setLeftJoinWhere(" AND a.WebID<>"+model.getWebId()+" AND (a.JQ_No='"+model.getJqNo()+"'"
    			+" OR a.JQ_Name='"+model.getJqName()+"')");
    	List<JlJqVO> list = jlJqSQL.findList(jlJqQuery);
    	if(list.size()>0){
    		this.error(error_102, "监区编号或监区名称不能重复");
			return this.toResult();
    	}
        jlJqSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlJqSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [获取当前监区选中的会见星期日]
     * L_晓天  @2018年11月2日
     */
    @RequestMapping("/getCheckedWeek")
    public String getCheckedWeek(String jqNo){
    	return jlJqSQL.getCheckedWeek(jqNo);
    }
    
    /**
     * 说明 [提交当前监区的会见星期日]
     * L_晓天  @2018年11月2日
     */
    @RequestMapping("/addJqWeek")
    public String addJqWeek(String jqNo, String weeks){
    	return jlJqSQL.addJqWeek(jqNo, weeks);
    }

}
