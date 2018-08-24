package com.sl.ue.service.sys.sqlImpl;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysUserService;

@Service("sysUserSQL")
public class SysUserServiceImpl extends BaseSqlImpl<SysUserVO> implements SysUserService{

}
