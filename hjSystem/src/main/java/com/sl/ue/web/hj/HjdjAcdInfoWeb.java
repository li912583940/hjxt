package com.sl.ue.web.hj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sl.ue.service.hj.HjdjAcdInfoService;

@Controller
@RequestMapping("/hjdjAcdInfo")
public class HjdjAcdInfoWeb {

	@Autowired
	private HjdjAcdInfoService hjdjAcdInfoService;
}
