package com.loopj.android.http.commonhttp;

import org.json.JSONObject;

/**********************************************************
 * @文件名称：CommonJsonResponseHandler.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月19日 上午11:01:13
 * @文件描述：业务逻辑层真正处理的地方，包括java层异常和业务层异常
 * @修改历史：2015年8月19日创建初始版本
 **********************************************************/
public class CommonJsonResponseHandler extends BaseJsonResponseHandler
{
	public CommonJsonResponseHandler(DisposeDataHandle dataHandle)
	{
		super(dataHandle);
	}

	public CommonJsonResponseHandler(DisposeDataHandle dataHandle, Class<?> clazz)
	{
		super(dataHandle, clazz);
	}

	@Override
	public void onStart()
	{
		mDataHandle.onStart();
	}

	@Override
	public void onProgress(long bytesWritten, long totalSize)
	{
		mDataHandle.onProgress(bytesWritten, totalSize);
	}

	@Override
	public void onSuccess(JSONObject response)
	{
		handleResponse(response);
	}

	@Override
	public void onFailure(Throwable throwObj)
	{
		mDataHandle.onFailure(new LogicException(NETWORK_ERROR, throwObj.getMessage()));
	}

	@Override
	public void onCancel()
	{
		mDataHandle.onCancel();
	}

	@Override
	public void onRetry(int retryNo)
	{
		mDataHandle.onRetry(retryNo);
	}

	@Override
	public void onFinish()
	{
		mDataHandle.onFinish();
	}

	/**
	 * handle the server response
	 */
	private void handleResponse(JSONObject response)
	{
		if (response == null)
		{
			mDataHandle.onFailure(new LogicException(NETWORK_ERROR, EMPTY_MSG));
			return;
		}

		try
		{
			if (response.has(RESULT_CODE))
			{
				if (response.optInt(RESULT_CODE) == RESULT_CODE_VALUE)
				{
					if (mClass == null)
					{
						mDataHandle.onSuccess(response);
					}
					else
					{
						Object obj = ResponseEntityToModule.parseJsonObjectToModule(response, mClass);
						if (obj != null)
						{
							mDataHandle.onSuccess(obj);
						}
						else
						{
							mDataHandle.onFailure(new LogicException(JSON_ERROR, EMPTY_MSG));
						}
					}
				}
				else
				{
					if (response.has(ERROR_MSG))
					{
						mDataHandle.onFailure(new LogicException(response.optInt(RESULT_CODE), response
								.optString(ERROR_MSG)));
					}
					else
					{
						mDataHandle.onFailure(new LogicException(response.optInt(RESULT_CODE), EMPTY_MSG));
					}
				}
			}
			else
			{
				if (response.has(ERROR_MSG))
				{
					mDataHandle.onFailure(new LogicException(OTHER_ERROR, response.optString(ERROR_MSG)));
				}
			}
		}
		catch (Exception e)
		{
			mDataHandle.onFailure(new LogicException(OTHER_ERROR, e.getMessage()));
			e.printStackTrace();
		}
	}
}