package com.sl.ue.hj.entity;

import java.io.File;

import com.sl.ue.hj.service.ServiceBean;

public class Entity2 {

	private String path ="D:\\声联项目\\hjxt\\HjSystem\\src\\main\\java\\com\\sl\\ue\\entity";
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
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isFile()){
					String filename = f.getName();
					System.out.println(filename);
					Entity3 entity3 = new Entity3();
					String[] s = filename.split("\\.");
					entity3.executeVO(s[0], "hj");
				}
			}
		}
	}
	
	private void jl(){
		System.out.println("读取文件夹： "+path+jl);
		File file = new File(path+jl);
		if(file.exists()){
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isFile()){
					String filename = f.getName();
					System.out.println(filename);
					Entity3 entity3 = new Entity3();
					String[] s = filename.split("\\.");
					entity3.executeVO(s[0], "jl");
				}
			}
		}
	}
	
	private void sys(){
		System.out.println("读取文件夹： "+path+sys);
		File file = new File(path+sys);
		if(file.exists()){
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isFile()){
					String filename = f.getName();
					System.out.println(filename);
					Entity3 entity3 = new Entity3();
					String[] s = filename.split("\\.");
					entity3.executeVO(s[0], "sys");
				}
			}
		}
	}
}
