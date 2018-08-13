package com.sl.ue.service.sys.sqlImpl;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.service.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysUserService;

@Service("SysUserSQL")
public class SysUserServiceImpl extends BaseSqlImpl<SysUser> implements SysUserService{

}
