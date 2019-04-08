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

import com.sl.ue.entity.jl.vo.JlQsVO;
import com.sl.ue.service.jl.JlQsService;
import com.sl.ue.util.ExcelUtil;
import com.sl.ue.util.component.SpringTool;

public class QsThreadXLSX implements Runnable {
	
	private Thread thread;
	private String path;

	public QsThreadXLSX(String path) {
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
		JlQsService jlQsSQL = (JlQsService) SpringTool.getBean("jlQsSQL");
		
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
				// 0罪犯编号，1罪犯姓名，2证件类型，3证件号码，4关系人姓名，5性别，6称谓，7电话，8家庭住址
				JlQsVO jlQs = new JlQsVO();
				
				String frNo = ExcelUtil.getCellValue(row.getCell(0));   //0罪犯编号
				String qsSfz = ExcelUtil.getCellValue(row.getCell(3));  //3证件号码
				String qsName = ExcelUtil.getCellValue(row.getCell(4)); //4关系人姓名
				if(StringUtils.isBlank(frNo) || StringUtils.isBlank(qsName)){
					
				}else{
					jlQs.setFrNo(frNo);
					if(StringUtils.isNotBlank(qsSfz)){
						jlQs.setQsSfz(qsSfz);
					}
					jlQs.setQsName(qsName);
					List<JlQsVO> list = jlQsSQL.findList(jlQs);
					if(list.size()>0){
					}else{
						String qsZjlb = ExcelUtil.getCellValue(row.getCell(2)); //2证件类型
						if(StringUtils.isNotBlank(qsZjlb)){
							if(qsZjlb.equals("身份证")){
								jlQs.setQsZjlb(1);
							}else if(qsZjlb.equals("警官证")){
								jlQs.setQsZjlb(2);
							}else if(qsZjlb.equals("工作证")){
								jlQs.setQsZjlb(3);
							}else if(qsZjlb.equals("港澳通行证")){
								jlQs.setQsZjlb(4);
							}else if(qsZjlb.equals("台湾通行证")){
								jlQs.setQsZjlb(5);
							}else if(qsZjlb.equals("其他")){
								jlQs.setQsZjlb(9);
							}
						}
						jlQs.setXb(ExcelUtil.getCellValue(row.getCell(5))); 	//5性别
						jlQs.setGx(ExcelUtil.getCellValue(row.getCell(6))); 	//6称谓
						jlQs.setTele(ExcelUtil.getCellValue(row.getCell(7))); 	//7电话
						jlQs.setDz(ExcelUtil.getCellValue(row.getCell(8)));	//8家庭住址
						jlQsSQL.add(jlQs);
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
