package com.sl.ue.web.hj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.hj.vo.HjdjAcdLogVO;
import com.sl.ue.service.hj.HjdjAcdLogService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/hjdjAcdLog")
public class HjdjAcdLogWeb extends Result{

    @Autowired
    private HjdjAcdLogService hjdjAcdLogSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        HjdjAcdLogVO model = new HjdjAcdLogVO();
        List<HjdjAcdLogVO> list = hjdjAcdLogSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        HjdjAcdLogVO model = hjdjAcdLogSQL.findOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(HjdjAcdLogVO model){
        hjdjAcdLogSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(HjdjAcdLogVO model){
        hjdjAcdLogSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        hjdjAcdLogSQL.deleteKey(id);
        return this.toResult();
    }

}
