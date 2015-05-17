package com.example.androidmultherddownloadtwo;

import java.io.File;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
/**
 * android 多线程断点续传下载
 * @author miaowei
 *
 */
public class MainActivity extends Activity {
	
	private EditText path;
	private TextView progress;
	private ProgressBar progressBar;
	
	private Handler handler = new  UIHandler();
	private DownloadService service;
	private Button downButton;
	private Button pauseButton;
	private String downloadFile1 = "http://gongxue.cn/yingyinkuaiche/UploadFiles_9323/201008/2010082909434077.mp3";
	
	private final class UIHandler extends Handler{
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				 int downloaded_size = msg.getData().getInt("size");  
                 progressBar.setProgress(downloaded_size);  
                 int result = (int) ((float) downloaded_size / progressBar.getMax() * 100);  
                 progress.setText(result + "%");  
                 if (progressBar.getMax() == progressBar.getProgress()) {  
                     Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();  
                 } 
				break;
			default:
				break;
			}
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(getApplicationContext(), R.layout.activity_main, null);
		setContentView(view);
		//
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		path = (EditText)view.findViewById(R.id.editText);
		
		progress = (TextView)view.findViewById(R.id.textView);
		progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
		
		downButton = (Button)view.findViewById(R.id.downButton);
		pauseButton = (Button)view.findViewById(R.id.pauseButton);
		
		downButton.setOnClickListener(new DownloadButton());
		pauseButton.setOnClickListener(new PauseButton());
	}
	
	private final class DownloadButton implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			
			DownloadTask task;
			try {
				
				task = new DownloadTask(downloadFile1);
				service.isPause = false;
				downButton.setEnabled(false);
				pauseButton.setEnabled(true);
				new Thread(task).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private final class PauseButton implements View.OnClickListener{

		@Override
		public void onClick(View v) {
		
			service.isPause = true;
			v.setEnabled(false);
			downButton.setEnabled(true);
		}
		
	}
	
	public void pause(View v) { 
		
    } 
	
	private final class DownloadTask implements Runnable{

		
		public DownloadTask(String target){
			try {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

					File destination = Environment.getExternalStorageDirectory();
					service = new DownloadService(target, destination, 3,
							getApplicationContext());
					progressBar.setMax(service.fileSize);
				} else {

					Toast.makeText(getApplicationContext(), "SD卡不在在或写保护",Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
	
		}
		
		@Override
		public void run() {
			try {
				service.download(new DownloadListener() {  
					  
	                @Override  
	                public void onDownload(int downloaded_size) {  
	                    Message message = new Message();  
	                    message.what = 1;  
	                    message.getData().putInt("size", downloaded_size);  
	                    handler.sendMessage(message);  
	                }  

	            }); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
}
