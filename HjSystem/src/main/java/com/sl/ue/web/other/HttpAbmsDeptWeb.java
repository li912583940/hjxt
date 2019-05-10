package com.sl.ue.web.other;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.other.vo.HttpAbmsDeptVO;
import com.sl.ue.service.other.HttpAbmsDeptService;
import com.sl.ue.util.anno.IgnoreSecurity;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/httpAbmsDept")
public class HttpAbmsDeptWeb extends Result{

    @Autowired
    private HttpAbmsDeptService httpAbmsDeptSQL;

    @RequestMapping("/findList")
    public String findList(HttpAbmsDeptVO model,Integer pageSize, Integer pageNum){
        List<HttpAbmsDeptVO> list = httpAbmsDeptSQL.findList(model, pageSize, pageNum, "ASC");
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(HttpAbmsDeptVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = httpAbmsDeptSQL.findPojo(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(HttpAbmsDeptVO model){
        Integer count = httpAbmsDeptSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(String id){
        HttpAbmsDeptVO model = httpAbmsDeptSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HttpAbmsDeptVO model){
        httpAbmsDeptSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HttpAbmsDeptVO model){
        httpAbmsDeptSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(String id){
        httpAbmsDeptSQL.deleteKey(id);
        return this.toResult();
    }

    /**
     * 说明 [获取AB门所有部门信息]
     * @return
     * L_晓天  @2019年5月6日
     */
    @RequestMapping("/syncAbmsDept")
    @IgnoreSecurity
    public String syncAbmsDept(){
    	return httpAbmsDeptSQL.syncAbmsDept();
    }
    
    /**
     * 说明 [将会见信息发送给AB门]
     * @return
     * L_晓天  @2019年5月6日
     */
    @RequestMapping("/httpToAbmsHjDj")
    @IgnoreSecurity
    public String httpToAbmsHjDj(Long hjid){
    	return httpAbmsDeptSQL.httpToAbmsHjDj(hjid);
    }
}
