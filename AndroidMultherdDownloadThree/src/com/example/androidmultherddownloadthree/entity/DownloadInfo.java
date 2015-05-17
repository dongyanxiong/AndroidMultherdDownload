package com.example.androidmultherddownloadthree.entity;
/**
 * 创建一个下载信息的实体类
 * @author miaowei
 *
 */
public class DownloadInfo {

	/**
	 * 下载器id 
	 */
	private int threadId;//下载器id 
	/**
	 * 开始点 
	 */
    private int startPos;//开始点
    /**
     * 结束点
     */
    private int endPos;//结束点 
    /**
     * 完成度 
     */
    private int compeleteSize;//完成度
    /**
     * 下载器网络标识地址
     */
    private String url;//下载器网络标识
    /**
     * 下载信息的实体类
     * @param threadId 下载器id
     * @param startPos 开始点
     * @param endPos 结束点
     * @param compeleteSize 完成度
     * @param url 下载器网络标识地址
     */
    public DownloadInfo(int threadId, int startPos, int endPos,  
            int compeleteSize,String url) {  
        this.threadId = threadId;  
        this.startPos = startPos;  
        this.endPos = endPos;  
        this.compeleteSize = compeleteSize;  
        this.url=url;  
    }
    @Override
    public String toString() {
    	return "DownloadInfo [下载器idthreadId=" + threadId  
                + ", 开始点startPos=" + startPos + ", 结束点endPos=" + endPos  
                + ", 完成度compeleteSize=" + compeleteSize +"]"; 
    }
    
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
	public int getCompeleteSize() {
		return compeleteSize;
	}
	public void setCompeleteSize(int compeleteSize) {
		this.compeleteSize = compeleteSize;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
    
}
