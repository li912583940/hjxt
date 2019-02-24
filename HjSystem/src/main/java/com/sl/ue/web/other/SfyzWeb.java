package com.sl.ue.web.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysNoticeConfService;
import com.sl.ue.util.http.Result;

@RestController
@RequestMapping("/sfyz")
public class SfyzWeb  extends Result{

	@Autowired
    private JlHjDjService jlHjDjSQL;
	@Autowired
	private JlFrService jlFrSQL;
	@Autowired
	private JlJqService jlJqSQL;
	@Autowired
	private JlJbService jlJbSQL;
	@Autowired
	private SysNoticeConfService sysNoticeConfSQL;
	
	@RequestMapping("/djYz")
    public String djYz(String qsSfz){
		JlHjDjVO jlHjDj = new JlHjDjVO();
		jlHjDj.setState(0);
		StringBuffer leftJoinTable = new StringBuffer();
		leftJoinTable.append(" LEFT JOIN JL_HJ_DJ_QS AS b ON a.HJID=b.HJID");
		jlHjDj.setLeftJoinTable(leftJoinTable.toString());
		StringBuffer leftJoinWhere = new StringBuffer();
		leftJoinWhere.append(" AND b.QS_SFZ='"+qsSfz+"'");
		jlHjDj.setLeftJoinWhere(leftJoinWhere.toString());
		List<JlHjDjVO> jlHjDjList = jlHjDjSQL.findList(jlHjDj);
		if(jlHjDjList.size()>0){
			jlHjDj = jlHjDjList.get(0);
			JlFrVO jlFr = new JlFrVO();
			jlFr.setFrNo(jlHjDj.getFrNo());
			List<JlFrVO> jlFrList = jlFrSQL.findList(jlFr);
			for(JlFrVO t : jlFrList){
				JlJqVO jlJq = new JlJqVO();
				jlJq.setJqNo(t.getJq());
				List<JlJqVO> jlJqList = jlJqSQL.findList(jlJq);
				jlJq = jlJqList.get(0);
				t.setJqName(jlJq.getJqName());
				
				JlJbVO jlJb = new JlJbVO();
				jlJb.setJbNo(t.getJbNo());
				List<JlJbVO> jlJbList = jlJbSQL.findList(jlJb);
				jlJb = jlJbList.get(0);
				t.setJbName(jlJb.getJbName());
			}
			
			
			this.putData("jlFrList", jlFrList);
			this.putJson("state", 0);
			
			// 身份验证成功后发起会见通知
			List<SysNoticeConfVO> sysNoticeConfList = sysNoticeConfSQL.findList(new SysNoticeConfVO());
			int notice = 0; // 会见通知。 0：登记完自动发起。1：身份验证成功后发起
			if(sysNoticeConfList.size()>0){
				SysNoticeConfVO sysNoticeConf = sysNoticeConfList.get(0);
				notice = sysNoticeConf.getHjNotice();
			}
			if(notice==1){
				jlHjDj.setPageTzMode(0);
				jlHjDjSQL.edit(jlHjDj);
			}
		}else{
			this.putJson("state", 1);
		}
        return this.toResult();
    }
}
