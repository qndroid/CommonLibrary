package com.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * @author vision
 * @function build the request
 */
public class CommonRequest
{

	/**
	 * create the key-value Request
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static Request createPostRequest(String url, RequestParams params)
	{
		FormBody.Builder mFormBodyBuild = new FormBody.Builder();
		if (params != null)
		{
			for (Map.Entry<String, String> entry : params.urlParams.entrySet())
			{
				mFormBodyBuild.add(entry.getKey(), entry.getValue());
			}
		}
		FormBody mFormBody = mFormBodyBuild.build();
		return new Request.Builder().url(url).post(mFormBody).build();
	}

	/**
	 * ressemble the params to the url
	 * @param url
	 * @param params
	 * @return
	 */
	public static Request createGetRequest(String url, RequestParams params)
	{
		StringBuilder urlBuilder = new StringBuilder(url).append("?");
		if (params != null)
		{
			for (Map.Entry<String, String> entry : params.urlParams.entrySet())
			{
				urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1)).get().build();
	}
}