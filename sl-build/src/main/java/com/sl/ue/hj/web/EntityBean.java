package com.sl.ue.hj.web;

import java.io.File;

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
				WebBean webBean = new WebBean();
				webBean.hj(str);
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
				WebBean webBean = new WebBean();
				webBean.jl(str);
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
				WebBean webBean = new WebBean();
				webBean.sys(str);
			}
		}
	}
}
