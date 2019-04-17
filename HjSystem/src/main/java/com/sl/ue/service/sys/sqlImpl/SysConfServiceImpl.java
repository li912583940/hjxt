package com.sl.ue.service.sys.sqlImpl;


import org.springframework.stereotype.Service;

import com.sl.ue.entity.sys.vo.SysConfVO;
import com.sl.ue.service.base.impl.BaseSqlImpl;
import com.sl.ue.service.sys.SysConfService;
import com.sl.ue.util.http.Result;

@Service("sysConfSQL")
public class SysConfServiceImpl extends BaseSqlImpl<SysConfVO> implements SysConfService{

	@Override
	public String editConf(SysConfVO sysConf) {
		Result result = new Result();
		if(sysConf.getId()==null){
			result.error(Result.error_102);
			return result.toResult();
		}
		this.edit(sysConf);
		return result.toResult();
	}

}
