package com.sl.ue.web.jl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.entity.jl.JlYjABDoor;
import com.sl.ue.service.jl.JlYjABDoorService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/jlYjABDoor")
public class JlYjABDoorWeb extends Result{

    @Autowired
    private JlYjABDoorService JlYjABDoorSQL;

    @RequestMapping("/findList")
    public String findList(Integer pageSize, Integer pageNum){
        JlYjABDoor model = new JlYjABDoor();
        List<JlYjABDoor> list = JlYjABDoorSQL.baseFindList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlYjABDoor model = JlYjABDoorSQL.baseFindOne(id);
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlYjABDoor model){
        JlYjABDoorSQL.baseAdd(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlYjABDoor model){
        JlYjABDoorSQL.baseEdit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
        JlYjABDoorSQL.baseDeleteKey(id);
        return this.toResult();
    }

}
