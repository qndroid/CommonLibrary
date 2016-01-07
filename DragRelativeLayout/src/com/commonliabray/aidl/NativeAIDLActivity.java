package com.commonliabray.aidl;

import com.commonliabray.aidl.NativeAIDLService.CountBinder;
import com.commonliabray.aidl.NativeAIDLService.MessageListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class NativeAIDLActivity extends Activity {

	private CountBinder countBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView textView = new TextView(this);
		textView.setText("获取远程服务数据");
		textView.setLayoutParams(
				new ViewGroup.MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(textView);

		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/**
				 * 主动拉取服务的数据
				 */
				Log.e("------->", countBinder.getNumber() + " ");
			}
		});

		Intent intent = new Intent(this, NativeAIDLService.class);
		bindService(intent, conn, BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

			countBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			countBinder = (CountBinder) service;

			/**
			 * 注册回调到要绑定的服务。使得接收到推送来的消息
			 */
			countBinder.registerListener(new MessageListener() {

				@Override
				public void notifyMessage(int number) {

					if (number % 5 == 0) {

						Log.e("------------------>", number + "");
					}
				}
			});
		}
	};
}