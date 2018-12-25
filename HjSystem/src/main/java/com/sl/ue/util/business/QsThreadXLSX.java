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
				// 0罪犯编号，1证件类型，2证件号码，3关系人姓名，4性别，5称谓，6电话，7家庭住址
				JlQsVO jlQs = new JlQsVO();
				String frNo = row.getCell(0).getStringCellValue();   //0罪犯编号
				String qsSfz = row.getCell(2).getStringCellValue();  //2证件号码
				String qsName = row.getCell(3).getStringCellValue(); //3关系人姓名
				jlQs.setFrNo(frNo!=null?frNo.trim():null);
				if(StringUtils.isNotBlank(qsSfz)){
					jlQs.setQsSfz(qsSfz.trim());
				}
				jlQs.setQsName(qsName!=null?qsName.trim():null);
				if(StringUtils.isNotBlank(frNo)){
					List<JlQsVO> list = jlQsSQL.findList(jlQs);
					if(list.size()>0){
					}else{
						Integer qsZjlb = 1;
						if(StringUtils.isNotBlank(row.getCell(1).getStringCellValue())){ //1证件类型
							qsZjlb = Integer.parseInt(row.getCell(1).getStringCellValue());
						}
						jlQs.setQsZjlb(qsZjlb);
						jlQs.setXb(row.getCell(4).getStringCellValue()); 	//4性别
						jlQs.setGx(row.getCell(5).getStringCellValue()); 	//5称谓
						jlQs.setTele(row.getCell(6).getStringCellValue()); 	//6电话
						jlQs.setDz(row.getCell(7).getStringCellValue());	//7家庭住址
						jlQsSQL.add(jlQs);
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
