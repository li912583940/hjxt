package com.sl.ue.web.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sfyz")
public class SfyzWeb  extends Result{

	@Autowired
    private JlHjDjService jlHjDjSQL; 
	
	@RequestMapping("/djYz")
    public String djYz(String qsSfz){
        
        return this.toResult();
    }
}
