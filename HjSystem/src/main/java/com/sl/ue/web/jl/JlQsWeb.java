package com.sl.ue.web.jl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlHjSpSetVO;
import com.sl.ue.entity.jl.vo.JlHjSpVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.jl.vo.JlQsSpVO;
import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.entity.sys.vo.SysLogVO;
import com.sl.ue.entity.sys.vo.SysUserVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlHjSpService;
import com.sl.ue.service.jl.JlHjSpSetService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.service.jl.JlQsSpService;
import com.sl.ue.service.sys.SysLogService;
import com.sl.ue.util.Config;
import com.sl.ue.util.Constants;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.anno.IgnoreSecurity;
import com.sl.ue.util.http.Result;
import com.sl.ue.util.http.WebContextUtil;
import com.sl.ue.util.http.token.JqRoleManager;
import com.sl.ue.util.http.token.TokenUser;

@RestController
@RequestMapping("/jlQs")
public class JlQsWeb extends Result{

    @Autowired
    private JlQsService jlQsSQL;
    @Autowired
    private JlFrService jlFrSQL;
    @Autowired
    private JlJqService jlJqSQL;
    @Autowired
	private SysLogService sysLogSQL;
    @Autowired
    private JlHjSpSetService jlHjSpSetSQL;
    @Autowired
    private JlHjSpService jlHjSpSQL;
    @Autowired
    private JlQsSpService jlQsSpSQL;

    
    @RequestMapping("/findList")
    public String findList(JlQsVO model,Integer pageSize, Integer pageNum){
        List<JlQsVO> list = jlQsSQL.findList(model, pageSize, pageNum);
        this.putData(list);
        return this.toResult();
    }

    @RequestMapping("/findPojo")
    public String findPojo(JlQsVO model, Integer pageSize, Integer pageNum){
        Map<String, Object> map = jlQsSQL.findPojoJoin(model, pageSize, pageNum);
        this.putPojo(map);
        return this.toResult();
    }

    @RequestMapping("/findCount")
    public String findCount(JlQsVO model){
    	StringBuffer leftJoinTable = new StringBuffer();
    	StringBuffer leftJoinWhere = new StringBuffer();
    	String token = WebContextUtil.getRequest().getHeader(Constants.TOKEN_NAME);
		JqRoleManager jqRoleManager = new JqRoleManager();
		String jqs = jqRoleManager.getJqs(token);
		if("admin".equals(jqs)){
		}else if(StringUtils.isBlank(jqs)){
			leftJoinWhere.append(" AND 1<>1 ");
		}else if(StringUtils.isNotBlank(jqs)){
			leftJoinTable.append(" LEFT JOIN JL_FR AS b ON a.FR_No=b.FR_No");
			leftJoinWhere.append(" AND b.JQ in("+jqs+")");
		}
		model.setLeftJoinTable(leftJoinTable.toString());
		model.setLeftJoinWhere(leftJoinWhere.toString());
        Integer count = jlQsSQL.count(model);
        this.putJson("count", count);
        return this.toResult();
    }

    @RequestMapping("/findOne")
    public String findOne(Integer id){
        JlQsVO model = jlQsSQL.findOne(id);
        JlFrVO jlFr = new JlFrVO();
        jlFr.setFrNo(model.getFrNo());
        List<JlFrVO> jrFrList = jlFrSQL.findList(jlFr);
        jlFr = jrFrList.get(0);
        model.setFrName(jlFr.getFrName());
        this.putJson(model);
        return this.toResult();
    }

    @RequestMapping("/add")
    public String add(JlQsVO model){
    	if(jlQsSQL.qsExist(model.getFrNo(), model.getQsSfz())){
			this.error(error_103, "当前亲属证件号码已绑定此罪犯");
			return this.toResult();
		}
    	if(StringUtils.isNotBlank(model.getJzBase64())){
    		byte[] b = Base64.getDecoder().decode(model.getJzBase64());
    		ByteArrayInputStream bais = new ByteArrayInputStream(b);
    		BufferedImage bi1;
    		String qsJzImage = Config.getPropertiesValue("file.path");
    		String qs_jz = "/qs_jz";
			try {
				File f = new File(qsJzImage+qs_jz);
				if(!f.exists()){
					f.mkdirs();
				}
				String relativeUrl="/qs_jz/"+System.currentTimeMillis()+".png";
				bi1 = ImageIO.read(bais);
				File w2 = new File(qsJzImage+relativeUrl);
	            ImageIO.write(bi1, "png", w2);
	            model.setJzUrl(relativeUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	if(StringUtils.isNotBlank(model.getZpBase64())){
    		byte[] b = Base64.getDecoder().decode(model.getZpBase64());
    		ByteArrayInputStream bais = new ByteArrayInputStream(b);
    		BufferedImage bi1;
    		String qsZpImage = Config.getPropertiesValue("file.path");
    		String qs_zp = "/qs_zp";
			try {
				File f = new File(qsZpImage+qs_zp);
				if(!f.exists()){
					f.mkdirs();
				}
				String relativeUrl="/qs_zp/"+System.currentTimeMillis()+".png";
				bi1 = ImageIO.read(bais);
				File w2 = new File(qsZpImage+relativeUrl);
	            ImageIO.write(bi1, "png", w2);
	            model.setZpUrl(relativeUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	// 查看审批亲属关系是否开启
    	if(StringUtils.isNotBlank(model.getGx())){
    		JlHjSpSetVO jlHjSpSet = new JlHjSpSetVO();
    		jlHjSpSet.setUsable(1);
    		jlHjSpSet.setSpNo("6");
    		List<JlHjSpSetVO> list = jlHjSpSetSQL.findList(jlHjSpSet);
    		if(list.size()>0){
    			jlHjSpSet = list.get(0);
    			if(StringUtils.isNotBlank(jlHjSpSet.getSpValue())){
    				List<String> spGx = Arrays.asList(jlHjSpSet.getSpValue().split(","));
    				if(spGx.contains(model.getGx())){
    					JlQsSpVO jlQsSp  = JSONObject.parseObject(JSONObject.toJSONString(model), JlQsSpVO.class);
    					jlQsSp.setState(0);
    					jlQsSp = jlQsSpSQL.add(jlQsSp);
    					
    					JlHjSpVO jlHjSp = new JlHjSpVO();
    					jlHjSp.setQsId(jlQsSp.getWebid());
    					jlHjSp.setType(2);
    					jlHjSp.setSetNo(jlHjSpSet.getSpNo());
    					jlHjSp.setSetName(jlHjSpSet.getSpName());
    					jlHjSp.setExplain(jlHjSpSet.getSpName());
    					jlHjSp.setMaxNum(jlHjSpSet.getMaxNum());
    					jlHjSp.setSpeedProgress(1);
    					jlHjSp.setState(0);
    					jlHjSp.setFrNo(model.getFrNo());
    					
    					JlFrVO jlFr = new JlFrVO();
    					jlFr.setFrNo(model.getFrNo());
    					List<JlFrVO> jlFrList = jlFrSQL.findList(jlFr);
    					if(jlFrList.size()>0){
    						jlFr = jlFrList.get(0);
    						jlHjSp.setFrName(jlFr.getFrName());
    						jlHjSp.setJqNo(jlFr.getJq());
    						
    						JlJqVO jlJq = new JlJqVO();
    						jlJq.setJqNo(jlFr.getJq());
    						List<JlJqVO> jlJqList = jlJqSQL.findList(jlJq);
    						if(jlJqList.size()>0){
    							jlJq = jlJqList.get(0);
    							jlHjSp.setJqName(jlJq.getJqName());
    							jlHjSp.setQsInfo(model.getQsName());
    							jlHjSp.setTjTime(new Date());
    						}
    					}
    					jlHjSpSQL.add(jlHjSp);
    					this.msg("添加亲属成功，但此家属需要审批通过之后才能入库。");
    					return this.toResult();
    				}
    			}
    		}
    	}
    	SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		sysLog.setType("正常");
		sysLog.setOp("添加亲属信息");
		sysLog.setInfo("为罪犯编号: "+model.getFrNo()+"添加亲属。亲属姓名: "+model.getQsName()+"。");
		sysLog.setModel("亲属管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
		
    	
        jlQsSQL.add(model);
        return this.toResult();
    }

    @RequestMapping("/edit")
    public String edit(JlQsVO model){
    	JlQsVO oldJlQs = new JlQsVO();
    	if(StringUtils.isNotBlank(model.getQsSfz())){
    		oldJlQs = jlQsSQL.findOne(model.getWebId()); //之前的家属
    		if(StringUtils.isNotBlank(model.getEnclosureUrl())){
    			if(StringUtils.isNotBlank(oldJlQs.getEnclosureUrl())){
        			String startPath = Config.getPropertiesValue("file.path");
        			File file =new File(startPath+oldJlQs.getEnclosureUrl());
        			if(file.exists() && file.isFile()){
        				file.delete();
        			}
        		}
    		}
    		
    		if(oldJlQs.getFrNo().equals(model.getFrNo()) && oldJlQs.getQsSfz().equals(model.getQsSfz())){
    			
    		}else{
    			if(jlQsSQL.qsExist(model.getFrNo(), model.getQsSfz())){
    				this.error(error_103, "当前亲属证件号码已绑定此罪犯");
    				return this.toResult();
    			}
    		}
    	}
    	
    	if(StringUtils.isNotBlank(model.getJzBase64())){
    		byte[] b = Base64.getDecoder().decode(model.getJzBase64());
    		ByteArrayInputStream bais = new ByteArrayInputStream(b);
    		BufferedImage bi1;
    		String qsJzImage = Config.getPropertiesValue("file.path");
    		String qs_jz = "/qs_jz";
			try {
				File f = new File(qsJzImage+qs_jz);
				if(!f.exists()){
					f.mkdirs();
				}
				String relativeUrl="/qs_jz/"+System.currentTimeMillis()+".png";
				bi1 = ImageIO.read(bais);
				File w2 = new File(qsJzImage+relativeUrl);
	            ImageIO.write(bi1, "png", w2);
	            model.setJzUrl(relativeUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	if(StringUtils.isNotBlank(model.getZpBase64())){
    		byte[] b = Base64.getDecoder().decode(model.getZpBase64());
    		ByteArrayInputStream bais = new ByteArrayInputStream(b);
    		BufferedImage bi1;
    		String qsZpImage = Config.getPropertiesValue("file.path");
    		String qs_zp = "/qs_zp";
			try {
				File f = new File(qsZpImage+qs_zp);
				if(!f.exists()){
					f.mkdirs();
				}
				String relativeUrl="/qs_zp/"+System.currentTimeMillis()+".png";
				bi1 = ImageIO.read(bais);
				File w2 = new File(qsZpImage+relativeUrl);
	            ImageIO.write(bi1, "png", w2);
	            model.setZpUrl(relativeUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    	// 查看审批亲属关系是否开启
    	if(StringUtils.isNotBlank(model.getGx())){
    		if(oldJlQs.getGx().equals(model.getGx())){
    			
    		}else{
    			jlQsSQL.deleteKey(model.getWebId());
    			
    			JlHjSpSetVO jlHjSpSet = new JlHjSpSetVO();
        		jlHjSpSet.setUsable(1);
        		jlHjSpSet.setSpNo("6");
        		List<JlHjSpSetVO> list = jlHjSpSetSQL.findList(jlHjSpSet);
        		if(list.size()>0){
        			jlHjSpSet = list.get(0);
        			if(StringUtils.isNotBlank(jlHjSpSet.getSpValue())){
        				List<String> spGx = Arrays.asList(jlHjSpSet.getSpValue().split(","));
        				if(spGx.contains(model.getGx())){
        					JlQsSpVO jlQsSp = new JlQsSpVO();
        					jlQsSp.setFrNo(model.getFrNo());
        					jlQsSp.setQsZjlb(model.getQsZjlb());
        					jlQsSp.setQsSfz(model.getQsSfz());
        					jlQsSp.setQsName(model.getQsName());
        					jlQsSp.setQsCard(model.getQsCard());
        					jlQsSp.setGx(model.getGx());
        					jlQsSp.setXb(model.getXb());
        					jlQsSp.setDz(model.getDz());
        					jlQsSp.setZp(model.getZp());
        					jlQsSp.setJz(model.getJz());
        					jlQsSp.setZpUrl(model.getZpUrl());
        					jlQsSp.setJzUrl(model.getJzUrl());
        					jlQsSp.setState(0);
        					jlQsSp = jlQsSpSQL.add(jlQsSp);
        					
        					JlHjSpVO jlHjSp = new JlHjSpVO();
        					jlHjSp.setQsId(jlQsSp.getWebid());
        					jlHjSp.setType(2);
        					jlHjSp.setSetNo(jlHjSpSet.getSpNo());
        					jlHjSp.setSetName(jlHjSpSet.getSpName());
        					jlHjSp.setExplain(jlHjSpSet.getSpName());
        					jlHjSp.setMaxNum(jlHjSpSet.getMaxNum());
        					jlHjSp.setSpeedProgress(1);
        					jlHjSp.setState(0);
        					jlHjSp.setFrNo(model.getFrNo());
        					
        					JlFrVO jlFr = new JlFrVO();
        					jlFr.setFrNo(model.getFrNo());
        					List<JlFrVO> jlFrList = jlFrSQL.findList(jlFr);
        					if(jlFrList.size()>0){
        						jlFr = jlFrList.get(0);
        						jlHjSp.setFrName(jlFr.getFrName());
        						jlHjSp.setJqNo(jlFr.getJq());
        						
        						JlJqVO jlJq = new JlJqVO();
        						jlJq.setJqNo(jlFr.getJq());
        						List<JlJqVO> jlJqList = jlJqSQL.findList(jlJq);
        						if(jlJqList.size()>0){
        							jlJq = jlJqList.get(0);
        							jlHjSp.setJqName(jlJq.getJqName());
        							jlHjSp.setQsInfo(model.getQsName());
        							jlHjSp.setTjTime(new Date());
        						}
        					}
        					jlHjSpSQL.add(jlHjSp);
        					this.msg("编辑亲属时，关系发生变化，此关系需要审批通过之后才能入库");
        					return this.toResult();
        				}
        			}
        		}
    		}
    		
    	}
    	
    	SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		sysLog.setType("正常");
		sysLog.setOp("编辑亲属信息");
		sysLog.setInfo("为罪犯编号: "+model.getFrNo()+"编辑亲属。亲属姓名: "+model.getQsName()+"。");
		sysLog.setModel("亲属管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
		
        jlQsSQL.edit(model);
        return this.toResult();
    }

    @RequestMapping("/delete")
    public String del(Integer id){
    	JlQsVO model = jlQsSQL.findOne(id);
    	SysUserVO user = TokenUser.getUser();
		SysLogVO sysLog = new SysLogVO();
		sysLog.setType("严重");
		sysLog.setOp("删除亲属信息");
		sysLog.setInfo("为罪犯编号: "+model.getFrNo()+"删除亲属。罪犯姓名: "+model.getQsName()+".");
		sysLog.setModel("亲属管理");
		sysLog.setUserNo(user.getUserNo());
		sysLog.setUserName(user.getUserName());
		sysLog.setLogTime(DateUtil.getDefaultNow());
		sysLogSQL.add(sysLog);
		
        jlQsSQL.deleteKey(id);
        return this.toResult();
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(JlQsVO model,
    		HttpServletRequest request, HttpServletResponse response) {
    	jlQsSQL.exportExcel(model, request, response);
    }
    
    @RequestMapping(value="/importExcel",method={RequestMethod.GET,RequestMethod.POST})
    @IgnoreSecurity
    public String importExcel(HttpServletRequest request, HttpServletResponse response){
    	return jlQsSQL.importExcel(request, response);
    }
    
    @RequestMapping(value="/uploadWord",method={RequestMethod.GET,RequestMethod.POST})
    @IgnoreSecurity
    public String uploadWord(HttpServletRequest request, HttpServletResponse response){
    	return jlQsSQL.uploadWord(request, response);
    }
    
    @RequestMapping("/wordDownload")
    public void wordDownload(JlQsVO model,
    		HttpServletRequest request, HttpServletResponse response) {
    	jlQsSQL.wordDownload(model, request, response);
    }
    
}
