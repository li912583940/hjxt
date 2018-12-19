package com.sl.ue.util.business;

public class QsThread implements Runnable {
	
	private Thread thread;
	private String path;

	public QsThread(String path) {
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
		// TODO Auto-generated method stub
		
	}

}
