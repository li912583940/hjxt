package com.sl.ue.util.business;

import java.io.File;
import java.util.List;

public class RecordFileThread implements Runnable{

	private Thread thread;
	private List<File> list;
	
	public RecordFileThread(List<File> list){
		this.list=list;
	}
	public void start() {
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		for(File file : list){
			if(file.exists()){
				file.delete();
			}
		}
		
	}

}
