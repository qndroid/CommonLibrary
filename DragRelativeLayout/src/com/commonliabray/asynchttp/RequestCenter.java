package com.commonliabray.asynchttp;

import com.commonliabray.model.User;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.commonhttp.CommonClient;
import com.loopj.android.http.commonhttp.CommonJsonResponseHandler;
import com.loopj.android.http.commonhttp.DisposeDataHandle;

public class RequestCenter
{

	public static RequestHandle requestLogin(String username, String password, DisposeDataHandle dataHandler)
	{
		RequestParams params = new RequestParams();
		params.put("mb", username);
		params.put("pwd", password);
		return CommonClient.post(UrlConstants.USER_LOGIN, params, new CommonJsonResponseHandler(dataHandler,User.class));
	}
}
