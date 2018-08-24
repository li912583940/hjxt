package com.sl.ue.service.sys.sqlImpl;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.SysLog;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysLogService;

@Service("sysLogSQL")
public class SysLogServiceImpl extends BaseSqlImpl<SysLog> implements SysLogService{

}
