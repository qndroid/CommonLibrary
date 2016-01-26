package com.commonliabray.asynchttp;

import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.commonhttp.CommonClient;
import com.sharesdk.ShareManager;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

/**********************************************************
 * @文件名称：CommonApplication.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月19日 下午10:38:25
 * @文件描述：App容器
 * @修改历史：2015年11月19日创建初始版本
 **********************************************************/
public class CommonApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		initCookieStore();
		initShareSdk();
		initJPush();

	}

	/**
	 * 初始化全局CookieStore
	 */
	private void initCookieStore() {
		CommonClient.setCookieStore(new PersistentCookieStore(this));
	}

	/**
	 * 初始化社会化分享组件
	 */
	private void initShareSdk() {
		ShareManager.initSDK(this);
	}

	private void initJPush() {

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}
}