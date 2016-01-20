package com.commonliabray.okhttp;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**********************************************************
 * @文件名称：OKHttpTestActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2016年1月20日 下午9:08:08
 * @文件描述：OkHttp使用测试
 * @修改历史：2016年1月20日创建初始版本
 **********************************************************/
public class OKHttpTestActivity extends Activity
{
	private OkHttpClient mOkHttpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mOkHttpClient = new OkHttpClient();

		// requestData();
		uploadFile();
	}

	/**
	 * 利用okhttp发送网络请求
	 */
	private void requestData()
	{
		Request request = new Request.Builder().url("https://github.com/hongyangAndroid").build();
		mOkHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onResponse(Response response) throws IOException
			{
				Headers responseHeaders = response.headers();
				for (int i = 0; i < responseHeaders.size(); i++)
				{
					System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
				}

				Log.e("TAG", response.body().string());
			}

			@Override
			public void onFailure(Request request, IOException ioexception)
			{
				System.out.println(ioexception.getMessage());
			}
		});
	}

	private void uploadFile()
	{
		File file = new File(Environment.getExternalStorageDirectory(), "picture1449494540.jpg");
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		RequestBody requestBody = new MultipartBuilder()
				.type(MultipartBuilder.FORM)
				.addPart(Headers.of("Content-Disposition", "form-data; name=\"username\""),
						RequestBody.create(null, "张鸿洋"))
				.addPart(
						Headers.of("Content-Disposition", "form-data; name=\"mFile\"",
								"filename=\"picture1449494540.jpg\""), fileBody).build();
		Request request = new Request.Builder().url("http://192.168.1.103:8080/okHttpServer/fileUpload")
				.post(requestBody).build();

		mOkHttpClient.newCall(request).enqueue(new Callback()
		{
			@
			Override
			public void onResponse(Response response) throws IOException
			{
				Log.e("TAG", response.body().string());
			}

			@Override
			public void onFailure(Request request, IOException ioexception)
			{
				Log.e("TAG_ERROR", ioexception.getMessage());
			}
		});
	}
}
