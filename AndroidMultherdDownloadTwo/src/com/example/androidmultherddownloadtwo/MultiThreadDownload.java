package com.example.androidmultherddownloadtwo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
/**
 * 
 * @author miaowei
 *
 */
public class MultiThreadDownload  implements Runnable{

	/**
	 * 
	 */
	public int id;
	/**
	 * 支持对随机访问文件的读取和写入
	 */
	private RandomAccessFile savedFile;
	private String path;
	/**
	 * 当前已下载量
	 */
	public int currentDownloadSize = 0;
	/**
	 * 下载状态
	 */
	public boolean finished;
	/**
	 * 用于监视下载状态
	 */
	private final DownloadService downloadService;
	/**
	 * 线程下载任务的起始点
	 */
	public int start;
	/**
	 * 线程下载任务的结束点
	 */
	private int end;
	
	/**
	 * 用于第二次下载续传
	 * @param id
	 * @param savedFile
	 * @param block
	 * @param path
	 * @param downlength
	 * @param downloadService
	 * @throws Exception
	 */
	public MultiThreadDownload(int id,File savedFile,int block,String path,Integer downlength,DownloadService downloadService) throws Exception {
		
		this.id = id;
		this.path = path;
		if (downlength != null) {
		
			this.currentDownloadSize = downlength;
		}
		
		this.savedFile = new RandomAccessFile(savedFile, "rwd");
		this.downloadService = downloadService;
		
		start = id * block + currentDownloadSize;
		
		end = (id + 1) * block;
	}
	
	@Override
	public void run() {
		try {  
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("GET");  
            //指定下载范围从某个字节开始(文件总大小-客户端请求的下载文件块的开始字节)
            conn.setRequestProperty("Range", "bytes=" + start + "-" + end); // 设置获取数据的范围  
  
            InputStream in = conn.getInputStream();  
            byte[] buffer = new byte[1024];  
            int len = 0;  
            //定位文件指针到start位置
            savedFile.seek(start);  
            while (!downloadService.isPause && (len = in.read(buffer)) != -1) {  
                savedFile.write(buffer, 0, len);  
                currentDownloadSize += len;  
            }  
            savedFile.close();  
            in.close();  
            conn.disconnect();  
            if (!downloadService.isPause)
            	Log.i(DownloadService.TAG, "Thread " + (this.id + 1) + "finished");  
              finished = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException("File downloading error!");  
        }  
		
	}

}
