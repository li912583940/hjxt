package com.sl.ue.service.sys.sqlImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysNoticeConfService;
import com.sl.ue.util.http.Result;

@Service("sysNoticeConfSQL")
public class SysNoticeConfServiceImpl extends BaseSqlImpl<SysNoticeConfVO> implements SysNoticeConfService{

	@Override
	public String noticeChange(Integer noticeValue) {
		Result result = new Result();
		List<SysNoticeConfVO> list = this.findList(new SysNoticeConfVO());
		SysNoticeConfVO model = list.get(0);
		model.setHjNotice(noticeValue);
		this.edit(model);
		return result.toResult();
	}

}
