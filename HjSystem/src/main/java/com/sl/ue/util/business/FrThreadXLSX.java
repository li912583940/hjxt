package com.sl.ue.util.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sl.ue.entity.jl.vo.JlFrVO;
import com.sl.ue.entity.jl.vo.JlJbVO;
import com.sl.ue.entity.jl.vo.JlJqVO;
import com.sl.ue.entity.sys.vo.SysServerVO;
import com.sl.ue.service.jl.JlFrService;
import com.sl.ue.service.jl.JlJbService;
import com.sl.ue.service.jl.JlJqService;
import com.sl.ue.service.sys.SysServerService;
import com.sl.ue.util.component.SpringTool;

/**
 * 说明 [罪犯excel导入线程--后缀为xlsx的excel表格]
 * L_晓天  @2018年12月20日
 */
public class FrThreadXLSX implements Runnable{

	private Thread thread;
	private String path;

	public FrThreadXLSX(String path) {
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
		SysServerService sysServerSQL = (SysServerService) SpringTool.getBean("sysServerSQL");
		List<SysServerVO> sysServerList = sysServerSQL.findList(new SysServerVO());
		String serverName = "Server1";
		if(sysServerList.size()>0)serverName = sysServerList.get(0).getServerName();
		/** servername 结束 */
		
		/** 监区 开始 */
		JlJqService jlJqSQL = (JlJqService) SpringTool.getBean("jlJqSQL");
		List<JlJqVO> jlJqList = jlJqSQL.findList(new JlJqVO());
		/** 监区 结束 */
		
		/** 级别 开始 */
		JlJbService jlJbSQL = (JlJbService) SpringTool.getBean("jlJbSQL");
		List<JlJbVO> jlJbList = jlJbSQL.findList(new JlJbVO());
		/** 级别 结束 */
		
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int sheets = workbook.getNumberOfSheets();
		for(int i=0;i<sheets;i++){
			XSSFSheet sheet = workbook.getSheetAt(i); // 读取工作表
			if(sheet == null){
				break;
			}
			
			int index = 1;
			while (true) {
				XSSFRow row = sheet.getRow(index);
				if(row == null){
					break;
				}
				// 0罪犯编号，1姓名，2监区名称，3籍贯，4出生日期，5家庭住址，6罪名，7刑期，8入监日期，9犯人级别
				JlFrVO jlFr = new JlFrVO();
				String frNo = row.getCell(0).getStringCellValue(); //0罪犯编号
				jlFr.setFrNo(frNo!=null?frNo.trim():null);
				if(StringUtils.isNotBlank(frNo)){
					List<JlFrVO> list = jlFrSQL.findList(jlFr);
					if(list.size()>0){
					}else{
						jlFr.setFrName(row.getCell(1).getStringCellValue()); //1姓名
						jlFr.setJy(serverName);
						String jq = row.getCell(2).getStringCellValue(); //2监区名称
						jlFr.setJq("-1");
						jlFr.setJqName("未定义");
						for(JlJqVO t : jlJqList){
							if(StringUtils.isNotBlank(jq) && jq.equals(t.getJqName())){
								jlFr.setJq(t.getJqNo());
								jlFr.setJqName(t.getJqName());
							}
						}
						jlFr.setInfoJg(row.getCell(3).getStringCellValue());   //3籍贯
						jlFr.setInfoCsrq(row.getCell(4).getStringCellValue()); //4出生日期
						jlFr.setInfoHome(row.getCell(5).getStringCellValue()); //5家庭住址
						jlFr.setInfoZm(row.getCell(6).getStringCellValue());   //6罪名
						jlFr.setInfoXq(row.getCell(7).getStringCellValue());   // 7刑期
						jlFr.setInfoRjsj(row.getCell(8).getStringCellValue()); //8入监日期
						String jb = row.getCell(9).getStringCellValue();
						jlFr.setJbNo("-1");
						jlFr.setJbName("未定义");
						for(JlJbVO t : jlJbList){
							if(StringUtils.isNotBlank(jb) && jb.equals(t.getJbName())){
								jlFr.setJbNo(t.getJbNo());
								jlFr.setJbName(t.getJbName());
							}
						}
						jlFrSQL.add(jlFr);
					}
				}else{
					break;
				}
				index++;
			}
		}
		
		//最后删除文件
		File file = new File(path);
		if(file.exists() && file.isFile())file.delete();
	}

}
