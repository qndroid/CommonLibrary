package com.renzhiqiang.update;

/**********************************************************
 * @文件名称：UpdateDownloadListener.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月26日 下午10:59:17
 * @文件描述：下载不同状态接口回调
 * @修改历史：2015年8月26日创建初始版本
 **********************************************************/
public interface UpdateDownloadListener {
	/**
	 * 下载请求开始回调
	 */
	public void onStarted();

	/**
	 * 请求成功，下载前的准备回调
	 * 
	 * @param contentLength
	 *            文件长度
	 * @param downloadUrl
	 *            下载地址
	 */
	public void onPrepared(long contentLength, String downloadUrl);

	/**
	 * 进度更新回调
	 * 
	 * @param progress
	 * @param downloadUrl
	 */
	public void onProgressChanged(int progress, String downloadUrl);

	/**
	 * 下载过程中暂停的回调
	 * 
	 * @param completeSize
	 * @param downloadUrl
	 */
	public void onPaused(int progress, int completeSize, String downloadUrl);

	/**
	 * 下载完成回调
	 * 
	 * @param completeSize
	 * @param downloadUrl
	 */
	public void onFinished(int completeSize, String downloadUrl);

	/**
	 * 下载失败回调
	 */
	public void onFailure();
}
