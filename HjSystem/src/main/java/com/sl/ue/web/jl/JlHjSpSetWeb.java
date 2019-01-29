package com.sl.ue.web.jl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.vo.JlHjSpSetVO;
import com.sl.ue.service.jl.JlHjSpSetService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlHjSpSet")
public class JlHjSpSetWeb extends Result{

    @Autowired
    private JlHjSpSetService jlHjSpSetSQL;

    @RequestMapping("/findList")
    public String findList(JlHjSpSetVO model,Integer pageSize, Integer pageNum){
        List<JlHjSpSetVO> list = jlHjSpSetSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlHjSpSetVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlHjSpSetSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlHjSpSetVO model){
        Integer count = jlHjSpSetSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlHjSpSetVO model = jlHjSpSetSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlHjSpSetVO model){
        jlHjSpSetSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlHjSpSetVO model){
        jlHjSpSetSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        jlHjSpSetSQL.deleteKey(id);
        return this.toResult();
    }

    
    /**
     * 说明 [查看审批详情]
     * @param id
     * @return
     * L_晓天  @2019年1月13日
     */
    @RequestMapping("/findDetails")
    public String findDetails(Integer id){
        return jlHjSpSetSQL.findDetails(id);
    }

    
    /**
     * 说明 [审批设置配置]
     * @return
     * L_晓天  @2019年1月13日
     */
    @RequestMapping("/spConf")
    public String spConf(Integer id, String spExplain, Integer usable,
    		String gxValue,
    		String deptValue1, String userValue1,
    		String deptValue2, String userValue2,
    		String deptValue3, String userValue3){
        jlHjSpSetSQL.spConf(id, spExplain, usable, 
        		gxValue,
        		deptValue1, userValue1,
        		deptValue2, userValue2,
        		deptValue3, userValue3);
        return this.toResult();
    }
}
