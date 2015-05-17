package com.example.androidmultherddownloadthree.entity;
/**
 * 自定义的一个记载下载器详细信息的类
 * @author miaowei
 *
 */
public class LoadInfo {

	/**
	 * 文件大小 
	 */
	public int fileSize;//文件大小
	/**
	 * 完成度  
	 */
    private int complete;//完成度  
    /**
     * 下载器标识地址
     */
    private String urlstring;//下载器标识
    
    /**
     * 下载器详细信息
     * @param fileSize 文件大小
     * @param complete 完成度 
     * @param urlstring 下载器标识地址
     */
    public LoadInfo(int fileSize, int complete, String urlstring) {  
        this.fileSize = fileSize;  
        this.complete = complete;  
        this.urlstring = urlstring;  
    }
    
    @Override
    public String toString() {
    	 return "LoadInfo [文件大小fileSize=" + fileSize + ", 完成度complete=" + complete  
                 + ", 下载器标识urlstring=" + urlstring + "]";
   
}


	public int getFileSize() {
		return fileSize;
	}


	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


	public int getComplete() {
		return complete;
	}


	public void setComplete(int complete) {
		this.complete = complete;
	}


	public String getUrlstring() {
		return urlstring;
	}


	public void setUrlstring(String urlstring) {
		this.urlstring = urlstring;
	}
}
