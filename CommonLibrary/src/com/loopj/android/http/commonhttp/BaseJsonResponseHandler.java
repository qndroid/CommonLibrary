package com.loopj.android.http.commonhttp;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

/**********************************************************
 * @文件名称：BaseJsonResponseHandler.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月19日 上午10:41:46
 * @文件描述：服务器Response基础类,包括了java层异常和业务逻辑层异常码定义
 * @修改历史：2015年8月19日创建初始版本
 **********************************************************/
public class BaseJsonResponseHandler extends JsonHttpResponseHandler
{
	/**
	 * the logic layer exception, may alter in different app
	 */
	protected final String RESULT_CODE = "ecode";
	protected final int RESULT_CODE_VALUE = 0;
	protected final String ERROR_MSG = "emsg";
	protected final String EMPTY_MSG = "";

	/**
	 * the java layer exception
	 */
	protected final int NETWORK_ERROR = -1; // the network relative error
	protected final int JSON_ERROR = -2; // the J relative error
	protected final int OTHER_ERROR = -3; // the unknow error

	/**
	 * interface and the handle class
	 */
	protected Class<?> mClass;
	protected DisposeDataHandle mDataHandle;

	public BaseJsonResponseHandler(DisposeDataHandle dataHandle, Class<?> clazz)
	{
		this.mDataHandle = dataHandle;
		this.mClass = clazz;
	}

	public BaseJsonResponseHandler(DisposeDataHandle dataHandle)
	{
		this.mDataHandle = dataHandle;
	}

	/**
	 * only handle the success branch(ecode == 0)
	 */
	public void onSuccess(JSONObject response)
	{
	}

	/**
	 * handle the java exception and logic exception branch(ecode != 0)
	 */
	public void onFailure(Throwable throwObj)
	{
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response)
	{
		onSuccess(response);
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
	{
		onFailure(throwable);
	}
}