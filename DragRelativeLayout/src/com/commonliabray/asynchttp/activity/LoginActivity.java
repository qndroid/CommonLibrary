package com.commonliabray.asynchttp.activity;

import com.common.widget.associatemail.MailBoxAssociateView;
import com.commonliabray.asynchttp.RequestCenter;
import com.commonliabray.asynchttp.manager.UserManager;
import com.commonliabray.jpush.JPushTestActivity;
import com.commonliabray.model.PushMessage;
import com.commonliabray.model.User;
import com.example.dragrelativelayout.R;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.commonhttp.DisposeDataHandle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**********************************************************
 * @文件名称：LoginActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月19日 下午11:21:54
 * @文件描述：测试用户登陆页面
 * @修改历史：2015年11月19日创建初始版本
 **********************************************************/
public class LoginActivity extends Activity implements OnClickListener {
	private RequestHandle loginRequest;
	/**
	 * UI
	 */
	private MailBoxAssociateView mUserNameView;
	private EditText mPasswordView;
	private TextView mLoginView;

	/**
	 * data
	 */
	private PushMessage mPushMessage; // 推送过来的消息
	private boolean fromPush; // 是否从推送到此页面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mailassociate);
		initData();
		initView();
	}

	private void initData() {
		Intent intent = getIntent();
		if (intent.hasExtra("pushMessage")) {
			mPushMessage = (PushMessage) intent.getSerializableExtra("pushMessage");
		}
		fromPush = intent.getBooleanExtra("fromPush", false);
	}

	private void initView() {
		mUserNameView = (MailBoxAssociateView) findViewById(R.id.associate_email_input);
		mPasswordView = (EditText) findViewById(R.id.login_input_password);
		mLoginView = (TextView) findViewById(R.id.login_button);
		mLoginView.setOnClickListener(this);
	}

	private void requestLogin() {
		loginRequest = RequestCenter.requestLogin("18911230100", "999999q", new DisposeDataHandle() {
			@Override
			public void onRetry(int retryNo) {
				super.onRetry(retryNo);
			}

			@Override
			public void onSuccess(Object responseObj) {
				/**
				 * 保存登陆信息
				 */
				UserManager.getInstance().setUser((User) responseObj);

				if (fromPush) {
					Intent intent = new Intent(LoginActivity.this, JPushTestActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("pushMessage", mPushMessage);
					startActivity(intent);
					finish();

				} else {
					Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
					intent.putExtra("user", (User) responseObj);
					startActivity(intent);
				}
			}

			@Override
			public void onFailure(Object reasonObj) {
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (loginRequest != null) {
			loginRequest.cancel(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.login_button:
			/**
			 * 可以弹出一个对话框阻止用户再用次操作
			 */
			requestLogin();
			break;
		}
	}
}
