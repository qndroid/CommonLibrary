package com.commonliabray.okhttp;

import java.io.File;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.commonliabray.asynchttp.UrlConstants;
import com.commonliabray.model.User;
import com.example.dragrelativelayout.R;
import com.okhttp.CommonOkHttpClient;
import com.okhttp.listener.DisposeDataHandle;
import com.okhttp.listener.DisposeDataListener;
import com.okhttp.request.CommonRequest;
import com.okhttp.request.RequestParams;

/**
 * @author vision
 * @function OkHttpClient网络请求测试页面
 */
public class OkHttpTestActivity extends Activity implements DisposeDataListener
{
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_okhttp_test);
		mImageView = (ImageView) findViewById(R.id.four_view);
		// getRequest();

		// postRequest();

		downloadFile();
	}

	private void getRequest()
	{
		RequestParams params = new RequestParams();
		params.put("type", "1");
		params.put("name", "renzhiqaing");

		CommonOkHttpClient.getInstance().get(
				CommonRequest.createGetRequest("https://publicobject.com/helloworld.txt", params),
				new DisposeDataHandle(new DisposeDataListener()
				{
					@Override
					public void onSuccess(Object responseObj)
					{
					}

					@Override
					public void onFailure(Object reasonObj)
					{
					}
				}));
	}

	/**
	 * 发送post请求,经过封装后使用方式式与AsyncHttpClient完全一样
	 */
	private void postRequest()
	{
		/**
		 * 这里在实际工程中还要再封装一层才好
		 */
		RequestParams params = new RequestParams();
		params.put("mb", "18911230100");
		params.put("pwd", "999999q");
		CommonOkHttpClient.getInstance().post(CommonRequest.createPostRequest(UrlConstants.USER_LOGIN, params),
				new DisposeDataHandle(this, User.class));
	}

	/**
	 * 此处以下载图片做为文件下载的demo
	 */
	private void downloadFile()
	{
		CommonOkHttpClient.getInstance().downloadFile(
				CommonRequest.createGetRequest("http://images.csdn.net/20150817/1.jpg", null),
				new DisposeDataHandle(new DisposeDataListener()
				{
					@Override
					public void onSuccess(Object responseObj)
					{
						mImageView.setImageBitmap(BitmapFactory.decodeFile(((File) responseObj).getAbsolutePath()));
					}

					@Override
					public void onFailure(Object reasonObj)
					{
					}
				}, Environment.getExternalStorageDirectory().getAbsolutePath() + "/test2.jpg"));
	}

	/**
	 * 服务器返回数据
	 * 
	 * @param responseObj
	 */
	@Override
	public void onSuccess(Object responseObj)
	{
		Log.e("----->success", ((User) responseObj).data.nick);
	}

	@Override
	public void onFailure(Object reasonObj)
	{
		Log.e("----->error", reasonObj.toString());

	}
}