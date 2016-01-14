package com.commonliabray.jpush;

import com.commonliabray.model.PushMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class JPushTestActivity extends Activity {

	private TextView mTextView;
	private PushMessage mPushMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initData();
		initView();

	}

	private void initData() {
		Intent intent = getIntent();
		mPushMessage = (PushMessage) intent.getSerializableExtra("pushMessage");
	}

	private void initView() {

		mTextView = new TextView(this);
		addContentView(mTextView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mTextView.setText(
				"MessageType: " + mPushMessage.messageType + " Message Content: " + mPushMessage.messageContent);
	}
}
