package com.commonliabray.asynchttp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.common.widget.associatemail.MailBoxAssociateView;
import com.commonliabray.asynchttp.RequestCenter;
import com.commonliabray.model.User;
import com.example.dragrelativelayout.R;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.commonhttp.DisposeDataHandle;

/**********************************************************
 * @文件名称：LoginActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月19日 下午11:21:54
 * @文件描述：测试用户登陆页面
 * @修改历史：2015年11月19日创建初始版本
 **********************************************************/
public class LoginActivity extends Activity implements OnClickListener
{
	private RequestHandle loginRequest;
	/**
	 * UI
	 */
	private MailBoxAssociateView mUserNameView;
	private EditText mPasswordView;
	private TextView mLoginView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mailassociate);
		initView();
	}

	private void initView()
	{
		mUserNameView = (MailBoxAssociateView) findViewById(R.id.associate_email_input);
		mPasswordView = (EditText) findViewById(R.id.login_input_password);
		mLoginView = (TextView) findViewById(R.id.login_button);
		mLoginView.setOnClickListener(this);
	}

	private void requestLogin()
	{
		loginRequest = RequestCenter.requestLogin("18911230100", "999999q", new DisposeDataHandle()
		{
			@Override
			public void onRetry(int retryNo)
			{
				super.onRetry(retryNo);
			}

			@Override
			public void onSuccess(Object responseObj)
			{
				Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
				intent.putExtra("user", (User) responseObj);
				startActivity(intent);
			}

			@Override
			public void onFailure(Object reasonObj)
			{
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (loginRequest != null)
		{
			loginRequest.cancel(true);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

		case R.id.login_button:
			/**
			 * 可以弹出一个对话框阻止用户再用次操作
			 */
			requestLogin();
			break;
		}
	}
}
