package com.sl.ue.hj.service;

import java.io.File;

import com.sl.ue.hj.service.ServiceBean;

/**
 * 说明 [读取实体类 并写入service]
 * @author lxt
 */
public class EntityBean {

	private String path ="G:\\声联项目\\hjxt\\HjSystem\\src\\main\\java\\com\\sl\\ue\\entity";
	private String hj = "\\hj";
	private String jl = "\\jl";
	private String sys = "\\sys";
	
	
	public void execute(){
		hj();
		jl();
		sys();
	}
	
	private void hj(){
		System.out.println("读取文件夹： "+path+hj);
		File file = new File(path+hj);
		if(file.exists()){
			String[] strs = file.list();
			for(String str : strs){
				System.out.println(str);
				ServiceBean serviceBean = new ServiceBean();
				String[] s = str.split("\\.");
				serviceBean.execute(s[0], "hj");
			}
		}
	}
	
	private void jl(){
		System.out.println("读取文件夹： "+path+jl);
		File file = new File(path+jl);
		if(file.exists()){
			String[] strs = file.list();
			for(String str : strs){
				System.out.println(str);
				ServiceBean serviceBean = new ServiceBean();
				String[] s = str.split("\\.");
				serviceBean.execute(s[0], "jl");
			}
		}
	}
	
	private void sys(){
		System.out.println("读取文件夹： "+path+sys);
		File file = new File(path+sys);
		if(file.exists()){
			String[] strs = file.list();
			for(String str : strs){
				System.out.println(str);
				ServiceBean serviceBean = new ServiceBean();
				String[] s = str.split("\\.");
				serviceBean.execute(s[0], "sys");
			}
		}
	}
}
