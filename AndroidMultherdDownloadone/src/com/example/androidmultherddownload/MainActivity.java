package com.example.androidmultherddownload;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * android 两个任务同时下载的小例子
 * @author miaowei
 *
 */
public class MainActivity extends Activity {
	
	private ProgressBar pb1 = null;
	private TextView tv1 = null;
	private ProgressBar pb2 = null;
	private TextView tv2 = null;
	
	private Button btnDown;
	
	/**
	 * 声明已经读过的长度变量
	 */
	private int hasRead = 0;

	/**
	 * 存储路径
	 */
	private String root = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	/**
	 * 下载文件地址
	 */
	private String downloadFile0= "http://gongxue.cn/yingyinkuaiche/UploadFiles_9323/201008/2010082909434077.mp3";
	private String downloadFile1 = "http://gongxue.cn/yingyinkuaiche/UploadFiles_9323/201008/2010082909434077.mp3";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(getApplicationContext(), R.layout.activity_main, null);
		setContentView(view);
		
		pb1 = (ProgressBar)view.findViewById(R.id.progressBar1);
		pb2 = (ProgressBar)view.findViewById(R.id.progressBar2);
		
		tv1 = (TextView)view.findViewById(R.id.textView1);
		tv2 = (TextView)view.findViewById(R.id.textView2);
		
		btnDown = (Button)view.findViewById(R.id.btnDown);
		
		btnDown.setOnClickListener(onClickListener);
		
		/*download(downloadFile0, root, pb1, tv1);
		download(downloadFile1, root, pb2, tv2);*/
	}
	
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnDown:
				download(downloadFile0, root, pb1, tv1);
				download(downloadFile1, root, pb2, tv2);
				break;

			default:
				break;
			}
			
		}
	};
	
	/**
	 * 
	 * @param url 下载地址
	 * @param targetPath 目标存储路径
	 * @param pb 控件
	 * @param tv 控件
	 */
	private void download(String url,String targetPath,ProgressBar pb,TextView tv){
		
		DownloadThread aThread = new DownloadThread(url, targetPath, pb, tv);
		aThread.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
