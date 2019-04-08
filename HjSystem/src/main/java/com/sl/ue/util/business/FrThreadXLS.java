package com.sl.ue.util.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysHjServerVO;
import com.sl.ue.entity.sys.vo.SysServerVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysHjServerService;
import com.sl.ue.service.sys.SysServerService;
import com.sl.ue.util.DateUtil;
import com.sl.ue.util.ExcelUtil;
import com.sl.ue.util.component.SpringTool;

/**
 * 说明 [罪犯excel导入线程--后缀为xls的excel表格]
 * L_晓天  @2018年12月19日
 */
public class FrThreadXLS implements Runnable {

	private Thread thread;
	private String path;

	public FrThreadXLS(String path) {
		this.path = path;
	}
	public void start() {
		if(thread == null){
			thread = new Thread(this, path);
			thread.start();
		}
	}
	@Override
	public void run() {
		JlFrService jlFrSQL = (JlFrService) SpringTool.getBean("jlFrSQL");
		/** servername 开始 */
		SysHjServerService sysHjServerSQL = (SysHjServerService) SpringTool.getBean("sysHjServerSQL");
		List<SysHjServerVO> sysHjServerList = sysHjServerSQL.findList(new SysHjServerVO(),null,null, "ASC");
		String serverName = "Server1";
		if(sysHjServerList.size()>0)serverName = sysHjServerList.get(0).getServerName();
		/** servername 结束 */
		
		/** 监区 开始 */
		JlJqService jlJqSQL = (JlJqService) SpringTool.getBean("jlJqSQL");
		List<JlJqVO> jlJqList = jlJqSQL.findList(new JlJqVO());
		/** 监区 结束 */
		
		/** 级别 开始 */
		JlJbService jlJbSQL = (JlJbService) SpringTool.getBean("jlJbSQL");
		List<JlJbVO> jlJbList = jlJbSQL.findList(new JlJbVO());
		/** 级别 结束 */
		
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int sheets = workbook.getNumberOfSheets();
		for(int i=0;i<sheets;i++){
			HSSFSheet sheet = workbook.getSheetAt(i); // 读取工作表
			if(sheet == null){
				break;
			}
			
			int index = 1;
			while (true) {
				HSSFRow row = sheet.getRow(index);
				if(row == null){
					break;
				}
				// 0罪犯编号，1姓名，2监区名称，3级别，4罪名，5刑期，6入监时间，7重点罪犯，8国籍，9籍贯，10出生日期，11家庭住址
				JlFrVO jlFr = new JlFrVO();
				
				String frNo = ExcelUtil.getCellValue(row.getCell(0)); //0罪犯编号
				String frName = ExcelUtil.getCellValue(row.getCell(1)); //1姓名
				if(StringUtils.isBlank(frNo) || StringUtils.isBlank(frName)){
					
				}else{
					jlFr.setFrNo(frNo); //0罪犯编号
					List<JlFrVO> list = jlFrSQL.findList(jlFr);
					if(list.size()>0){
					}else{
						jlFr.setFrName(frName); //1姓名
						jlFr.setJy(serverName);
						String jq = ExcelUtil.getCellValue(row.getCell(2)); //2监区名称
						jlFr.setJq("-1");
						jlFr.setJqName("未定义");
						for(JlJqVO t : jlJqList){
							if(StringUtils.isNotBlank(jq) && jq.equals(t.getJqName())){
								jlFr.setJq(t.getJqNo());
								jlFr.setJqName(t.getJqName());
							}
						}
						
						String jb = ExcelUtil.getCellValue(row.getCell(3)); //3级别
						jlFr.setJbNo("-1");
						jlFr.setJbName("未定义");
						for(JlJbVO t : jlJbList){
							if(StringUtils.isNotBlank(jb) && jb.equals(t.getJbName())){
								jlFr.setJbNo(t.getJbNo());
								jlFr.setJbName(t.getJbName());
							}
						}
						jlFr.setInfoZm(ExcelUtil.getCellValue(row.getCell(4)));   //4罪名
						jlFr.setInfoXq(ExcelUtil.getCellValue(row.getCell(5)));   //5刑期
						jlFr.setInfoRjsj(ExcelUtil.getCellValue(row.getCell(6))); //6入监日期
						String zdzf = ExcelUtil.getCellValue(row.getCell(7)); //7重点罪犯
						if(StringUtils.isNotBlank(zdzf)){
							zdzf = zdzf.trim();
							if(zdzf.equals("重点罪犯")){
								jlFr.setStateZdzf(1);
								jlFr.setInfoZdzf("");
							}else if(zdzf.equals("非重点罪犯")){
								jlFr.setStateZdzf(0);
								jlFr.setInfoZdzf("");
							}else{
								jlFr.setStateZdzf(1);
								jlFr.setInfoZdzf(zdzf);
							}
						}else{
							jlFr.setStateZdzf(0);
							jlFr.setInfoZdzf("");
						}
						jlFr.setFrGj(ExcelUtil.getCellValue(row.getCell(8))); //8国籍
						jlFr.setInfoJg(ExcelUtil.getCellValue(row.getCell(9)));   //9籍贯
						jlFr.setInfoCsrq(ExcelUtil.getCellValue(row.getCell(10))); //10出生日期
						jlFr.setInfoHome(ExcelUtil.getCellValue(row.getCell(11))); //11家庭住址
						jlFrSQL.add(jlFr);
					}
				}
				index++;
			}
		}
		
		//最后删除文件
		File file = new File(path);
		if(file.exists() && file.isFile())file.delete();
	}
	
	
	
}
