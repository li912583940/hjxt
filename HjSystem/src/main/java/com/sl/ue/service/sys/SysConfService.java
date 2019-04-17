package com.sl.ue.service.sys;

import com.sl.ue.entity.sys.vo.SysConfVO;
import com.sl.ue.service.base.BaseService;

public interface SysConfService extends BaseService<SysConfVO>{

	public String editConf(SysConfVO sysConf);
}