package com.sl.ue.service.jl;

import com.sl.ue.entity.jl.vo.JlHjSpSetVO;
import com.sl.ue.service.base.BaseService;

public interface JlHjSpSetService extends BaseService<JlHjSpSetVO>{

	public String findDetails(Integer id);
	
	public String spConf(Integer id, String spExplain, Integer usable,
			String gxValue,
    		String deptValue1, String userValue1,
    		String deptValue2, String userValue2,
    		String deptValue3, String userValue3);
}