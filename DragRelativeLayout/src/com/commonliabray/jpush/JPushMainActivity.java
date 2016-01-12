package com.commonliabray.jpush;

import com.example.dragrelativelayout.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class JPushMainActivity extends InstrumentedActivity implements OnClickListener {

	private Button mInit;
	private Button mSetting;
	private Button mStopPush;
	private Button mResumePush;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jpush_activity_main);
		initView();
	}

	private void initView() {
		TextView mImei = (TextView) findViewById(R.id.tv_imei);
		String udid = ExampleUtil.getImei(getApplicationContext(), "");
		if (null != udid)
			mImei.setText("IMEI: " + udid);

		TextView mAppKey = (TextView) findViewById(R.id.tv_appkey);
		String appKey = ExampleUtil.getAppKey(getApplicationContext());
		if (null == appKey)
			appKey = "AppKey异常";
		mAppKey.setText("AppKey: " + appKey);

		String packageName = getPackageName();
		TextView mPackage = (TextView) findViewById(R.id.tv_package);
		mPackage.setText("PackageName: " + packageName);

		String versionName = ExampleUtil.GetVersion(getApplicationContext());
		TextView mVersion = (TextView) findViewById(R.id.tv_version);
		mVersion.setText("Version: " + versionName);

		mInit = (Button) findViewById(R.id.init);
		mInit.setOnClickListener(this);

		mStopPush = (Button) findViewById(R.id.stopPush);
		mStopPush.setOnClickListener(this);

		mResumePush = (Button) findViewById(R.id.resumePush);
		mResumePush.setOnClickListener(this);

		mSetting = (Button) findViewById(R.id.setting);
		mSetting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting:
			Intent intent = new Intent(JPushMainActivity.this, JPushSetActivity.class);
			startActivity(intent);
			break;
		case R.id.stopPush:
			JPushInterface.stopPush(getApplicationContext());
			break;
		case R.id.resumePush:
			JPushInterface.resumePush(getApplicationContext());
			break;
		}
	}
}