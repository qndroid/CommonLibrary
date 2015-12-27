package com.commonliabray.sharesdk;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;

import com.example.dragrelativelayout.R;
import com.sharesdk.ShareData;
import com.sharesdk.ShareManager;
import com.sharesdk.ShareManager.PlatofrmType;

public class ShareActivity extends Activity implements OnClickListener, PlatformActionListener
{

	private Button mQQButton;
	private Button mQZoneButton;
	private Button mWeChatFriend;
	private Button mSmsView;
	private Button mEmailView;
	private Button mTecentWeiBoView;

	private ShareManager shareManager;
	private ShareData mData;

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
		mWeChatFriend = (Button) findViewById(R.id.wechat_view);
		mSmsView = (Button) findViewById(R.id.sms_view);
		mEmailView = (Button) findViewById(R.id.email_view);
		mTecentWeiBoView = (Button) findViewById(R.id.tecent_weibo_view);

		mQQButton.setOnClickListener(this);
		mQZoneButton.setOnClickListener(this);
		mWeChatFriend.setOnClickListener(this);
		mSmsView.setOnClickListener(this);
		mEmailView.setOnClickListener(this);
		mTecentWeiBoView.setOnClickListener(this);

		shareManager = ShareManager.getInstance();

	}

	@Override
	public void onClick(View v)
	{
		mData = new ShareData();
		switch (v.getId())
		{
		case R.id.qq_view:
			ShareParams params = new ShareParams();
			params.setText("QQ文本");
			mData.mPlatformType = PlatofrmType.QQ;
			mData.mShareParams = params;
			break;
		case R.id.qzone_view:
			ShareParams params2 = new ShareParams();
			params2.setText(getString(R.string.app_name));
			params2.setTitle("测试分享的标题");
			// params2.setTitleUrl("www.sharesdk.cn");
			//params2.setTitleUrl(getString(R.string.baidu));
			mData.mPlatformType = PlatofrmType.QZone;
			mData.mShareParams = params2;
			break;
		case R.id.wechat_view:
			ShareParams params3 = new ShareParams();
			params3.setShareType(Platform.SHARE_IMAGE);
			params3.setTitle("你好，沛语!");
			params3.setImagePath(Environment.getExternalStorageDirectory() + "/1.jpg");
			mData.mPlatformType = PlatofrmType.WeChat;
			mData.mShareParams = params3;
			break;
		case R.id.email_view:
			ShareParams params5 = new ShareParams();
			// params4.setShareType(Platform.SHARE_IMAGE);
			params5.setAddress("277451977@qq.com");
			params5.setTitle("邮件文本");
			params5.setText("我要分享的内容。");
			params5.setImagePath(Environment.getExternalStorageDirectory() + "/1.jpg");
			mData.mPlatformType = PlatofrmType.Email;
			mData.mShareParams = params5;
			break;
		case R.id.sms_view:
			ShareParams params4 = new ShareParams();
			// params4.setShareType(Platform.SHARE_IMAGE);
			params4.setAddress("18911230100");
			params4.setTitle("短信文本");
			params4.setText("我要分享的内容。");
			params4.setImagePath(Environment.getExternalStorageDirectory() + "/1.jpg");
			mData.mPlatformType = PlatofrmType.SMS;
			mData.mShareParams = params4;
			break;
		case R.id.tecent_weibo_view:
			ShareParams params51 = new ShareParams();
			// params4.setShareType(Platform.SHARE_IMAGE);
			// params51.setAddress("18911230100");
			// params51.setTitle("短信文本");
			params51.setText("我要分享的内容。");
			params51.setImagePath(Environment.getExternalStorageDirectory() + "/1.jpg");
			mData.mPlatformType = PlatofrmType.TencentWeibo;
			mData.mShareParams = params51;
			break;
		}
		/**
		 * 需要一个对话框阻止用户多次操作
		 */
		shareManager.shareData(mData, this);
	}

	@Override
	public void onCancel(Platform arg0, int arg1)
	{
		Log.e("------------->", "cancel");
	}

	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2)
	{
		Log.e("------------->", arg0.getName() + "arg1:" + arg1);
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2)
	{
		Log.e("----->onError", arg2.getMessage());

	}

}