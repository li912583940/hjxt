package com.sl.ue.service.other.sqlImpl;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.other.vo.DeptVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.other.DeptService;

@Service("deptSQL")
public class DeptServiceImpl extends BaseSqlImpl<DeptVO> implements DeptService{

}
