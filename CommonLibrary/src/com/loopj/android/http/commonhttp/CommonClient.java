package com.loopj.android.http.commonhttp;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

/**********************************************************
 * @文件名称：CommonClient.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月19日 上午11:38:57
 * @文件描述：通用httpclient,支持重连,取消请求,Cookie存储
 * @修改历史：2015年8月19日创建初始版本
 **********************************************************/
public class CommonClient
{
	private static AsyncHttpClient client;
	static
	{
		/**
		 * init the retry exception
		 */
		AsyncHttpClient.allowRetryExceptionClass(IOException.class);
		AsyncHttpClient.allowRetryExceptionClass(SocketTimeoutException.class);
		AsyncHttpClient.allowRetryExceptionClass(ConnectTimeoutException.class);
		/**
		 * init the block retry exception
		 */
		AsyncHttpClient.blockRetryExceptionClass(UnknownHostException.class);
		AsyncHttpClient.blockRetryExceptionClass(ConnectionPoolTimeoutException.class);

		client = new AsyncHttpClient();
	}

	public static RequestHandle get(String url, AsyncHttpResponseHandler responseHandler)
	{
		return client.get(url, responseHandler);
	}

	public static RequestHandle get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
	{
		return client.get(url, params, responseHandler);
	}

	public static RequestHandle get(Context context, String url, AsyncHttpResponseHandler responseHandler)
	{
		return client.get(context, url, responseHandler);
	}

	public static RequestHandle get(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler)
	{
		return client.get(context, url, params, responseHandler);
	}

	public static RequestHandle get(Context context, String url, Header[] headers, RequestParams params,
			AsyncHttpResponseHandler responseHandler)
	{
		return client.get(context, url, headers, params, responseHandler);
	}

	public static RequestHandle post(String url, AsyncHttpResponseHandler responseHandler)
	{
		return client.post(url, responseHandler);
	}

	public static RequestHandle post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
	{
		return client.post(url, params, responseHandler);
	}

	public static RequestHandle post(Context context, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler)
	{
		return client.post(context, url, params, responseHandler);
	}

	public static RequestHandle post(Context context, String url, HttpEntity entity, String contentType,
			AsyncHttpResponseHandler responseHandler)
	{
		return client.post(context, url, entity, contentType, responseHandler);
	}

	public static RequestHandle post(Context context, String url, Header[] headers, RequestParams params,
			String contentType, AsyncHttpResponseHandler responseHandler)
	{
		return client.post(context, url, headers, params, contentType, responseHandler);
	}

	public static RequestHandle post(Context context, String url, Header[] headers, HttpEntity entity,
			String contentType, AsyncHttpResponseHandler responseHandler)
	{
		return client.post(context, url, headers, entity, contentType, responseHandler);
	}

	/**
	 * calcel the context relative request
	 * @param context
	 * @param mayInterruptIfRunning
	 */
	public void calcelRequests(Context context, boolean mayInterruptIfRunning)
	{
		client.cancelRequests(context, mayInterruptIfRunning);
	}

	/**
	 * cancel current all request in app
	 * @param mayInterruptIfRunning
	 */
	public void cacelAllrequests(boolean mayInterruptIfRunning)
	{
		client.cancelAllRequests(mayInterruptIfRunning);
	}

	public static void setHttpContextAttribute(String id, Object obj)
	{
		client.getHttpContext().setAttribute(id, obj);
	}

	public static Object getHttpContextAttribute(String id)
	{
		return client.getHttpContext().getAttribute(id);
	}

	public static void removeHttpContextAttribute(String id)
	{
		client.getHttpContext().removeAttribute(id);
	}

	/**
	 * set the cookie store
	 * @param cookieStore
	 */
	public static void setCookieStore(CookieStore cookieStore)
	{
		client.setCookieStore(cookieStore);
	}

	/**
	 * remove the cookie store
	 */
	public static void removeCookieStore()
	{
		removeHttpContextAttribute(ClientContext.COOKIE_STORE);
	}
}