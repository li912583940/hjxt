package com.sl.ue.util;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {

	public static String getCellValue(HSSFCell cell){
		if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
		String value="";
		switch (cell.getCellType()) {
		case STRING: value =  cell.getStringCellValue().trim();break;
		case NUMERIC: 
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				value = DateUtil.getDefault(cell.getDateCellValue()).trim();
			}else{
				value =  new DecimalFormat("#").format(cell.getNumericCellValue()).trim();
			}
			break;
		case BOOLEAN: value = cell.getBooleanCellValue()==true?"是":"否"; break;
		default:
			value = cell.toString().trim();
			break;
		}
		return value;
	}
	
	public static String getCellValue(XSSFCell cell){
		if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
		String value="";
		switch (cell.getCellType()) {
		case STRING: value =  cell.getStringCellValue().trim();break;
		case NUMERIC: 
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				value = DateUtil.getDefault(cell.getDateCellValue()).trim();
			}else{
				value =  new DecimalFormat("#").format(cell.getNumericCellValue()).trim();
			}
			break;
		case BOOLEAN: value = cell.getBooleanCellValue()==true?"是":"否"; break;
		default:
			value = cell.toString().trim();
			break;
		}
		return value;
	}
}
