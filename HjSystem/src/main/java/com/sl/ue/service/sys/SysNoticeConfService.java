package com.sl.ue.service.sys;

import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.service.base.BaseService;

public interface SysNoticeConfService extends BaseService<SysNoticeConfVO>{

	public String noticeChange(Integer noticeValue);
}