package com.sl.ue.web.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjDjVO;
import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.entity.sys.vo.SysHjLineVO;
import com.sl.ue.entity.sys.vo.SysNoticeConfVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjDjService;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.service.sys.SysHjLineService;
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
	@Autowired
	private JlQsService jlQsSQL;
	@Autowired
	private SysHjLineService sysHjLineSQL;
	
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
			JSONObject json = new JSONObject();
			if(jlFrList.size()>0){
				jlFr = jlFrList.get(0);
				JlJqVO jlJq = new JlJqVO();
				jlJq.setJqNo(jlFr.getJq());
				List<JlJqVO> jlJqList = jlJqSQL.findList(jlJq);
				if(jlJqList.size()>0){
					jlJq = jlJqList.get(0);
					json.put("jqName", jlJq.getJqName());
				}
				JlJbVO jlJb = new JlJbVO();
				jlJb.setJbNo(jlFr.getJbNo());
				List<JlJbVO> jlJbList = jlJbSQL.findList(jlJb);
				if(jlJbList.size()>0){
					jlJb = jlJbList.get(0);
					json.put("jbName", jlJb.getJbName());
				}
				
				
				json.put("frNo", jlFr.getFrNo());
				json.put("frName", jlFr.getFrName());
				json.put("infoZm", jlFr.getInfoZm());
				if(jlHjDj.getFpLineNo()!=null){
					SysHjLineVO sysHjLine = new SysHjLineVO();
					sysHjLine.setLineNo(jlHjDj.getFpLineNo());
					List<SysHjLineVO> sysHjLineList = sysHjLineSQL.findList(sysHjLine);
					if(sysHjLineList.size()>0){
						sysHjLine = sysHjLineList.get(0);
						json.put("zw", sysHjLine.getZw());
					}
				}
			}
			this.putJson("jlFr", json);
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
		
		JlQsVO jlQs = new JlQsVO();
		jlQs.setQsSfz(qsSfz);
		List<JlQsVO> jlQsList = jlQsSQL.findList(jlQs);
		if(jlQsList.size()>0){
			this.putJson("jlQs", jlQsList.get(0));
		}
        return this.toResult();
    }
}
