package com.commonliabray.sharesdk;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import com.example.dragrelativelayout.R;
import com.sharesdk.ShareManager;
import com.sharesdk.ShareManager.PlatofrmType;

public class ThirdLoginActivity extends Activity implements OnClickListener, PlatformActionListener
{

	private Button mQQButton;
	private Button mQZoneButton;

	private ShareManager shareManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharesdk_layout);
		initView();
	}

	private void initView()
	{
		mQQButton = (Button) findViewById(R.id.qq_view);
		mQZoneButton = (Button) findViewById(R.id.qzone_view);

		mQQButton.setOnClickListener(this);
		mQZoneButton.setOnClickListener(this);

		shareManager = ShareManager.getInstance();

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.qq_view:
		case R.id.qzone_view:
			shareManager.loginEntry(PlatofrmType.QQ, this);
			break;

		}
		/**
		 * 需要一个对话框阻止用户多次操作
		 */
	}

	@Override
	public void onCancel(Platform arg0, int arg1)
	{
		Log.e("------------->", "cancel");
	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2)
	{
		Log.e("------------->", arg0.getName() + "arg2:" + arg2.toString());
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2)
	{
		Log.e("----->onError", arg2.getMessage());
	}
}