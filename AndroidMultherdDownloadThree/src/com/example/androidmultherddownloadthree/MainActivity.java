package com.example.androidmultherddownloadthree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidmultherddownloadthree.adapter.DownLoadAdapter;
import com.example.androidmultherddownloadthree.core.Downloader;
import com.example.androidmultherddownloadthree.entity.LoadInfo;
/**
 * 多线程断点续传示例 
 * 注意子线程不要影响主UI线程,灵活运用task和handler,
 * 各取所长,保证用户体验,handler通常在主线程中有利于专门负责处理UI的一些工作
 * @author miaowei
 *
 */
public class MainActivity extends Activity {

	/**
	 *  固定下载的资源路径，这里可以设置网络上的地址  
	 */
    private static final String URL = "http://download.haozip.com/";  
    //private static final String URL = "http://gongxue.cn/yingyinkuaiche/UploadFiles_9323/201008/";
    /**
     *  固定存放下载的音乐的路径：SD卡目录下  
     */
    private static final String SD_PATH = "/mnt/sdcard/";  
    /**
     *  存放各个下载核心类容器  
     *  每个url对应一个下载核心类
     */
    private Map<String, Downloader> downloaders = new HashMap<String, Downloader>();  
    /**
     *  存放与下载器对应的进度条  
     *  每个url对应一个ProgressBar
     */
    private Map<String, ProgressBar> ProgressBars = new HashMap<String, ProgressBar>();
	
    
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    
    private ListView listView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(this, R.layout.activity_main, null);
		setContentView(view);
		listView = (ListView)view.findViewById(R.id.listview);
		
		showListView();
	}

	/**
	 * 显示listview
	 */
	private void showListView(){
		Map<String, String> map = new HashMap<String, String>();  
        map.put("name", "haozip_v3.1.exe"); 
		//map.put("name", "2010082909434077.mp3"); 
        data.add(map);  
        map = new HashMap<String, String>();  
        map.put("name", "haozip_v3.1_hj.exe");
        //map.put("name", "2010082909434077.mp3");
        data.add(map);  
        map = new HashMap<String, String>();  
        map.put("name", "haozip_v2.8_x64_tiny.exe");
        //map.put("name", "2010082909434077.mp3");
        data.add(map);  
        map = new HashMap<String, String>();  
        map.put("name", "haozip_v2.8_tiny.exe");
        //map.put("name", "2010082909434077.mp3");
        data.add(map);  
        DownLoadAdapter adapter=new DownLoadAdapter(this,data);    
        //setListAdapter(adapter);
        listView.setAdapter(adapter);
	}
	
	/**
	 * 响应开始下载按钮的点击事件
	 * 对应XML中的属性方法 android:onClick="startDownload"
	 * @param v 当前View对象
	 */
	public void startDownload(View v){
		 // 得到textView的内容   
        LinearLayout layout = (LinearLayout) v.getParent();  
        String resouceName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();  
        String urlstr = URL + resouceName;  
        String localfile = SD_PATH + resouceName;  
        //设置下载线程数为4，这里是我为了方便随便固定的  
        String threadcount = "4";  
        DownloadTask downloadTask=new DownloadTask(v);  
        downloadTask.execute(urlstr,localfile,threadcount);  
	}
	
	/**
	 * 执行异步下载类
	 * @author miaowei
	 *
	 */
	private class DownloadTask extends AsyncTask<String, Integer, LoadInfo>{

		/**
		 * 当前View对象
		 */
		View view = null;
		/**
		 * 下载类
		 */
		Downloader downloader=null;
		/**
		 * url地址
		 */
		String urlString = null;
		/**
		 * 
		 * @param v 当前View对象
		 */
		public DownloadTask(final View v) {
			this.view =  v;
		}
		/**
		 * 执行前操作
		 */
		@Override
		protected void onPreExecute() {
			Button btn_start=(Button)((View)view.getParent()).findViewById(R.id.btn_start);  
            Button btn_pause=(Button)((View)view.getParent()).findViewById(R.id.btn_pause);  
            btn_start.setVisibility(View.GONE);  
            btn_pause.setVisibility(View.VISIBLE);
		}
		/**
		 * 执行中操作
		 */
		@Override
		protected LoadInfo doInBackground(String... params) {
			
			urlString=params[0];  
            String localfile=params[1];  
            int threadcount=Integer.parseInt(params[2]);  
             // 初始化一个downloader下载器  
             downloader = downloaders.get(urlString);  
             if (downloader == null) {  
                 downloader = new Downloader(urlString, localfile, threadcount, MainActivity.this, mHandler);  
                 //
                 downloaders.put(urlString, downloader);  
             }  
             if (downloader.isdownloading()){
            	 
            	 return null; 
             }  
             // 得到下载信息类的个数组成集合  
             return downloader.getDownloaderInfors(); 
		}
		/**
		 * 执行后操作
		 */
		@Override
		protected void onPostExecute(LoadInfo loadInfo) {
			if(loadInfo!=null){  
                // 显示进度条  
                showProgress(loadInfo, urlString, view);  
                // 调用方法开始下载  
                downloader.download();  
           } 
		}
	}
	

	/**
	 * 显示进度条  
	 * @param loadInfo 下载器详细信息的类
	 * @param url 地址
	 * @param v 当前View对象
	 */
     private void showProgress(LoadInfo loadInfo, String url, View v) {  
         ProgressBar bar = ProgressBars.get(url);  
         if (bar == null) {  
             bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);  
             bar.setMax(loadInfo.getFileSize());  
             bar.setProgress(loadInfo.getComplete()); 
             //每个url对应一个ProgressBar
             ProgressBars.put(url, bar);  
             LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, 5);  
             ((LinearLayout) ((LinearLayout) v.getParent()).getParent()).addView(bar, params);  
         }  
     }
     
     /**
      * 响应暂停下载按钮的点击事件 
      * @param v 当前View对象
      */
     public void pauseDownload(View v) {  
         LinearLayout layout = (LinearLayout) v.getParent();  
         String resouceName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();  
         String urlstr = URL + resouceName;  
         downloaders.get(urlstr).pause();  
         Button btn_start=(Button)((View)v.getParent()).findViewById(R.id.btn_start);  
         Button btn_pause=(Button)((View)v.getParent()).findViewById(R.id.btn_pause);  
         btn_pause.setVisibility(View.GONE);  
         btn_start.setVisibility(View.VISIBLE);  
     } 

    /**
     *  利用消息处理机制适时更新进度条 
     */
     private Handler mHandler = new Handler(){
    	 
    	 public void handleMessage(android.os.Message msg) {
    		 
    		 switch (msg.what) {
			case 1:
				String urlString = (String)msg.obj;
				int lenght = msg.arg1;
				ProgressBar bar = ProgressBars.get(urlString);
				if (bar != null) {
					//设置进度条按读取的length长度更新
					bar.incrementProgressBy(lenght);
					//如果进度条的当前进度已经到最大
					if (bar.getProgress() == bar.getMax()) {
						
						LinearLayout layout = (LinearLayout)bar.getParent();
						TextView resouceName = (TextView)layout.findViewById(R.id.tv_resouce_name);
						Toast.makeText(MainActivity.this, "["+resouceName.getText()+"]下载完成！", Toast.LENGTH_SHORT).show();
						//下载完成后清除进度条并将map中的数据清空
						layout.removeView(bar);
						
						ProgressBars.remove(urlString);
						downloaders.get(urlString).delete(urlString);
						downloaders.get(urlString).reset();
						downloaders.remove(urlString);
						
						Button btn_start = (Button)layout.findViewById(R.id.btn_start);
						Button btn_pButton = (Button)layout.findViewById(R.id.btn_pause);
					
						btn_start.setVisibility(View.GONE);
						btn_pButton.setVisibility(View.GONE);
					
					}
				}
				break;

			default:
				break;
			}
    	 };
    	 
     };
}
