package com.commonliabray.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.common.widget.view.IndiactorView;
import com.example.dragrelativelayout.R;

public class IndicatorActivity extends Activity
{

	private IndiactorView indictorView;
	private int i = 0;
	private Handler handler = new Handler()
	{

		public void handleMessage(Message msg)
		{

			indictorView.setPosition(i++);
			if (i <= 100)
			{

				this.sendEmptyMessageDelayed(0x01, 500);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator_layout);

		indictorView = (IndiactorView) findViewById(R.id.indictor_view);

		indictorView.setPosition(i);

		handler.sendEmptyMessageDelayed(0x01, 500);
	}
}