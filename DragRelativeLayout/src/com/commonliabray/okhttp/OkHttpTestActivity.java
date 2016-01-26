package com.commonliabray.okhttp;

import java.io.File;

import com.commonliabray.asynchttp.UrlConstants;
import com.commonliabray.model.User;
import com.example.dragrelativelayout.R;
import com.okhttp.CommonOkHttpClient;
import com.okhttp.listener.DisposeDataHandle;
import com.okhttp.listener.DisposeDataListener;
import com.okhttp.request.CommonRequest;
import com.okhttp.request.RequestParams;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author vision
 * @function OkHttpClient网络请求测试页面
 */
public class OkHttpTestActivity extends Activity implements DisposeDataListener, OnClickListener {
	private ImageView mImageView;
	private Button mLoginView;
	private Button mCookieView;
	private Button mFileDownloadView;
	private TextView mCookieTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_okhttp_test);
		initView();
	}

	private void initView() {
		mImageView = (ImageView) findViewById(R.id.four_view);
		mLoginView = (Button) findViewById(R.id.login_view);
		mCookieView = (Button) findViewById(R.id.get_cookie_view);
		mFileDownloadView = (Button) findViewById(R.id.down_load_file);
		mCookieTextView = (TextView) findViewById(R.id.cookie_show_view);
		mLoginView.setOnClickListener(this);
		mCookieView.setOnClickListener(this);
		mFileDownloadView.setOnClickListener(this);
	}

	private void getRequest() {
		RequestParams params = new RequestParams();
		params.put("type", "1");
		params.put("name", "renzhiqaing");

		CommonOkHttpClient.get(CommonRequest.createGetRequest("https://publicobject.com/helloworld.txt", params),
				new DisposeDataHandle(new DisposeDataListener() {
					@Override
					public void onSuccess(Object responseObj) {
					}

					@Override
					public void onFailure(Object reasonObj) {
					}
				}));
	}

	/**
	 * 发送post请求,经过封装后使用方式式与AsyncHttpClient完全一样
	 */
	private void postRequest() {
		/**
		 * 这里在实际工程中还要再封装一层才好
		 */
		RequestParams params = new RequestParams();
		params.put("mb", "18911230100");
		params.put("pwd", "999999q");
		CommonOkHttpClient.post(CommonRequest.createPostRequest(UrlConstants.USER_LOGIN, params),
				new DisposeDataHandle(this, User.class));
	}

	/**
	 * 此处以下载图片做为文件下载的demo
	 */
	private void downloadFile() {
		CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest("http://images.csdn.net/20150817/1.jpg", null),
				new DisposeDataHandle(new DisposeDataListener() {
					@Override
					public void onSuccess(Object responseObj) {
						mImageView.setImageBitmap(BitmapFactory.decodeFile(((File) responseObj).getAbsolutePath()));
					}

					@Override
					public void onFailure(Object reasonObj) {
					}
				}, Environment.getExternalStorageDirectory().getAbsolutePath() + "/test2.jpg"));
	}

	/**
	 * 服务器返回数据
	 * 
	 * @param responseObj
	 */
	@Override
	public void onSuccess(Object responseObj) {
		mCookieTextView.setText(((User) responseObj).data.nick);

		/**
		 * 这是一个需要Cookie的请求，说明Okhttp帮我们存储了Cookie
		 */
		CommonOkHttpClient.post(CommonRequest.createPostRequest(UrlConstants.PUSH_LIST, null),
				new DisposeDataHandle(new DisposeDataListener() {
					@Override
					public void onSuccess(Object responseObj) {
						mCookieTextView.setText(responseObj.toString());
					}

					@Override
					public void onFailure(Object reasonObj) {
					}
				}));
	}

	@Override
	public void onFailure(Object reasonObj) {
		Log.e("----->error", reasonObj.toString());
	}

	private void getCookie() {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.login_view:
			postRequest();
			break;
		case R.id.get_cookie_view:
			getCookie();
			break;
		case R.id.down_load_file:
			downloadFile();
			break;
		}
	}
}