package com.commonliabray.asynchttp.activity;

import org.apache.http.cookie.Cookie;

import com.example.dragrelativelayout.R;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**********************************************************
 * @文件名称：WebActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月21日 下午12:16:05
 * @文件描述：显示需要Cookie的WebView页面
 * @修改历史：2015年11月21日创建初始版本
 **********************************************************/
public class WebActivity extends Activity {
	private static final String url = "http://i.qianjing.com/account/ac/coupon.php";

	/**
	 * UI
	 */
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_webview);
		initView();
	}

	private void initView() {
		mWebView = (WebView) findViewById(R.id.web_view);
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				setTitle(title);
			}
		});
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		mWebView.getSettings().setJavaScriptEnabled(true);

		loadCookieUrl(url);
		mWebView.loadUrl(url);
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		} else {
			finish();
		}
	}

	private void loadCookieUrl(String url) {
		/**
		 * 注意CookieSyncManager在API19已经过期，API19以上可以完全使用CookieManager
		 * 但还要适配API19以下还是需要CookieSyncManager的
		 */
		CookieManager cookieManager = CookieManager.getInstance();
		PersistentCookieStore cookieStore = new PersistentCookieStore(this);
		for (int i = 0; i < cookieStore.getCookies().size(); i++) {
			Cookie cookie = cookieStore.getCookies().get(i);
			/**
			 * 为此Url添加所有的可用Cookie,如果知道仅需要的cookie，也可以只添加需要的cookie即可。
			 */
			cookieManager.setCookie(url, cookie2String(cookie));
		}
		CookieManager.getInstance().flush();
	}

	/**
	 * 将Cookie对象拼接为String
	 */
	private String cookie2String(Cookie cookie) {
		StringBuilder cookieBuild = new StringBuilder();
		cookieBuild.append(cookie.getName()).append("=").append(cookie.getValue()).append(";").append(" path=")
				.append(cookie.getPath()).append(";").append(" domain=").append(cookie.getDomain());
		return cookieBuild.toString();
	}
}