package com.loopj.android.http.commonhttp;

/**********************************************************
 * @文件名称：DisposeDataHandle.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月19日 上午10:41:46
 * @文件描述：请求功能接口适配类
 * @修改历史：2015年8月19日创建初始版本
 **********************************************************/
public class DisposeDataHandle implements DisposeDataListener
{
	@Override
	public void onStart()
	{
	}

	@Override
	public void onSuccess(Object responseObj)
	{
	}

	@Override
	public void onFailure(Object reasonObj)
	{
	}

	@Override
	public void onRetry(int retryNo)
	{
	}

	@Override
	public void onProgress(long bytesWritten, long totalSize)
	{
	}

	@Override
	public void onFinish()
	{
	}

	@Override
	public void onCancel()
	{
	}
}