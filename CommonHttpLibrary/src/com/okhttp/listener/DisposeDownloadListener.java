package com.okhttp.listener;

public interface DisposeDownloadListener extends DisposeDataListener
{
	public void onProgress(int progrss);
}
