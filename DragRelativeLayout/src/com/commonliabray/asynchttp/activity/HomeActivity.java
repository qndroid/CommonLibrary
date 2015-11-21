package com.commonliabray.asynchttp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.commonliabray.model.User;
import com.example.dragrelativelayout.R;
import com.loopj.android.http.PersistentCookieStore;

/**********************************************************
 * @文件名称：HomeActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月21日 下午12:12:14
 * @文件描述：显示从服务器返回的Cookie值
 * @修改历史：2015年11月21日创建初始版本
 **********************************************************/
public class HomeActivity extends Activity
{
	private User user;
	/**
	 * UI
	 */
	private TextView mUserView;
	private TextView mCookieView;
	private TextView mGotoWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initData();
		initView();
	}

	private void initData()
	{
		user = (User) getIntent().getSerializableExtra("user");
	}

	private void initView()
	{
		mUserView = (TextView) findViewById(R.id.username_value_view);
		mCookieView = (TextView) findViewById(R.id.cookie_value_view);
		mUserView.setText(user.data.name);
		/**
		 * 已经将Cookie自动存储到全局变量中，可以按以下方式取出Cookie供WebView等需要Cookie的地方使用
		 */
		PersistentCookieStore cookies = new PersistentCookieStore(this);
		for (int i = 0; i < cookies.getCookies().size(); i++)
		{
			/**
			 * 这里只显示了tookie,还有其它cookie保存了起来
			 */
			if (cookies.getCookies().get(i).getName().equals("tcookie"))
			{
				mCookieView.setText(cookies.getCookies().get(i).toString());
			}
		}

		mGotoWebView = (TextView) findViewById(R.id.visit_webview);
		mGotoWebView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(HomeActivity.this, WebActivity.class));
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		/**
		 * dispose the request
		 */
		super.onDestroy();
	}
}
