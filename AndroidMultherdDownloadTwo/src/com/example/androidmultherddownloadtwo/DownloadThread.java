package com.example.androidmultherddownloadtwo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下载线程
 * @author miaowei
 *
 */
public class DownloadThread extends Thread {

	private String url;
	private String targetPath = "";
	
	private int hasDownload = 0;
	
	private int len = -1;
	private byte buffer[] = new byte[4 * 1024];
	/**
	 * 文件总大小
	 */
	private int size = 0;
	/**
	 * 百分比
	 */
	private int rate = 0;
	
	
	private MyHandler mHandler = null;
	private Message msg = null;
	private ProgressBar pbBar = null;
	private TextView tvTextView = null;
	
	public DownloadThread(String url,String targetPath,ProgressBar pBar,TextView tv) {
		
		this.url = url;
		this.targetPath = targetPath;
		
		mHandler = new MyHandler(pBar, tv);
		this.pbBar = pBar;
		this.tvTextView = tv;
	}
	
	/**
	 * 自定义一个Handler类，处理线程消息
	 * @author miaowei
	 *
	 */
	private class MyHandler extends Handler{
		private ProgressBar pbBar = null;
		private TextView tvTextView = null;
		
		public MyHandler(ProgressBar pbBar,TextView tvTextView) {
			
			this.pbBar = pbBar;
			this.tvTextView = tvTextView;
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			this.pbBar.setProgress(msg.arg1);
			this.tvTextView.setText(msg.arg1 + "%");
		}
		
	}
	
	@Override
	public void run() {
		super.run();
		String targetFileName = this.targetPath
				+ this.url.substring(this.url.lastIndexOf("/") + 1,this.url.length());
		
		File downloadFile = new File(targetFileName);
		
		if (!downloadFile.exists()) {
			
			try {
				downloadFile.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		try {
			URL fileUrl = new URL(this.url);
			HttpURLConnection connection = (HttpURLConnection)fileUrl.openConnection();
			
			//获取文件大小
			size = connection.getContentLength();
			//获取输入流(往程序里写的)
			InputStream isInputStream = connection.getInputStream();
			//创建输出流(程序往外写)
			OutputStream osOutputStream = new FileOutputStream(targetFileName);
			//当文件没有读到结尾时
			while ( (len = isInputStream.read(buffer)) != -1) {
				
				osOutputStream.write(buffer);
				
				hasDownload += len;
				//转换为百分比
				rate = (hasDownload * 100 /size);
				
				msg = mHandler.obtainMessage();
				msg.arg1 = rate;
				mHandler.sendMessage(msg);
				System.out.println(rate+"%");
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}
